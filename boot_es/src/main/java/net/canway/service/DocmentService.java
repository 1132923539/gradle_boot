package net.canway.service;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


@Service
public class DocmentService {
    private final static String article = "my_index1";


    //指定ES的集群
    Settings settings = Settings.builder().put("cluster.name", "my-application").build();

    //创建访问ES的客户端
    TransportClient client;

    {
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


//    @Test
//    public void test() throws IOException {
//        System.out.println("----------------------------------------------");
////        System.out.println(addDocument());
////        System.out.println(deleteBook());
////        System.out.println(query());
//        System.out.println(queryAll());
//    }

    //创建索引

    public String createIndex() {

        CreateIndexRequestBuilder cib = client.admin().indices().prepareCreate(article);
        return "success";
    }


    //添加书籍
    public RestStatus addDocument() {


        /**
         * 在这里添加一本书的信息
         *
         */
        XContentBuilder mapping = null;
        try {
            mapping = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("id", "1")
                    .field("title", "Java编程思想")
                    .field("content", "“Thinking in Java should be read cover to cover by every Java programmer, then kept close at hand for frequent reference. The exercises are challenging, and the chapter on Collections is superb! Not only did this book help me to pass the Sun Certified Java Programmer exam; it’s also the first book I turn to whenever I have a Java question.”")
                    .field("postdate", "2018-05-20")
                    .field("url", "www.baidu.com")
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        IndexResponse response = client.prepareIndex("my_index", "blog", "1001")
                .setSource(mapping).get();

        return response.status();
    }

    //删除书籍
    public int deleteBook() {
        DeleteResponse response = client.prepareDelete("my_index", "blog", "1001").get();
        return response.status().getStatus();
    }

    //根据关键词删除书籍
    public long deleteBookByKeyword() {
        BulkByScrollResponse res = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("content", "java"))
                .source("my_index")
                .get();
        return res.getDeleted();

    }

    //查询
    public String query() {
        GetResponse response = client.prepareGet("my_index", "blog", "1001").execute().actionGet();
        return response.getSourceAsString();
    }

    //查询所有
    public List queryAll() {
        QueryBuilder qb = QueryBuilders.matchAllQuery();
        SearchResponse sr = client.prepareSearch("my_index")
                .setQuery(qb)
                .setSize(2).get();
        SearchHits hits = sr.getHits();
        List list = new ArrayList();
        for (SearchHit hit : hits
                ) {
            list.add(hit.getSourceAsMap());
        }
        return list;
    }

    //根据条件查询
    public List queryByItem() {
        QueryBuilder qb = QueryBuilders.matchQuery("content", "java");
        SearchResponse response = client.prepareSearch("my_Index")
                .setQuery(qb)
                .setSize(3).get();
        SearchHits hits = response.getHits();

        List list = new ArrayList();
        for (SearchHit hit : hits
                ) {
            list.add(hit.getSourceAsMap());
        }
        return list;
    }
}
