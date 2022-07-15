package com.maizi.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

@Slf4j
public class ES03_Index_Search {


    public static void main(String[] args) throws IOException {
        // 创建高级别客户端对象
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder builder = RestClient.builder(httpHost);
        RestHighLevelClient esClient = new RestHighLevelClient(builder);


        //查询索引
        GetIndexRequest request = new GetIndexRequest("student");
        GetIndexResponse getIndexResponse = esClient.indices().get(request, RequestOptions.DEFAULT);

        log.info("====>别名:" + getIndexResponse.getAliases().toString());
        getIndexResponse.getMappings().forEach((k, v) -> System.out.println("====>映射:" + k + " =" + v));
        log.info("====>映射:" + getIndexResponse.getSettings().toString());

        esClient.close();
    }
}
