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
