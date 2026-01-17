/**
 * Copyright (C) 2026  the authors of VShield
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 **/
package com.github.litermc.vshield.attachment;

import com.github.litermc.vshield.accessor.LevelChunkAccessor;
import com.github.litermc.vshield.api.ShieldDamageType;
import com.github.litermc.vtil.api.attachment.IServerTickListener;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.joml.primitives.AABBic;
import org.valkyrienskies.core.api.ships.LoadedServerShip;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.impl.game.ships.ShipData;

import java.util.HashMap;
import java.util.Map;

@JsonAutoDetect(
	fieldVisibility = JsonAutoDetect.Visibility.NONE,
	isGetterVisibility = JsonAutoDetect.Visibility.NONE,
	getterVisibility = JsonAutoDetect.Visibility.NONE,
	setterVisibility = JsonAutoDetect.Visibility.NONE
)
public final class ShieldAttachment implements IServerTickListener {
	private static final double PASSIVE_REGEN_RATIO = 1.0 / (20 * 60); // 20 min to be fully recharged by passive regeneration
	private static final int MAX_PASSIVE_REGEN_INTERRUPTION = 10;

	private Object2DoubleMap<ShieldDamageType> shieldDamages = new Object2DoubleOpenHashMap<>();

	private int oneSecondTicks = 0;
	private Object2DoubleMap<ShieldDamageType> cachedShieldMaxHealth = new Object2DoubleOpenHashMap<>();
	private double passiveRegenInterruption = 0;

	public ShieldAttachment() {}

	public static ShieldAttachment get(final LoadedServerShip ship) {
		ShieldAttachment attachment = ship.getAttachment(ShieldAttachment.class);
		if (attachment == null) {
			attachment = new ShieldAttachment();
			ship.setAttachment(attachment);
		}
		return attachment;
	}

	@JsonGetter("shieldDamages")
	private Map<String, Double> getShieldHealthMap() {
		final Map<String, Double> data = new HashMap<>(this.shieldDamages.size(), 1);
		this.shieldDamages.forEach((k, v) -> data.put(k.getID(), v));
		return data;
	}

	@JsonSetter("shieldDamages")
	private void setShieldHealthMap(final Map<String, Double> data) {
		this.shieldDamages.clear();
		data.forEach((k, v) -> this.shieldDamages.put(ShieldDamageType.get(k), v));
	}

	public double getShieldMaxHealth(final ShieldDamageType type) {
		return this.cachedShieldMaxHealth.getDouble(type);
	}

	public double getShieldHealth(final ShieldDamageType type) {
		return Math.max(this.getShieldMaxHealth(type) - this.getShieldDamage(type), 0);
	}

	public double getShieldDamage(final ShieldDamageType type) {
		return this.shieldDamages.getDouble(type);
	}

	public void setShieldDamage(final ShieldDamageType type, final double amount) {
		this.shieldDamages.put(type, amount);
	}

	/**
	 * deal specific type and amount of damage to the shield.
	 * Also prevents passive shield regen for an amount time based on the damage it recieves.
	 *
	 * @param type damage type
	 * @param amount the amount of damage dealing to the shield
	 * @return the remain damage cannot be absorbed
	 */
	public double dealShieldDamage(final ShieldDamageType type, final double amount) {
		final double absorbAmount = Math.min(this.getShieldHealth(type), amount);
		this.shieldDamages.put(type, this.shieldDamages.getDouble(type) + absorbAmount);
		this.passiveRegenInterruption = Math.min(this.passiveRegenInterruption + amount / 100, MAX_PASSIVE_REGEN_INTERRUPTION);
		return amount - absorbAmount;
	}

	/**
	 * recharge specific type and health to the shield.
	 *
	 * @param type shield type
	 * @param amount the health amount to recharge to the shield
	 * @return the remain health that cannot be charged
	 */
	public double rechargeShield(final ShieldDamageType type, final double amount) {
		final double damage = this.shieldDamages.getDouble(type);
		if (damage <= amount) {
			this.shieldDamages.remove(type);
			return amount - damage;
		}
		this.shieldDamages.put(type, damage - amount);
		return 0;
	}

	@Override
	public void onServerTick(final ServerLevel level, final LoadedServerShip ship) {
		this.oneSecondTicks--;
		if (this.oneSecondTicks > 0) {
			return;
		}
		this.oneSecondTicks = 20;
		this.refreshCaches(level, ship);
		if (this.passiveRegenInterruption >= 1) {
			this.passiveRegenInterruption -= 1;
		} else {
			this.passiveRegenInterruption = 0;
			this.shieldPassiveRegen();
		}
	}

	private void shieldPassiveRegen() {
		final ObjectSet<Object2DoubleMap.Entry<ShieldDamageType>> entries = this.shieldDamages.object2DoubleEntrySet();
		final ObjectIterator<Object2DoubleMap.Entry<ShieldDamageType>> iter = entries.iterator();
		while (iter.hasNext()) {
			final Object2DoubleMap.Entry<ShieldDamageType> entry = iter.next();
			final ShieldDamageType type = entry.getKey();
			final double damage = entry.getDoubleValue();
			final double maxHealth = this.getShieldMaxHealth(type);
			final double maxRegen = maxHealth * PASSIVE_REGEN_RATIO;
			if (damage <= maxRegen) {
				iter.remove();
				continue;
			}
			entry.setValue(damage - maxRegen);
		}
	}

	private void refreshCaches(final ServerLevel level, final LoadedServerShip ship) {
		this.cachedShieldMaxHealth.clear();
		final AABBic box = ship.getShipAABB();
		if (box == null) {
			return;
		}
		ChunkPos.rangeClosed(
			new ChunkPos(SectionPos.blockToSectionCoord(box.minX()), SectionPos.blockToSectionCoord(box.minZ())),
			new ChunkPos(SectionPos.blockToSectionCoord(box.maxX()), SectionPos.blockToSectionCoord(box.maxZ()))
		)
			.forEach((chunkPos) -> {
				final LevelChunk chunk = level.getChunk(chunkPos.x, chunkPos.z);
				((LevelChunkAccessor) (chunk)).vshield$getShieldMaxHealth()
					.forEach((type, amount) -> this.cachedShieldMaxHealth.put(type, this.cachedShieldMaxHealth.getDouble(type) + amount));
			});
	}
}
