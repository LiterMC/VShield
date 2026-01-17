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
package com.github.litermc.vshield.command;

import com.github.litermc.vshield.Constants;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.command.ShipArgument;

import java.util.Set;

public final class VShieldCommands {
	public static final String ROOT_LITERAL = Constants.MOD_ID;

	private VShieldCommands() {}

	public static void register(final CommandDispatcher<CommandSourceStack> dispatcher) {
		// dispatcher.register(Commands.literal(ROOT_LITERAL)
		// 	.requires((source) -> source.hasPermission(2))
		// 	.then(Commands.literal("delete")
		// 		.then(Commands.argument("ships", ShipArgument.ships())
		// 			.executes(VShieldCommands::delete)
		// 		)
		// 	)
		// );
	}

	// private static int delete(final CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
	// 	final CommandSourceStack source = context.getSource();
	// 	final MinecraftServer server = source.getServer();
	// 	final Set<Ship> ships = ShipArgument.getShips(context, "ships");
	// 	int successCount = 0;
	// 	for (final Ship ship : ships) {
	// 		if (!(ship instanceof ServerShip serverShip)) {
	// 			continue;
	// 		}
	// 		final ServerLevel level = Utils.getLevel(serverShip.getChunkClaimDimension());
	// 		if (level == null) {
	// 			continue;
	// 		}
	// 		ShipAllocator.get(level).putShip(serverShip);
	// 		successCount++;
	// 	}
	// 	final int finalSuccessCount = successCount;
	// 	source.sendSuccess(() ->
	// 		Component.translatable("command.valkyrienskies.delete.success", finalSuccessCount),
	// 		true
	// 	);
	// 	return finalSuccessCount;
	// }
}
