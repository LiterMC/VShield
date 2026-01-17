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
package com.github.litermc.vshield.api;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ShieldDamageContext {
	private final ShieldableDamage damage;
	private final Vec3 from;
	private final Vec3 to;
	private final Entity source;

	public ShieldDamageContext(final ShieldableDamage damage, final Vec3 from, final Vec3 to) {
		this(damage, from, to, null);
	}

	public ShieldDamageContext(final ShieldableDamage damage, final Vec3 from, final Vec3 to, final Entity source) {
		this.damage = damage;
		this.from = from;
		this.to = to;
		this.source = source;
	}

	public ShieldableDamage getDamage() {
		return this.damage;
	}

	public Vec3 getFrom() {
		return this.from;
	}

	public Vec3 getTo() {
		return this.to;
	}

	public Entity getSource() {
		return this.source;
	}
}
