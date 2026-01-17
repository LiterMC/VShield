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

public abstract class ShieldableDamage {
	private double damage;

	public ShieldableDamage(final double damage) {
		this.damage = damage;
	}

	/**
	 * Get the damage type that determines which shield will be used to block the damage.
	 * @return the damage type
	 */
	public abstract ShieldDamageType getType();

	/**
	 * Check if the damage can cause ship to be destroyed.
	 * @return {@code true} if damage is greater than {@code 0}, {@code false} otherwise.
	 */
	public boolean hasDamage() {
		return this.damage > 0;
	}

	/**
	 * Get current damage dealing to the target.
	 * @return current damage amount
	 * @see setDamage
	 */
	public double getDamage() {
		return this.damage;
	}

	/**
	 * Update the damage dealing to the target.
	 * @param damage new damage amount
	 * @see getDamage
	 */
	public void setDamage(final double damage) {
		this.damage = damage;
	}

	public class Energy extends ShieldableDamage {
		public Energy(final double damage) {
			super(damage);
		}

		@Override
		public ShieldDamageType getType() {
			return ShieldDamageType.ENERGY;
		}
	}

	public class Kinetic extends ShieldableDamage {
		public Kinetic(final double damage) {
			super(damage);
		}

		@Override
		public ShieldDamageType getType() {
			return ShieldDamageType.KINETIC;
		}
	}
}
