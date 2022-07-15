package com.maizi.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

@Slf4j
public class ES01_Client {


    public static void main(String[] args) throws IOException {
        // 创建高级别客户端对象
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder builder = RestClient.builder(httpHost);
        RestHighLevelClient esClient = new RestHighLevelClient(builder);


        log.info("创建高级别客户端对象 完成");

        // 关闭客户端连接
        esClient.close();
    }
}
