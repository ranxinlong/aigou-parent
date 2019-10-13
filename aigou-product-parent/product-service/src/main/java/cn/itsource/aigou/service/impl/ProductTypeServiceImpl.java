package cn.itsource.aigou.service.impl;


import cn.itsource.aigou.domain.ProductType;
import cn.itsource.aigou.mapper.ProductTypeMapper;
import cn.itsource.aigou.service.IProductTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author rxl
 * @since 2019-10-12
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    @Override
    public List<ProductType> loadTypeTree() {
        return loadTypeTreeLoop();
    }

    public List<ProductType> loadTypeTreeLoop(){
        //查询所有的数据
        List<ProductType> productTypes = baseMapper.selectList(null);

        //组装数据需要
        List<ProductType> firsLeve1Type = new ArrayList<>();
        Map<Long,ProductType> productTypeMap = new HashMap<>();

        //先遍历全部数据一次，把数据都装到map集合里面去
        for (ProductType productType : productTypes) {
            productTypeMap.put(productType.getId(), productType);
        }
        //再次循环，判断是父菜单还是菜单，如果是子菜单，就找到父菜单并添加子菜单
        for (ProductType productType : productTypes) {
            if(productType.getPid() == 0){//==0表示就是父级菜单，直接保存到list集合里面
                firsLeve1Type.add(productType);
            }else {//如果是子级菜单
                //1.先找到他的父级菜单
                ProductType parent = productTypeMap.get(productType.getPid());
                //parent就是当前菜单的父级菜单
                //2.像实体类里面的children添加当前的菜单
                parent.getChildren().add(productType);
            }
        }
        return firsLeve1Type;
    }
}
