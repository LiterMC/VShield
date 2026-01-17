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
package com.github.litermc.vshield.util;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public final class TaskUtil {
	private static final Queue<Task> TICK_START_QUEUE = new PriorityBlockingQueue<>();
	private static final Queue<Task> TICK_END_QUEUE = new PriorityBlockingQueue<>();
	private static volatile long tick = 0;

	private TaskUtil() {}

	public static void preServerTick() {
		tick++;
		final long t = tick;
		for (int i = TICK_START_QUEUE.size(); i > 0; i--) {
			final Task task = TICK_START_QUEUE.element();
			if (task.tick() > t) {
				return;
			}
			TICK_START_QUEUE.remove();
			task.task().run();
		}
	}

	public static void postServerTick() {
		final long t = tick;
		for (int i = TICK_END_QUEUE.size(); i > 0; i--) {
			final Task task = TICK_END_QUEUE.element();
			if (task.tick() > t) {
				return;
			}
			TICK_END_QUEUE.remove();
			task.task().run();
		}
	}

	public static void queueTickStart(final Runnable task) {
		queueTickStart(0, task);
	}

	public static void queueTickStart(final int delay, final Runnable task) {
		TICK_START_QUEUE.add(new Task(tick + delay, task));
	}

	public static void queueTickEnd(final Runnable task) {
		queueTickEnd(0, task);
	}

	public static void queueTickEnd(final int delay, final Runnable task) {
		TICK_END_QUEUE.add(new Task(tick + delay, task));
	}

	record Task(long tick, Runnable task) implements Comparable<Task> {
		@Override
		public int compareTo(final Task other) {
			return Long.compare(this.tick, other.tick);
		}
	}
}
