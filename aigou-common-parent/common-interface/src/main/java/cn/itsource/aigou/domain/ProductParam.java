package cn.itsource.aigou.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductParam {

    private String keyword;
    private Long productTypeId;
    private Long brandId;
    //最低最高价格
    private Integer minPrice;
    private Integer maxPrice;
    //排序的列
    private String sortFilId;
    //排序的方式
    private String sortType;
    private Integer page;
    private Integer rows;
}
