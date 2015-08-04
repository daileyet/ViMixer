package openthinks.crypto.mix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
/**
 * The collections of {@link MixBlock}
 * @author minjdai
 *
 */
public final class MixBlocks implements Iterable<MixBlock>{
	
	public static final MixBlocks create(List<MixBlock> blocks){
		return new MixBlocks(blocks);
	}
	
	private List<MixBlock> list =new ArrayList<MixBlock>();
	
	public MixBlocks(MixBlock[] blocks) {
		list.addAll(Arrays.asList(blocks));
	}
	
	public MixBlocks(List<MixBlock> blocks) {
		list.addAll(blocks);
	}
	
	public long size(){
		return list.size();
	}
	
	public MixBlock get(int index){
		return list.get(index);
	}
	
	@Override
	public Iterator<MixBlock> iterator() {
		return new Iterator<MixBlock>(){
			private int index = 0;
			@Override
			public boolean hasNext() {
				return (index) < MixBlocks.this.size();
			}

			@Override
			public MixBlock next() {
				MixBlock block = list.get(index);
				index++;
				return block;
			}
		};
	}

	

}
