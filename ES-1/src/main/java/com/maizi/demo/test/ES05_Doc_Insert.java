package com.maizi.demo.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maizi.demo.content.ESContent;
import com.maizi.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.Date;

@Slf4j
public class ES05_Doc_Insert {


    public static void main(String[] args) throws IOException {
        // 创建高级别客户端对象
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder builder = RestClient.builder(httpHost);
        RestHighLevelClient esClient = new RestHighLevelClient(builder);


        log.info("添加文档");

        User user = new User();
        user.setUserName("John");
        user.setEmail("John@example.com");
        user.setPassword("password123");
        user.setNickName("John_Nick");
        user.setCreateTime(new Date());

        ObjectMapper mapper = new ObjectMapper();
        final String userJson = mapper.writeValueAsString(user);

        try {
            IndexRequest request = new IndexRequest(ESContent.INDEX_NAME).id("1004").source(userJson, XContentType.JSON);
            IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);
            log.info(response.getResult().toString());
        } catch (Exception exception) {
            if (exception.getMessage().contains("Created")) {
                log.info("springboot和springData对es的支持版本是7 https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/");
            } else {
                throw exception;
            }
        }


        // 关闭客户端连接
        esClient.close();
    }
}
