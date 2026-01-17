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
package com.github.litermc.vshield.compat;

import com.github.litermc.vshield.platform.PlatformHelper;

public enum CompatMods {
	COMPUTERCRAFT("computercraft"),
	JADE("jade");

	private final String modId;
	private Boolean loaded = null;

	private CompatMods(final String modId) {
		this.modId = modId;
	}

	public String getId() {
		return this.modId;
	}

	public boolean isLoaded() {
		if (this.loaded == null) {
			this.loaded = PlatformHelper.get().isModLoaded(this.getId());
		}
		return this.loaded;
	}
}
