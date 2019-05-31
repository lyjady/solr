package com.example.solr;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SolrApplicationTests {

    public static IndexWriter getIndexWriter() throws IOException {
        Directory directory = FSDirectory.open(new File("D:/index/indexs/"));
        //分析器
        Analyzer analyzer = new StandardAnalyzer();
        //配置IndexWriter
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);
        return indexWriter;
    }

    public static IndexSearcher getIndexSearcher() throws IOException {
        Directory directory = FSDirectory.open(new File("D:/index/indexs/"));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }

    public static void printResult(ScoreDoc[] scoreDocs, IndexSearcher indexSearcher) throws IOException {
        int count = 1;
        for (ScoreDoc doc : scoreDocs) {
            int id = doc.doc;
            Document document = indexSearcher.doc(id);
            System.out.println("第" + count + "个文档");
            System.out.println("文件名称:" + document.get("fileName"));
            System.out.println("文件内容:" + document.get("fileContent"));
            System.out.println("文件路径:" + document.get("filePath"));
            System.out.println("文件大小:" + document.get("fileSize"));
            System.out.println("--------------------------------------------");
            count++;
        }
        indexSearcher.getIndexReader().close();
    }

    @Test
    public void createIndex() throws IOException {
        //IndexWriter 指定的Directory(文档路劲)
        Directory directory = FSDirectory.open(new File("D:/index/indexs/"));
        //分析器
        Analyzer analyzer = new StandardAnalyzer();
        //配置IndexWriter
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);

        Document document = null;
        File path = new File("D:/index/file/");
        File[] files = path.listFiles();
        for (File file : files) {
            document = new Document();
            String fileContent  = FileUtils.readFileToString(file, "UTF-8");
            Field fieldContent = new TextField("fileContent", fileContent, Field.Store.YES);
            long fileSize = FileUtils.sizeOf(file);
            Field fieldSize = new LongField("fileSize", fileSize, Field.Store.YES);
            String filePath = file.getPath();
            Field fieldPath = new StoredField("filePath", filePath);
            String fileName = file.getName();
            Field fieldName = new TextField("fileName", fileName, Field.Store.YES);
            document.add(fieldContent);
            document.add(fieldSize);
            document.add(fieldPath);
            document.add(fieldName);
            indexWriter.addDocument(document);
        }
        indexWriter.unlock(directory);
        indexWriter.close();
    }

    @Test
    public void searchDocument() throws IOException {
        Directory directory = FSDirectory.open(new File("D:/index/indexs/"));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        Query query = new TermQuery(new Term("fileContent", "spring"));
        TopDocs result = searcher.search(query, 10);
        ScoreDoc[] scoreDocs = result.scoreDocs;
        for (ScoreDoc doc : scoreDocs) {
            int id = doc.doc;
            Document document = searcher.doc(id);
            System.out.println("文件名称:" + document.get("fileName"));
            System.out.println("文件内容:" + document.get("fileContent"));
            System.out.println("文件路径:" + document.get("filePath"));
            System.out.println("文件大小:" + document.get("fileSize"));
            System.out.println("--------------------------------------------");
        }
    }

    @Test
    public void matchAllQuery() throws IOException {
        IndexSearcher indexSearcher = getIndexSearcher();
        Query query = new MatchAllDocsQuery();
        TopDocs result = indexSearcher.search(query, 10);
        ScoreDoc[] scoreDocs = result.scoreDocs;
        printResult(scoreDocs, indexSearcher);
    }

    @Test
    public void rangeQuery() throws IOException {
        IndexSearcher indexSearcher = getIndexSearcher();
        Query query = NumericRangeQuery.newLongRange("fileSize", 500L, 700L, true, true);
        System.out.println(query);
        TopDocs result = indexSearcher.search(query, 10);
        ScoreDoc[] scoreDocs = result.scoreDocs;
        printResult(scoreDocs, indexSearcher);
    }

    @Test
    public void booleanQuery() throws IOException {
        IndexSearcher indexSearcher = getIndexSearcher();
        BooleanQuery booleanQuery = new BooleanQuery();
        Query query1 = new TermQuery(new Term("fileContent", "spring"));
        Query query2 = new TermQuery(new Term("fileContent", "framework"));
        booleanQuery.add(query1, BooleanClause.Occur.MUST);
        booleanQuery.add(query2,BooleanClause.Occur.SHOULD);
        TopDocs result = indexSearcher.search(booleanQuery, 10);
        ScoreDoc[] scoreDocs = result.scoreDocs;
        printResult(scoreDocs, indexSearcher);
    }

    @Test
    public void queryParser() throws Exception {
        IndexSearcher indexSearcher = getIndexSearcher();
        QueryParser queryParser = new QueryParser("fileContent", new StandardAnalyzer());
        Query parse = queryParser.parse("-fileContent:spring");
//        Query parse = queryParser.parse("*:*");
//        Query parse = queryParser.parse("fileSize:[500 TO 700]);
        TopDocs result = indexSearcher.search(parse, 10);
        ScoreDoc[] scoreDocs = result.scoreDocs;
        printResult(scoreDocs, indexSearcher);
    }

}
