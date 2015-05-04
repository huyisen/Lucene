package org.yisen.share.lucene04_Filter;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
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

/**
 * Lucene 简单的工具类，封装了Lucene的一些常用操作
 * 
 * @author 胡义森
 * @date 2015年3月30日
 */
public class LuceneUtils {
	
	public static IndexReader indexReader = null;
	public static Directory directory = null;
	public static IndexSearcher indexSearcher = null;
	
	static{
		try {
			directory = FSDirectory.open(new File("./filter/"));
			indexReader = IndexReader.open(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建索引
	 * 
	 * @author 胡义森
	 * @throws IOException IO异常
	 * @date 2015年3月30日
	 */
	public static void Index() throws IOException{
		IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
		Document doc = null;
		for (int i = 1; i <= 9; i++) {
			doc = new Document();
			doc.add(new Field("id", i+"",Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
			doc.add(new Field("title", i+"title"+i,Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
			doc.add(new Field("content", i+"this is Document "+i, Field.Store.YES,Field.Index.ANALYZED_NO_NORMS));
			indexWriter.addDocument(doc);
		}
		indexWriter.close();
	}
	
	public static void search(){
		try {
			IndexSearcher searcher = getIndexSearcher();
			TermQuery query = new TermQuery(new Term("content", "document"));
			TopDocs tds = searcher.search(query,new DeleteFilter(new SimpleFilterData()), 10);
			ScoreDoc[] sds = tds.scoreDocs;
			for (ScoreDoc sd : sds) {
				Document doc = searcher.doc(sd.doc);
				System.out.println("id : "+ doc.get("id") +",| title : "+doc.get("title")+",| content :"+ doc.get("content"));
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 获取单例的IndexSearcher
	 * 
	 * @author 胡义森
	 * @date 2015年3月30日
	 * @return
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	public static IndexSearcher getIndexSearcher() throws CorruptIndexException, IOException{
		return new IndexSearcher(indexReader);
	}
	
}
