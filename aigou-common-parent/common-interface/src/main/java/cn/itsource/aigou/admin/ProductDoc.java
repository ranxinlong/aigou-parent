package cn.itsource.aigou.admin;

import lombok.Data;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "aigou")
@Data
public class ProductDoc {

    @Id
    @Field(type = FieldType.Integer)
    private Long id;

    /**
     * 关键字+分词索引
     * "商品标题 商品副标题 类型名称 品牌名称"  做分词
     */
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String all;

    //* 商品类型ID
    private Long productTypeId;
    //商品品牌ID
    private Long brandId;
    //最高价格
    private Integer maxPrice;

    /**    
     * 最低价格
     */
    private Integer minPrice;
    //销售数量
    private Integer saleCount;
    //上架时间
    private Long onSaleTime;
    //评论数量
    private Integer commentCount;
    //浏览数量
    private Integer viewCount;

    //商品名称
    @Field(store = true,type = FieldType.Text)
    private String name;

    //商品副标题
    @Field(store = true,type = FieldType.Text)
    private String subName;

    //商品媒体属性
    @Field(store=true,type = FieldType.Text)
    private String medias;

    //显示属性
    @Field(store=true,type = FieldType.Text)
    private String viewProperties;

    //sku属性
    @Field(store=true,type = FieldType.Text)
    private String skuProperties;

}
