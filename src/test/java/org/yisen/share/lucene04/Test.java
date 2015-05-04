package org.yisen.share.lucene04;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("\\d{4}-.[01]\\d-\\d{2}");
		Matcher matcher = pattern.matcher("2011-13-23");
		System.out.println(matcher.matches());

	}
	
	

}
