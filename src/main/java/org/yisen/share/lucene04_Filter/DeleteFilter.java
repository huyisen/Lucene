package org.yisen.share.lucene04_Filter;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.util.OpenBitSet;

public class DeleteFilter extends Filter {

	private static final long serialVersionUID = 5789361790489119532L;
	private FilterData filterData;
	
	public DeleteFilter(FilterData filterData) {
		super();
		this.filterData = filterData;
	}


	@Override
	public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
		OpenBitSet obs = new OpenBitSet(reader.maxDoc());
		
		if(filterData.set()){
			setOpenBitSet(reader,obs);
		}else{
			clearOpenBitSet(reader,obs);
		}
		return obs;
	}

	
	private void clearOpenBitSet(IndexReader reader, OpenBitSet obs) {
		try {
			//先把元素填满
			obs.set(0,reader.maxDoc());
			int[] docs = new int[1];
			int [] freqs = new int[1];
			//获取id所在的doc的位置，并且将其设置为0
			for(String delId:filterData.values()) {
				//获取TermDocs
				TermDocs tds = reader.termDocs(new Term(filterData.getField(),delId));
				//会见查询出来的对象的位置存储到docs中，出现的频率存储到freqs中，返回获取的条数
				int count = tds.read(docs, freqs);
				if(count==1) {
					//将这个位置的元素删除
					obs.clear(docs[0]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setOpenBitSet(IndexReader reader, OpenBitSet obs) {
		try {
			int[] docs = new int[1];
			int[] freqs = new int[1];
			//获取id所在的doc的位置，并且将其设置为0
			for(String delId : filterData.values()){
				//获取TermDocs
				TermDocs tds = reader.termDocs(new Term(filterData.getField(),delId));
				//会见查询出来的对象的位置存储到docs中，出现的频率存储到freqs中，返回获取的条数
				int count = tds.read(docs, freqs);
				if(count==1) {
					obs.set(docs[0]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
