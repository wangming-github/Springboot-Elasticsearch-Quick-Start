package com.maizi.demo.test;

import com.maizi.demo.dao.ProductDao;
import com.maizi.demo.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)// MethodSorters.NAME_ASCENDING  按照方法名字顺序执行
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexProductTest {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @Autowired
    private ProductDao productDao;

    @Test
    public void A_deleteIndex() {
        //创建索引，系统初始化会自动创建索引
        boolean flg = restTemplate.indexOps(Product.class).delete();
        System.out.println("删除索引 = " + flg);
    }


    /**
     * 创建索引并增加映射配置
     */

    @Test
    public void B_createIndex() {
        if (!restTemplate.indexOps(Product.class).exists()) {
            restTemplate.indexOps(Product.class).create();
            restTemplate.indexOps(Product.class).putMapping();
        }
    }

    @Test
    public void C_insertDoc() {

        List<String> list = Arrays.asList("华为", "小米", "oppo", "vivo", "魅族", "锤子", "360", "锤子", "美图", "联想", "华硕", "波导", "苹果", "三星", "松下", "索尼", "富士通");

        final Random random = new Random();

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            final int i1 = random.nextInt(list.size() - 1);
            products.add(Product.builder()//
                    .id(Long.parseLong(String.valueOf(i)))//
                    .title(list.get(i1) + "手机")//
                    .price(Math.floor(random.nextDouble() * 100000) / 10)//
                    .images("https://www.freesion.com/article/59481222940/").build());
        }
        productDao.saveAll(products);
    }


    @Test
    public void D_find() {
        //productDao.findAll().forEach(System.out::println);
        productDao.findAll().forEach(System.out::println);
    }

    @Test
    public void E_find() {
        log.info(productDao.findById(1L).toString());
    }

    @Test
    public void F_update() {
        // 其他值不设置将为NULL [Product(id=1, title=小米 2 手机, category=null, price=null, images=null)]
        productDao.save(Product.builder().id(1L).title("小米2手机").build());
    }

    @Test
    public void G_delete() {
        // 其他值不设置将为NULL [Product(id=1, title=小米 2 手机, category=null, price=null, images=null)]
        productDao.delete(Product.builder().id(1L)// ID 并且 title 都符合条件才能删除
                .title("小米2手机").build());
    }

    //分页查询
    @Test
    public void H_findByPageable() {
        //设置排序(排序方式，正序还是倒序，排序的 id)
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(1, 5, sort);
        //分页查询 遍历打印日志
        productDao.findAll(pageRequest).forEach(System.out::println);
    }


    /**
     * restTemplate高级查询:分页+排序
     */
    @Test
    public void I_findByPageable() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery query = queryBuilder
                // 条件
                .withQuery(QueryBuilders.multiMatchQuery("小米", "title"))
                // 分页
                .withPageable(PageRequest.of(0, 5))
                // 根据查询结果按ID倒序
                .withSorts(SortBuilders.fieldSort("id").order(SortOrder.DESC))
                // 构建
                .build();

        SearchHits<Product> searchHits = restTemplate.search(query, Product.class);
        searchHits.stream().iterator().forEachRemaining(System.out::println);
    }

    /**
     * restTemplate高级查询:分页+排序
     */
    @Test
    public void J_findByPageableV2() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery query = queryBuilder
                // 条件
                .withQuery(QueryBuilders.multiMatchQuery("小米", "title"))
                // 简写 分页+多字段排序(汉字不行)
                .withPageable(PageRequest.of(0, 5, Sort.Direction.ASC, "id", "price"))
                //
                .build();

        SearchHits<Product> searchHits = restTemplate.search(query, Product.class);
        searchHits.stream().iterator().forEachRemaining(System.out::println);
    }


    /**
     * restTemplate高级查询:高亮
     */
    @Test
    public void K_find() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery query = queryBuilder
                // 条件
                .withQuery(QueryBuilders.multiMatchQuery("小米", "title"))
                // 简写 分页+多字段排序
                .withPageable(PageRequest.of(0, 5, Sort.Direction.DESC, "id", "price"))
                // 默认样式
                //.withHighlightFields(new HighlightBuilder.Field("title"))
                // 自定义样式
                .withHighlightBuilder(new HighlightBuilder().field("title").preTags("<p style='color:yellow'> ").postTags("<p>"))
                // 构建
                .build();
        SearchHits<Product> searchHits = restTemplate.search(query, Product.class);
        searchHits.stream().iterator().forEachRemaining(System.out::println);

    }
}
