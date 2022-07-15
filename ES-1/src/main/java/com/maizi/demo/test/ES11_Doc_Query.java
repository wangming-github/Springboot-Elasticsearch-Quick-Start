package com.maizi.demo.test;

import com.maizi.demo.content.ESContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

@Slf4j
public class ES11_Doc_Query {


    private static SearchHits hits;

    public static void main(String[] args) throws IOException {
        // 创建高级别客户端对象
        HttpHost httpHost = new HttpHost("localhost", 9200, "http");
        RestClientBuilder builder = RestClient.builder(httpHost);
        RestHighLevelClient esClient = new RestHighLevelClient(builder);

        log.info("高级查询 全量查询....");

        //1.全量查询
        //SearchRequest searchRequest = new SearchRequest();
        //searchRequest.indices(ESContent.INDEX_NAME_TEACHER);
        //searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        //final SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);

        //2.条件查询 查询年龄 30
        //SearchRequest searchRequest = new SearchRequest();
        //searchRequest.indices(ESContent.INDEX_NAME_TEACHER);
        //searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age", 30)));
        //final SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);


        //3.分页查询
        //final SearchSourceBuilder query = new SearchSourceBuilder();
        //query.from(2);//起始条 (当前页码-1)*size
        //query.size(2);//每页数量
        //query.query(QueryBuilders.matchAllQuery());
        ////创建请求，并为其设置索引，查询条件。
        //SearchRequest searchRequest = new SearchRequest();
        //searchRequest.indices(ESContent.INDEX_NAME_TEACHER);
        //searchRequest.source(query);
        //final SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);


        //4.结果排序
        //final SearchSourceBuilder query = new SearchSourceBuilder();
        //query.sort("age", SortOrder.DESC);//按照年龄倒序
        //query.query(QueryBuilders.matchAllQuery());
        ////创建请求，并为其设置索引，查询条件。
        //SearchRequest searchRequest = new SearchRequest();
        //searchRequest.indices(ESContent.INDEX_NAME_TEACHER);
        //searchRequest.source(query);
        //final SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);


        //5.过滤字段
        //构造查询条件
        //final SearchSourceBuilder query = new SearchSourceBuilder();
        //String[] includes = {"userName"};
        //String[] excludes = {"age"};
        //query.fetchSource(includes, excludes);
        //query.query(QueryBuilders.matchAllQuery());
        ////创建请求，并为其设置索引，查询条件。
        //SearchRequest searchRequest = new SearchRequest();
        //searchRequest.indices(ESContent.INDEX_NAME_TEACHER);
        //searchRequest.source(query);
        //final SearchResponse response = esClient.search(searchRequest, RequestOptions.DEFAULT);


        //6.多条件查询（组合查询）
        //BoolQueryBuilder query = QueryBuilders.boolQuery();//构造组合条件
        ////boolQuery.must(QueryBuilders.matchQuery("age", 30));//年龄必须是30
        ////boolQuery.must(QueryBuilders.matchQuery("sex", "男"));//性别必须是男
        ////boolQuery.mustNot(QueryBuilders.matchQuery("sex", "男"));//性别必须不是男
        //query.should(QueryBuilders.matchQuery("age", 30));//OR
        //query.should(QueryBuilders.matchQuery("age", 40));//OR
        //创建请求，并为其设置索引，查询条件
        //SearchRequest request = new SearchRequest();
        //request.indices(ESContent.INDEX_NAME_TEACHER);
        //request.source(new SearchSourceBuilder().query(query));
        //final SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);


        //7.范围查询
        //RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");
        //rangeQueryBuilder.gte("30");
        //rangeQueryBuilder.lte("50");
        //创建请求，并为其设置索引，查询条件
        //SearchRequest request = new SearchRequest();
        //request.indices(ESContent.INDEX_NAME_TEACHER);
        //request.source(new SearchSourceBuilder().query(rangeQueryBuilder));
        //SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);


        //8.模糊查询  com.maizi.demo.test.ES09_Doc_Insert_Batch.main
        //FuzzyQueryBuilder query = QueryBuilders.fuzzyQuery("userName", "wangwu");
        //query.fuzziness(Fuzziness.TWO);//精准度
        //
        ////创建请求，并为其设置索引，查询条件
        //SearchRequest request = new SearchRequest();
        //request.indices(ESContent.INDEX_NAME_TEACHER);
        //request.source(new SearchSourceBuilder().query(query));
        //SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);


        //9.高亮查询
        //TermQueryBuilder query = QueryBuilders.termQuery("userName", "zhangsan");
        //HighlightBuilder highlightBuilder = new HighlightBuilder();
        //highlightBuilder.preTags("<font color=\"red\">");
        //highlightBuilder.postTags("</font>");
        //highlightBuilder.field("userName");
        //
        //SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(query);
        //searchSourceBuilder.highlighter(highlightBuilder);
        //
        //SearchRequest request = new SearchRequest();
        //request.indices(ESContent.INDEX_NAME_TEACHER);
        //request.source(searchSourceBuilder);
        //SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);


        //10.聚合查询
        //SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //AggregationBuilder aggregationBuilder = AggregationBuilders.max("MaxAge").field("age");
        //searchSourceBuilder.aggregation(aggregationBuilder);
        //SearchRequest request = new SearchRequest();
        //request.indices(ESContent.INDEX_NAME_TEACHER);
        //request.source(searchSourceBuilder);
        //SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        //10.聚合查询
        //SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //AggregationBuilder aggregationBuilder = AggregationBuilders.max("MaxAge").field("age");
        //searchSourceBuilder.aggregation(aggregationBuilder);
        //SearchRequest request = new SearchRequest();
        //request.indices(ESContent.INDEX_NAME_TEACHER);
        //request.source(searchSourceBuilder);
        //SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);


        //11.分组查询
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");
        searchSourceBuilder.aggregation(aggregationBuilder);
        SearchRequest request = new SearchRequest();
        request.indices(ESContent.INDEX_NAME_TEACHER);
        request.source(searchSourceBuilder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);


        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            log.info("====>" + hit.getSourceAsString());
        }

        log.info("总数" + hits.getTotalHits().toString());
        log.info("耗时" + response.getTook());

        log.info("操作结束....");
        esClient.close();
    }
}
