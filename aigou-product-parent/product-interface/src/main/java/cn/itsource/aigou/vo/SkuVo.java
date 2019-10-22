package cn.itsource.aigou.vo;

import cn.itsource.aigou.domain.Specification;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 这个vo主要用来接收前端的sku属性和数组的
 */
@Data
public class SkuVo {

    //对应前端里面的SkuProperties
    private List<Specification> skuproperties;
    //对应前端里面的skus
    private List<Map<String,String>> skus;
}
