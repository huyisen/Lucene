package org.yisen.share.lucene04_Filter;


public class SimpleFilterData implements FilterData {

	@Override
	public boolean set() {
		return false;
	}

	@Override
	public String getField() {
		return "id";
	}

	@Override
	public String[] values() {
		return new String[]{"1","3","5"};
	}

}
