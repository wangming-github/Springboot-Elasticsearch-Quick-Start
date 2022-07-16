package com.maizi.demo.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author maizi
 * @Document: 代表一个文档记录
 * <p>
 * ​ indexName: 用来指定索引名称
 * <p>
 * ​ type: 用来指定索引类型（7.0后不用指定类型）
 */
@Data
@Builder
@Document(indexName = "product")
public class Product {

    /**
     * 商品唯一标识
     *
     * @Id: 用来将对象中id和ES中_id映射
     */
    @Id
    private Long id;

    /**
     * 商品名称
     *
     * Field注解，指定字段类型，以及是否存储，还有分词器类型等；
     * 这里注意，在查询时，查询参数分词后是and关系，不是or的关系。
     */
    @Field(type = FieldType.Text)
    private String title;


    /**
     * 分类名称
     */
    @Field(type = FieldType.Keyword)//不分词
    private String category;

    /**
     * 商品价格
     */
    @Field(type = FieldType.Double)
    private Double price;

    /**
     * 图片地址
     */
    @Field(type = FieldType.Keyword, index = false)//不分词 不查询
    private String images;//图片地址

}
