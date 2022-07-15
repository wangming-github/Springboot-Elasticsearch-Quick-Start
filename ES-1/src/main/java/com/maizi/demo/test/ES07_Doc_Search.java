package com.maizi.demo.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maizi.demo.content.ESContent;
import com.maizi.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.Date;

@Slf4j
public class ES07_Doc_Search {


    public static void main(String[] args) throws IOException {
        // 创建高级别客户端对象
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder builder = RestClient.builder(httpHost);
        RestHighLevelClient esClient = new RestHighLevelClient(builder);

         log.info("查询数据");

        try {
            GetRequest request = new GetRequest()//更新请求
                    .index(ESContent.INDEX_NAME)//索引
                    .id("1001");//id
            GetResponse response = esClient.get(request, RequestOptions.DEFAULT);
            log.info(response.getSourceAsString());
        } catch (Exception exception) {
            if (exception.getMessage().contains("OK")) {
                log.info("springboot和springData对es的支持版本是7 https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/");
            } else {
                throw exception;
            }
        }

        log.info("关闭客户端连接...");
        esClient.close();
    }
}
