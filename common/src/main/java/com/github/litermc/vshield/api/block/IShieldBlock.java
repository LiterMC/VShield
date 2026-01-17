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
package com.github.litermc.vshield.api.block;

import com.github.litermc.vshield.api.ShieldDamageType;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * IShieldBlock is an interface that can be implemented on subclass of {@link Block} to provide shields to the ship.
 */
public interface IShieldBlock {
	/**
	 * Get the amount of damage the shield block may absorb.
	 * For same given inputs, the result must always be same as well.
	 * @param level level the block is in
	 * @param state state of the block
	 * @param type type of damage
	 * @return amount damage the shield may absorb from the type of damage
	 */
	double getShieldDamageAbsorbation(Level level, BlockState state, ShieldDamageType type);
}
