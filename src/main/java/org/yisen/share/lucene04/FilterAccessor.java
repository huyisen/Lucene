package org.yisen.share.lucene04;

public interface FilterAccessor {

	public String[] values();
	
	public String getField();
	
	public boolean set();
}
