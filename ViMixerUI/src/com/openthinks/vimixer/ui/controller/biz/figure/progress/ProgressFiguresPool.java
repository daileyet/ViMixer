package com.openthinks.vimixer.ui.controller.biz.figure.progress;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.openthinks.vimixer.ui.controller.biz.figure.FigureOverviewPool;
import com.openthinks.vimixer.ui.model.ViFile;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * @deprecated
 * @see FigureOverviewPool
 * @author Dailey
 * @since v1.0
 */
@Deprecated
public class ProgressFiguresPool {
	private final static ObservableMap<ViFile, ProgressView> caches = FXCollections.observableHashMap();
	private static ProgressOverViewFigure currentFigure;
	private final static LinkedBlockingQueue<ProgressOverViewFigure> concurrentLinkedQueue = new LinkedBlockingQueue<ProgressOverViewFigure>();

	public static ProgressView get(ViFile vifile) {
		if (caches.containsKey(vifile)) {
			return caches.get(vifile);
		} else {
			caches.put(vifile, new ProgressView());
			return caches.get(vifile);
		}
	}

	public final static void removeAll(List<ViFile> vifiles) {
		if (vifiles == null)
			return;
		for (ViFile vifile : vifiles) {
			caches.remove(vifile);
		}
		if (currentFigure != null) {
			currentFigure.destory();
		}
	}

	public final static void clear() {
		if (currentFigure != null) {
			currentFigure.destory();
		}
		caches.clear();
	}

	/**
	 * 
	 * @return ProgressOverViewFigure
	 */
	public static final ProgressOverViewFigure currentFigure() {
		return currentFigure;
	}

	public final static void push(ProgressOverViewFigure overViewFigure) {
		currentFigure = overViewFigure;
		concurrentLinkedQueue.add(overViewFigure);
	}

	public final static void active() {
		new ProgressFiguresPoolThread().start();
	}

	private final static class ProgressFiguresPoolThread extends Thread {
		@Override
		public void run() {
			ProgressOverViewFigure picked = null;
			do {
				try {
					picked = ProgressFiguresPool.concurrentLinkedQueue.take();
					if (ProgressFiguresPool.currentFigure == picked)
						picked.render();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (true);
		}
	}
}
