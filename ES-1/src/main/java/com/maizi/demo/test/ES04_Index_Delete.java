package com.maizi.demo.test;

import com.maizi.demo.content.ESContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

@Slf4j
public class ES04_Index_Delete {


    public static void main(String[] args) throws IOException {
        // 创建高级别客户端对象
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder builder = RestClient.builder(httpHost);
        RestHighLevelClient esClient = new RestHighLevelClient(builder);


        DeleteIndexRequest request = new DeleteIndexRequest(ESContent.INDEX_NAME);
        AcknowledgedResponse delete = esClient.indices().delete(request, RequestOptions.DEFAULT);
        log.info("====>删除:" + delete.isAcknowledged());
        esClient.close();
    }
}
