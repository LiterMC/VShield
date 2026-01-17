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
package com.github.litermc.vshield.config;

import com.github.litermc.vshield.platform.PlatformHelper;

import java.nio.file.Path;

public final class ConfigSpec {
	public static final ConfigFile serverSpec;

	// public static final ConfigFile.Value<Boolean> FORCE_LOAD_ALL_SHIPS;

	private ConfigSpec() {}

	static {
		final ConfigFile.Builder builder = PlatformHelper.get().createConfigBuilder();
		// {
		// 	builder
		// 		.comment("General settings")
		// 		.push("general");

		// 	FORCE_LOAD_ALL_SHIPS = builder
		// 		.comment("Should force load all ships on the server")
		// 		.define("force_load_all_ships", Config.forceLoadAllShips);

		// 	builder.pop();
		// }

		serverSpec = builder.build(ConfigSpec::syncServer);
	}

	public static void syncServer(Path path) {
		// Config.forceLoadAllShips = FORCE_LOAD_ALL_SHIPS.get();
	}

	public static void syncClient(Path path) {
	}
}
