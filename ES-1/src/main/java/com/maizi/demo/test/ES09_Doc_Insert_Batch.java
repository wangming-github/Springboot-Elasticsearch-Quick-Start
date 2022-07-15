package com.maizi.demo.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maizi.demo.content.ESContent;
import com.maizi.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

@Slf4j
public class ES09_Doc_Insert_Batch {


    public static void main(String[] args) throws IOException {
        // 创建高级别客户端对象
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder builder = RestClient.builder(httpHost);
        RestHighLevelClient esClient = new RestHighLevelClient(builder);


        log.info("批量添加文档");

        User user1 = new User("zhangSan", "男", 20);
        User user2 = new User("liSi", "男", 30);
        User user3 = new User("wangWu", "男", 40);
        User user4 = new User("zhaoLiu", "男", 50);
        User user5 = new User("xiaoMing", "男", 60);
        User user6 = new User("liMei", "女", 70);
        User user3_1 = new User("wangWu1", "男", 80);
        User user3_2 = new User("wangWu12", "男", 90);

        try {
            ObjectMapper mapper = new ObjectMapper();
            IndexRequest request1 = new IndexRequest(ESContent.INDEX_NAME_TEACHER).id("1001").source(mapper.writeValueAsString(user1), XContentType.JSON);
            IndexRequest request2 = new IndexRequest(ESContent.INDEX_NAME_TEACHER).id("1002").source(mapper.writeValueAsString(user2), XContentType.JSON);
            IndexRequest request3 = new IndexRequest(ESContent.INDEX_NAME_TEACHER).id("1003").source(mapper.writeValueAsString(user3), XContentType.JSON);
            IndexRequest request4 = new IndexRequest(ESContent.INDEX_NAME_TEACHER).id("1004").source(mapper.writeValueAsString(user4), XContentType.JSON);
            IndexRequest request5 = new IndexRequest(ESContent.INDEX_NAME_TEACHER).id("1005").source(mapper.writeValueAsString(user5), XContentType.JSON);
            IndexRequest request6 = new IndexRequest(ESContent.INDEX_NAME_TEACHER).id("1006").source(mapper.writeValueAsString(user6), XContentType.JSON);
            IndexRequest request7 = new IndexRequest(ESContent.INDEX_NAME_TEACHER).id("1007").source(mapper.writeValueAsString(user3_1), XContentType.JSON);
            IndexRequest request8 = new IndexRequest(ESContent.INDEX_NAME_TEACHER).id("1008").source(mapper.writeValueAsString(user3_2), XContentType.JSON);


            final BulkRequest bulkRequest = new BulkRequest();
            bulkRequest.add(request1, request2, request3, request4, request5, request6, request7, request8);
            final BulkResponse bulk = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info(bulk.getItems().toString());
            log.info("耗时：" + bulk.getTook().toString());

        } catch (Exception exception) {
            if (exception.getMessage().contains("OK")) {
                log.info("springboot和springData对es的支持版本是7 https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/");
            } else {
                throw exception;
            }
        }


        // 关闭客户端连接
        esClient.close();
    }
}
