package cn.itsource.aigou.vo;

import cn.itsource.aigou.domain.ProductType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来接收面包屑数据的vo类
 */
@Data
public class ProductTypeCrumbVo {

    /**我们在home页输入商品类型的数据
    */
    private ProductType productType;
    /**
     * 输入商品类型数据的同级其他的商品
     */
    private List<ProductType> otherTypes = new ArrayList<>();
}
