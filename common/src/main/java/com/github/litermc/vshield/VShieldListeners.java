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
package com.github.litermc.vshield;

import com.github.litermc.vshield.util.TaskUtil;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

import org.valkyrienskies.mod.common.ValkyrienSkiesMod;

public final class VShieldListeners {
	private VShieldListeners() {}

	/**
	 * may runs parallelly with other mods
	 */
	public static void onModInit() {
		VShieldRegistry.register();
	}

	/**
	 * runs on main thread only
	 */
	public static void onModSetup() {
		registerAttachments();
	}

	private static void registerAttachments() {
		// ValkyrienSkiesMod.getApi().registerAttachment(Attachment.class);
	}

	public static void onServerLevelLoad(final ServerLevel level) {
	}

	public static void onServerLevelUnload(final ServerLevel level) {
	}

	public static void preServerTick(final MinecraftServer server) {
		TaskUtil.preServerTick();
	}

	public static void postServerTick(final MinecraftServer server) {
		TaskUtil.postServerTick();
	}
}
