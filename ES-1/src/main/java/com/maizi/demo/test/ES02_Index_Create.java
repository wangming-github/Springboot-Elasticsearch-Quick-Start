package com.maizi.demo.test;

import com.maizi.demo.content.ESContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;

@Slf4j
public class ES02_Index_Create {


    public static void main(String[] args) throws IOException {
        // 创建高级别客户端对象
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder builder = RestClient.builder(httpHost);
        RestHighLevelClient esClient = new RestHighLevelClient(builder);


        //创建索引
        CreateIndexResponse createIndexResponse = null;
        try {
            CreateIndexRequest user = new CreateIndexRequest(ESContent.INDEX_NAME);
            createIndexResponse = esClient.indices().create(user, RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException e) {
            log.warn("创建索引出错！！！");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //响应状态
        boolean acknowledged = createIndexResponse.isAcknowledged();
        //查询 http://127.0.0.1:9200/_cat/indices?v
        log.info("Create Index:{}", acknowledged);


        // 关闭客户端连接
        esClient.close();
    }
}
