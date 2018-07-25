package net.canway.service

import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.common.xcontent.XContentBuilder
import org.elasticsearch.common.xcontent.XContentFactory
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.reindex.DeleteByQueryAction
import org.elasticsearch.rest.RestStatus
import org.elasticsearch.transport.client.PreBuiltTransportClient
import org.springframework.stereotype.Service
import java.io.IOException
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.*


@Service
class DocmentService {


    //指定ES的集群
    internal var settings = Settings.builder().put("cluster.name", "my-application").build()

    //创建访问ES的客户端
    internal lateinit var client: TransportClient

    init {
        try {
            client = PreBuiltTransportClient(settings)
                    .addTransportAddress(InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300))
        } catch (e: UnknownHostException) {
            e.printStackTrace()
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

    fun createIndex(): String {

        val cib = client.admin().indices().prepareCreate(article)
        return "success"
    }


    //添加书籍
    fun addDocument(): RestStatus {


        /**
         * 在这里添加一本书的信息
         *
         */
        var mapping: XContentBuilder? = null
        try {
            mapping = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("id", "1")
                    .field("title", "Java编程思想")
                    .field("content", "“Thinking in Java should be read cover to cover by every Java programmer, then kept close at hand for frequent reference. The exercises are challenging, and the chapter on Collections is superb! Not only did this book help me to pass the Sun Certified Java Programmer exam; it’s also the first book I turn to whenever I have a Java question.”")
                    .field("postdate", "2018-05-20")
                    .field("url", "www.baidu.com")
                    .endObject()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val response = client.prepareIndex("my_index", "blog", "1001")
                .setSource(mapping).get()

        return response.status()
    }

    //删除书籍
    fun deleteBook(): Int {
        val response = client.prepareDelete("my_index", "blog", "1001").get()
        return response.status().status
    }

    //根据关键词删除书籍
    fun deleteBookByKeyword(): Long {
        val res = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("content", "java"))
                .source("my_index")
                .get()
        return res.deleted

    }

    //查询
    fun query(): String {
        val response = client.prepareGet("my_index", "blog", "1001").execute().actionGet()
        return response.sourceAsString
    }

    //查询所有
    fun queryAll(): List<*> {
        val qb = QueryBuilders.matchAllQuery()
        val sr = client.prepareSearch("my_index")
                .setQuery(qb)
                .setSize(2).get()
        val hits = sr.hits
        val list = ArrayList<Any>()
        for (hit in hits) {
            list.add(hit.sourceAsMap)
        }
        return list
    }

    //根据条件查询
    fun queryByItem(): List<*> {
        val qb = QueryBuilders.matchQuery("content", "java")
        val response = client.prepareSearch("my_Index")
                .setQuery(qb)
                .setSize(3).get()
        val hits = response.hits

        val list = ArrayList<Any>()
        for (hit in hits) {
            list.add(hit.sourceAsMap)
        }
        return list
    }

    companion object {
        private val article = "my_index1"
    }
}
