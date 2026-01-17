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
package com.github.litermc.vshield.block;

import com.github.litermc.vshield.api.ShieldDamageType;
import com.github.litermc.vshield.api.block.IShieldBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractEnergyShieldBlock extends Block implements IShieldBlock {
	protected AbstractEnergyShieldBlock(final BlockBehaviour.Properties props) {
		super(props);
	}

	@Override
	public double getShieldDamageAbsorbation(final Level level, final BlockState state, final ShieldDamageType type) {
		if (type == ShieldDamageType.ENERGY) {
			return this.getShieldEnergyDamageAbsorbation(level, state);
		}
		return 0;
	}

	public abstract double getShieldEnergyDamageAbsorbation(final Level level, final BlockState state);
}
