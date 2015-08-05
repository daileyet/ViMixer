/**
 * 
 */
package openthinks.crypto.mix;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The template class to implement {@link Mixer}
 * @author minjdai
 * @since v1.0
 */
public abstract class AbstractMixer implements Mixer {

	protected abstract MixTarget getMixTarget();

	protected abstract MixStrategy getMixStrategy();

	protected abstract MixProcesser getMixProcesser();

	@Override
	public void mix() throws InterruptedException {
		MixTarget mixTarget = this.getMixTarget();
		MixStrategy mixStrategy = this.getMixStrategy();
		MixProcesser mixProcesser = this.getMixProcesser();
		mixTarget.initial();
		final ExecutorService executorService =
		//Executors.newCachedThreadPool();
		Executors.newFixedThreadPool(100);
		final CompletionService<MixBlock> competeService = new ExecutorCompletionService<MixBlock>(executorService);
		mixProcesser.start();
		MixBlocks blocks = mixTarget.blocks();
		for (MixBlock mixBlock : blocks) {
			competeService.submit(new Callable<MixBlock>() {
				@Override
				public MixBlock call() throws Exception {
					mixStrategy.action(mixBlock);
					mixBlock.persist();
					return mixBlock;
				}
			});
		}
		// waiting for all the piece task 
		for (int i = 0; i < blocks.size(); i++) {
			Future<MixBlock> future = competeService.take();
			MixBlock block = null;
			try {
				block = future.get();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			mixProcesser.processed(block);
		}
		mixTarget.free();
		mixProcesser.completed();
		executorService.shutdownNow();
	}

}
