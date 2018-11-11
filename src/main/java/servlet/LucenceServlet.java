package servlet;

import com.alibaba.fastjson.JSON;
import domain.SearchResult;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LucenceServlet",urlPatterns = "/search")
public class LucenceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        QueryParser parser = new QueryParser("agreeNum",new SmartChineseAnalyzer());
//        Query query = LongPoint.newRangeQuery("agreeNum",10000,100000);
//        Query query = new TermRangeQuery("agreenum",new BytesRef(1),new BytesRef(10000),true,true);
//        Query query = parser.parse("agreeNum:[10000 TO 100000]");
        String questionKeyword = request.getParameter("question");
        String authorKeyword = request.getParameter("author");
        String contentKeyword = request.getParameter("content");
        String agreeMin = request.getParameter("agreeMin");
        String agreeMax = request.getParameter("agreeMax");
        List<Query> queryList = new ArrayList<>();
        if(questionKeyword!=null && !"".equals(questionKeyword)){
            Query qusetionQuery = new TermQuery(new Term("question",questionKeyword));
            queryList.add(qusetionQuery);
        }
        if(authorKeyword!=null && !"".equals(authorKeyword)){
            Query query = new TermQuery(new Term("author",authorKeyword));
            queryList.add(query);
        }
        if(contentKeyword!=null && !"".equals(contentKeyword)){
            System.out.println(contentKeyword);
            Query query = new TermQuery(new Term("content",contentKeyword));
            queryList.add(query);
        }
        if(agreeMin!=null && !agreeMin.equals("") && !"".equals(agreeMax)){
            Query query = LongPoint.newRangeQuery("agreeNum",Integer.parseInt(agreeMin),Integer.parseInt(agreeMax));
            queryList.add(query);
        }
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        for(Query query:queryList){
            builder.add(query,BooleanClause.Occur.SHOULD);
        }
        BooleanQuery query = builder.build();
        String path = request.getServletContext().getRealPath("")+"answer_index"+File.separator;
        System.out.println(path);
        Directory directory = FSDirectory.open(Paths.get(path));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs topDocs = searcher.search(query,5);
        System.out.println("lenth:"+topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<SearchResult> searchResultList = new ArrayList<>();
        for(ScoreDoc d:scoreDocs){
            Document doc = searcher.doc(d.doc);
            searchResultList.add(doc2SearchResult(doc));
        }
        reader.close();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(searchResultList));
        writer.flush();
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    public SearchResult doc2SearchResult(Document document){
        SearchResult searchResult = new SearchResult();
        searchResult.setId(document.get("id"));
        searchResult.setQuestion(document.get("question"));
        searchResult.setAuthor(document.get("anthor"));
        searchResult.setAuthorUrl(document.get("authorUrl"));
        searchResult.setContent(document.get("content"));
        searchResult.setAgreeNum(document.get("agreeNumStored"));
        return searchResult;
    }
}
