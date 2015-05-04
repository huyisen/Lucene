package org.yisen.share.lucene04;

import java.io.File;
import java.io.FileReader;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

public class MyTest {
	
	@Test
	public void testIndex() throws Exception {
		MyTest m = new MyTest();
		m.index();
	}
	
	@Test
	public void testSearcher() throws Exception {
		MyTest m = new MyTest();
		m.seacher();
	}
	
	public void index(){
		try {
			Directory dir = FSDirectory.open(new File("./myIndex"));
			IndexWriter writer = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35, new MMSegAnalyzer()));
			File files = new File("./cn");
			Document doc = null;
			int i = 0;
			for (File file : files.listFiles()) {
				doc = new Document();
				doc .add(new Field("content", new FileReader(file)));
				doc.add(new Field("id",i+"", Field.Store.YES, Field.Index.ANALYZED_NO_NORMS));
				doc.add(new Field("filename",file.getName(),Field.Store.YES,Field.Index.ANALYZED_NO_NORMS));
				i++;
				writer.addDocument(doc);
			}
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void seacher(){
		Directory dir;
		try {
			dir = FSDirectory.open(new File("./myIndex"));
			IndexReader reader = IndexReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);
			TermQuery query = new TermQuery(new Term("content","啊"));
			TopDocs tds= searcher.search(query, 10);
			ScoreDoc[] sds = tds.scoreDocs;
			
			for (ScoreDoc sd : sds) {
				Document doc = searcher.doc(sd.doc);
				System.out.println(doc.get("id")+":"+doc.get("filename"));
			}
			searcher.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
