package com.maizi.demo.test;

import com.maizi.demo.content.ESContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.Random;

@Slf4j
public class ES10_Doc_Delete_Batch {


    public static void main(String[] args) throws IOException {
        // 创建高级别客户端对象
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder builder = RestClient.builder(httpHost);
        RestHighLevelClient esClient = new RestHighLevelClient(builder);


        log.info("批量删除文档");

        try {

            DeleteRequest request1 = new DeleteRequest(ESContent.INDEX_NAME).id(String.valueOf(new Random().nextInt(10)));
            DeleteRequest request2 = new DeleteRequest(ESContent.INDEX_NAME).id(String.valueOf(new Random().nextInt(10)));

            final BulkRequest bulkRequest = new BulkRequest().add(request1, request2);//多个request
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
