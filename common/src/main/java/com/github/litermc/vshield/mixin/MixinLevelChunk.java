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
package com.github.litermc.vshield.mixin;

import com.github.litermc.vshield.accessor.LevelChunkAccessor;
import com.github.litermc.vshield.api.ShieldDamageType;
import com.github.litermc.vshield.api.block.IShieldBlock;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.ticks.LevelChunkTicks;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelChunk.class)
public abstract class MixinLevelChunk extends ChunkAccess implements LevelChunkAccessor {
	@Shadow
	@Final
	Level level;

	@Unique
	private Object2DoubleMap<ShieldDamageType> shieldMaxHealth = new Object2DoubleOpenHashMap<>();

	protected MixinLevelChunk() {
		super(null, null, null, null, 0, null, null);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void LevelChunk$init(
		final Level level,
		final ChunkPos chunkPos,
		final UpgradeData data,
		final LevelChunkTicks<Block> blockTicks,
		final LevelChunkTicks<Fluid> fluidTicks,
		final long inhabitedTime,
		final LevelChunkSection[] sections,
		final LevelChunk.PostLoadProcessor postProcessor,
		final BlendingData blendingData,
		final CallbackInfo ci
	) {
		this.findBlocks(
			(state) -> state.getBlock() instanceof IShieldBlock,
			(pos, state) -> this.onBlockStateChanged(null, state)
		);
	}

	@Override
	public Object2DoubleMap<ShieldDamageType> vshield$getShieldMaxHealth() {
		return this.shieldMaxHealth;
	}

	@Unique
	private void onBlockStateChanged(final BlockState oldState, final BlockState newState) {
		if (oldState != null && oldState.getBlock() instanceof final IShieldBlock oldShield) {
			for (final ShieldDamageType type : ShieldDamageType.getAllTypes()) {
				final double health = oldShield.getShieldDamageAbsorbation(this.level, oldState, type);
				if (health != 0) {
					this.shieldMaxHealth.put(type, this.shieldMaxHealth.getDouble(type) - health);
				}
			}
		}
		if (newState.getBlock() instanceof final IShieldBlock newShield) {
			for (final ShieldDamageType type : ShieldDamageType.getAllTypes()) {
				final double health = newShield.getShieldDamageAbsorbation(this.level, newState, type);
				if (health != 0) {
					this.shieldMaxHealth.put(type, this.shieldMaxHealth.getDouble(type) + health);
				}
			}
		}
	}

	@Inject(method = "setBlockState", at = @At("RETURN"))
	public void setBlockState(
		final BlockPos pos,
		final BlockState newState,
		final boolean isMoving,
		final CallbackInfoReturnable<BlockState> cir
	) {
		final BlockState oldState = cir.getReturnValue();
		if (oldState == null) {
			return;
		}
		this.onBlockStateChanged(oldState, newState);
	}
}
