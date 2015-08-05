/**
 * 
 */
package openthinks.vimixer.ui.controller.biz.figure;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import openthinks.vimixer.ui.model.ViFile;

/**
 * {@link FigureOverview} cache pool and request queue
 * @since v1.0
 * @author minjdai
 */
public class FigureOverviewPool {

	// cache of all Figureable, the structure as below: ViFile <-> FigureableMap
	private final static ObservableMap<ViFile, FigureableMap> caches = FXCollections.observableHashMap();

	// cache current FigureOverview
	private final static ObservableMap<Class<? extends FigureOverview<? extends Figureable>>, FigureOverview<? extends Figureable>> currentFigureMap = FXCollections
			.observableHashMap();

	private final static LinkedBlockingQueue<FigureOverview<? extends Figureable>> concurrentLinkedQueue = new LinkedBlockingQueue<FigureOverview<? extends Figureable>>();

	/**
	 * create and get {@link Figurable} by the keys
	 * 
	 * @param observableItem
	 * @param figureOverviewClz
	 * @return {@link Figurable}
	 */
	public final static Figureable lookup(ViFile observableItem,
			Class<? extends FigureOverview<? extends Figureable>> figureOverviewClz) {

		if (caches.containsKey(observableItem)) {
			FigureableMap figureableMap = caches.get(observableItem);
			Figureable figureable = figureableMap.get(figureOverviewClz);
			return figureable;
		} else {
			caches.put(observableItem, new FigureableMap());
		}
		return lookup(observableItem, figureOverviewClz);
	}

	/**
	 * get current {@link FigureOverview} by key
	 * 
	 * @param figureOverviewClz
	 * @return
	 */
	public final static FigureOverview<? extends Figureable> currentFigure(
			Class<? extends FigureOverview<? extends Figureable>> figureOverviewClz) {
		return currentFigureMap.get(figureOverviewClz);
	}

	@SuppressWarnings("unchecked")
	public final static void push(FigureOverview<? extends Figureable> figureOverview) {
		currentFigureMap.put((Class<? extends FigureOverview<? extends Figureable>>) figureOverview.getClass(),
				figureOverview);
		concurrentLinkedQueue.add(figureOverview);
	}

	public final static void removeAll(List<ViFile> vifiles) {
		if (vifiles == null)
			return;
		for (ViFile vifile : vifiles) {
			caches.remove(vifile);
		}
		if (currentFigureMap != null) {
			for (Class<? extends FigureOverview<? extends Figureable>> key : currentFigureMap.keySet()) {
				FigureOverview<? extends Figureable> figureOverview = currentFigureMap.get(key);
				if (figureOverview != null) {
					figureOverview.destory();
				}
			}
			currentFigureMap.clear();
		}
	}

	public final static void clear() {
		if (currentFigureMap != null) {
			for (Class<? extends FigureOverview<? extends Figureable>> key : currentFigureMap.keySet()) {
				FigureOverview<? extends Figureable> figureOverview = currentFigureMap.get(key);
				if (figureOverview != null) {
					figureOverview.destory();
				}
			}
			currentFigureMap.clear();
		}
		caches.clear();
	}

	public final static void active() {
		new FiguresPoolThread().start();
	}

	// Thread for figure render
	private final static class FiguresPoolThread extends Thread {

		@Override
		public void run() {
			FigureOverview<? extends Figureable> picked = null;
			do {
				try {
					picked = concurrentLinkedQueue.take();
					@SuppressWarnings("unchecked")
					FigureOverview<? extends Figureable> current = currentFigure((Class<? extends FigureOverview<? extends Figureable>>) picked
							.getClass());
					if (current == picked) {
						picked.render();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} while (true);
		}

	}

	/**
	 * @param figureOverviewClz
	 */
	private static Figureable figureableOf(Class<? extends FigureOverview<? extends Figureable>> figureOverviewClz) {
		Figureable figureable = null;

		Class<? extends Figureable> figureableClazz = figureClazzMap.get(figureOverviewClz);
		if (figureableClazz == null) {
			throw new UnsupportFigureOverview("Not suuport this class type:" + figureOverviewClz);
		}
		try {
			figureable = figureableClazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return figureable;
	}

	// store the mapping between FigureOverview and Figureable
	private static final ObservableMap<Class<? extends FigureOverview<? extends Figureable>>, Class<? extends Figureable>> figureClazzMap = FXCollections
			.observableHashMap();
	private static final Lock lock = new ReentrantLock();

	public static final void logFigureClass(Class<? extends FigureOverview<? extends Figureable>> figureOverviewClazz,
			Class<? extends Figureable> figureableClazz) {
		lock.lock();
		try {
			if (figureClazzMap.containsKey(figureOverviewClazz))
				;
			else
				figureClazzMap.put(figureOverviewClazz, figureableClazz);
		} finally {
			lock.unlock();
		}
	}

	// as the value in caches; ViFile <-> FigureableMap
	// Class<FigureOverview> <-> Figureable
	private final static class FigureableMap {
		private ObservableMap<Class<? extends FigureOverview<? extends Figureable>>, Figureable> figureMap = FXCollections
				.observableHashMap();

		@SuppressWarnings({ "unchecked" })
		<K extends Figureable> K get(Class<? extends FigureOverview<? extends Figureable>> figureOverviewClz) {
			if (figureMap.containsKey(figureOverviewClz))
				;
			else {
				Figureable figureable = figureableOf(figureOverviewClz);
				figureMap.put(figureOverviewClz, figureable);
			}
			return (K) figureMap.get(figureOverviewClz);
		}
	}

}
