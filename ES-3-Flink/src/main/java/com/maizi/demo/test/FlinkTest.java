package com.maizi.demo.test;


import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch7.ElasticsearchSink;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlinkTest {


    public static void main(String[] args) throws Exception {

        // 构建Flink环境对象
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Source : 数据的输入端口 需要监听
        DataStreamSource<String> source = env.socketTextStream("localhost", 9999);

        // 使用ESBuilder构建输出
        List<HttpHost> hosts = new ArrayList<>();
        hosts.add(new HttpHost("127.0.0.1", 9201, "http"));
        //S 获取的数据
        //requestIndexer 请求的索引
        ElasticsearchSink.Builder<String> esBuilder = new ElasticsearchSink.Builder<>(hosts, (ElasticsearchSinkFunction<String>) (data, runtimeContext, requestIndexer) -> {
            //数据S处理
            Map<String, String> jsonMap = new HashMap<>();
            jsonMap.put("data", data);
            //存储
            IndexRequest indexRequest = Requests.indexRequest();
            indexRequest.index("flink-index");
            indexRequest.id("9001");
            indexRequest.source(jsonMap);
            requestIndexer.add(indexRequest);
        });

        // Sink:数据的输出 批处理数量（1请求一条处理一条）
        esBuilder.setBulkFlushMaxActions(1);
        source.addSink(esBuilder.build());

        // 执行操作(作业名称)
        env.execute("flink-es");

    }
}
