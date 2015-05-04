package org.yisen.share.lucene04_Filter;


import org.junit.Test;

public class TestFilter {
	@Test
	public void testIndex() throws Exception {
		LuceneUtils.Index();
	}

	@Test
	public void testSearch() throws Exception {
		LuceneUtils.search();
	}
}
