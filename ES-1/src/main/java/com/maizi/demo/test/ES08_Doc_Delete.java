package com.maizi.demo.test;

import com.maizi.demo.content.ESContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

@Slf4j
public class ES08_Doc_Delete {


    public static void main(String[] args) throws IOException {
        // 创建高级别客户端对象
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder builder = RestClient.builder(httpHost);
        RestHighLevelClient esClient = new RestHighLevelClient(builder);

        log.info("删除数据");

        try {
            DeleteRequest request = new DeleteRequest()//更新请求
                    .index(ESContent.INDEX_NAME)//索引
                    .id("1001");//id
            DeleteResponse response = esClient.delete(request, RequestOptions.DEFAULT);
            log.info(response.toString());
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
