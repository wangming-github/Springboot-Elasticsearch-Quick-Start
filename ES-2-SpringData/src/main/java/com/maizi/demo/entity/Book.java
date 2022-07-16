package com.maizi.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author 池海
 * @version 1.0.0
 * @description: TODO
 * @date 2021/12/16
 * * 注解说明
 * * @Document：在类级别应用，以指示该类是映射到数据库的候选对象。最重要的属性是：
 * *      indexName：用于存储此实体的索引的名称。它可以包含SpEL模板表达式，例如 "log-#{T(java.time.LocalDate).now().toString()}"
 * *      type：映射类型。如果未设置，则使用小写的类的简单名称。（从版本4.0开始不推荐使用）
 * *      shards：索引的分片数。
 * *      replicas：索引的副本数。
 * *      refreshIntervall：索引的刷新间隔。用于索引创建。默认值为“ 1s”。
 * *      indexStoreType：索引的索引存储类型。用于索引创建。默认值为“ fs”。
 * *      createIndex：标记是否在存储库引导中创建索引。默认值为true。请参见使用相应的映射自动创建索引
 * *      versionType：版本管理的配置。默认值为EXTERNAL。
 * * @Id：在字段级别应用，以标记用于标识目的的字段。
 * * @Transient：默认情况下，存储或检索文档时，所有字段都映射到文档，此注释不包括该字段。
 * * @PersistenceConstructor：标记从数据库实例化对象时要使用的给定构造函数，甚至是受保护的程序包。构造函数参数按名称映射到检索到的Document中的键值。
 * * @Field：在字段级别应用并定义字段的属性，大多数属性映射到各自的Elasticsearch映射定义（以下列表不完整，请查看注释Javadoc以获得完整参考）：
 * *      name：字段名称，它将在Elasticsearch文档中表示，如果未设置，则使用Java字段名称。
 * *      type：字段类型，可以是Text, Keyword, Long, Integer, Short, Byte, Double, Float, Half_Float,
 * Scaled_Float, Date, Date_Nanos, Boolean, Binary, Integer_Range, Float_Range, Long_Range, Double_Range,
 * Date_Range, Ip_Range, Object, Nested, Ip, TokenCount, Percolator, Flattened, Search_As_You_Type。请参阅Elasticsearch映射类型
 * *      format和日期类型的pattern定义。必须为日期类型定义format
 * *      store：标记是否将原始字段值存储在Elasticsearch中，默认值为false。
 * *      analyzer，searchAnalyzer，normalizer用于指定自定义分析和正规化。
 * * @GeoPoint：将字段标记为geo_point数据类型。如果字段是GeoPoint类的实例，则可以省略。
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "longzhu")
@Builder
@Setting(settingPath = "/bookSettings.json")
public class Book {

    @Id
    long id;

    /**
     * IP所属地域  如，日本、大陆、欧美 可组合
     */
    @Field(type = FieldType.Keyword, copyTo = "all")
    private String territory;

    /**
     * 所属ip 如，龙珠、海贼、火影
     */
    @Field(type = FieldType.Keyword, copyTo = "all")
    private String ip;

    /**
     * 分类 如，景品 GK  版权  手办
     */
    @Field(type = FieldType.Keyword, copyTo = "all")
    private String type;

    /**
     * 产品正式名称
     */
    @CompletionField(analyzer = "completion_analyzer", searchAnalyzer = "ik_smart")
    private String name;

    /**
     * 国语简称，即玩家起的简称
     */
    @CompletionField(analyzer = "completion_analyzer", searchAnalyzer = "ik_smart")
    private String nickname;

    /**
     * 简介
     */
    @Field(type = FieldType.Text, analyzer = "text_analyzer")
    private String description;

    /**
     * 生产商
     */
    @Field(type = FieldType.Text, analyzer = "text_analyzer", copyTo = "all")
    private String production;

    /**
     * 生产时间
     */
    @Field(type = FieldType.Date, format = DateFormat.year_month_day)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd", timezone = "GMT+8")
    private LocalDate productionTime;

    /**
     * 出厂价格 默认单位人民币 -1.00 代表抽赏  -2.00 代表赠送
     */
    @Field(type = FieldType.Double, copyTo = "all")
    private Double price;

    /**
     * 发售形式
     */
    @Field(type = FieldType.Text)
    private String releaseForm;

    /**
     * 发售地区
     */
    @Field(type = FieldType.Text)
    private String salesArea;

    /**
     * 发售数量 -1.00 代表未知
     */
    @Field(type = FieldType.Text)
    private String indultNumber;

    /**
     * 最近价格  -1.00 代表未知
     */
    @Field(type = FieldType.Double, index = false)
    private Double currentPrice;

    /**
     * 商品比例 直接存储数字8代表1/8 6代表 1/6
     */
    @Field(type = FieldType.Text, copyTo = "all")
    private String ratio;

    /**
     * 商品高度
     */
    @Field(type = FieldType.Double)
    private Double height;

    /**
     * 商品材质
     */
    @Field(type = FieldType.Text, copyTo = "all")
    private String material;

    /**
     * 商品重量
     */
    @Field(type = FieldType.Double)
    private Double weight;

    /**
     * 总评分
     */
    @Field(type = FieldType.Double)
    private Double grade;

    /**
     * 档案创始人，即首个提交完善资料的用户
     */
    @Field(type = FieldType.Long, index = false)
    private long originatorId;

    /**
     * 元气贡献者， 即后续进行信息补充、纠错、数据更新的用户
     */
    @Field(type = FieldType.Keyword)
    private long[] contributors;

}
