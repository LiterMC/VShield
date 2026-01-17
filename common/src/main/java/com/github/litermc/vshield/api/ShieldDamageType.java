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

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ShieldDamageType {
	// TODO: use registry?
	private static final Map<String, ShieldDamageType> TYPES = new ConcurrentHashMap<>();

	public static final ShieldDamageType ENERGY = of("energy");
	public static final ShieldDamageType KINETIC = of("kinetic");

	private final String id;

	private ShieldDamageType(final String id) {
		this.id = id;
	}

	public static ShieldDamageType of(final String id) {
		return TYPES.computeIfAbsent(id, ShieldDamageType::new);
	}

	public static ShieldDamageType get(final String id) {
		return TYPES.get(id);
	}

	public static Collection<ShieldDamageType> getAllTypes() {
		return TYPES.values();
	}

	/**
	 * Get the unique constant ID of this damage type
	 * @return an unique constant ID of this damage type
	 */
	public String getID() {
		return this.id;
	}
}
