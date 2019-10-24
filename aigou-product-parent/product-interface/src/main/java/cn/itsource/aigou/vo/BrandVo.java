package cn.itsource.aigou.vo;

import cn.itsource.aigou.domain.Brand;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Data
public class BrandVo {

    //当前商品类型的全部商品
    private List<Brand> brands = new ArrayList<>();
    //商品的大写首写字母
    private Set<String> letters = new TreeSet<>();


}
