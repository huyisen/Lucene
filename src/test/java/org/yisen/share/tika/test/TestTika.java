package org.yisen.share.tika.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.tika.Tika;
import org.junit.Test;

public class TestTika {
	@Test
	public void testDoc1() throws Exception {
		File f = new File("./tika/tika.docx");
		BufferedInputStream bis = new BufferedInputStream(
				new FileInputStream(f));
		int offset = -1;
		byte[] bf = new byte[1024];
		while ((offset = bis.read(bf)) != -1) {
			System.out.println(new String(bf, 0, offset));
		}
	}

	@Test
	public void testDoc2() throws Exception {
		InputStream istream = new FileInputStream("./tika/tika.docx");
		XWPFDocument docx = new XWPFDocument(istream);
		List<XWPFParagraph> paraGraph = docx.getParagraphs();
		for (XWPFParagraph para : paraGraph) {
			List<XWPFRun> run = para.getRuns();
			for (XWPFRun r : run) {
				int i = 0;
				System.out.println("字体颜色：" + r.getColor());
				System.out.println("字体名称:" + r.getFontFamily());
				System.out.println("字体大小：" + r.getFontSize());
				System.out.println(r.getText(i++));
				System.out.println("粗体？：" + r.isBold());
				System.out.println("斜体？：" + r.isItalic());

			}
		}
	}

	@Test
	public void testDoc3() throws Exception {
		InputStream istream = new FileInputStream("./tika/tika.docx");
		Tika tika = new Tika();
		Reader reader = tika.parse(istream);
		char[] cbuf = new char[1024];
		while((reader.read(cbuf))!=-1){
			System.out.println(new String(cbuf));
		}
	}
}
