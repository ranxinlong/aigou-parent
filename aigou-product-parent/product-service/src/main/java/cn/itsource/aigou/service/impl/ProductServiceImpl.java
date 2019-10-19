package cn.itsource.aigou.service.impl;


import cn.itsource.aigou.domain.Product;
import cn.itsource.aigou.domain.ProductExt;
import cn.itsource.aigou.domain.Specification;
import cn.itsource.aigou.mapper.ProductExtMapper;
import cn.itsource.aigou.mapper.ProductMapper;
import cn.itsource.aigou.mapper.SpecificationMapper;
import cn.itsource.aigou.query.ProductQuery;
import cn.itsource.aigou.service.IProductService;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author rxl
 * @since 2019-10-12
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductExtMapper productExtMapper;

    @Autowired
    private SpecificationMapper specificationMapper;

    @Override
    @Transactional
    public boolean save(Product product) {
        product.setCreateTime(System.currentTimeMillis());
        baseMapper.insert(product);

        ProductExt ext = product.getExt();
        //mybatis自动返回新增数据的主键值
        ext.setProductId(product.getId());
        productExtMapper.insert(ext);
        return true;
    }

    @Override
    @Transactional
    public boolean updateById(Product product) {
        baseMapper.updateById(product);

        //修改ext表里面的数据
        productExtMapper.updateById(product.getExt());
        return true;
    }

    @Override
    @Transactional
    public boolean removeById(Serializable id) {
        //删除商品表的数据
        baseMapper.deleteById(id);

        //同时删除ext表里面对应的数据
        productExtMapper.deleteByProId(id);
        return true;
    }

    @Override
    public PageList<Product> queryPage(ProductQuery query) {
        IPage<Product> queryPage = baseMapper.queryPage(new Page(query.getPage(), query.getRows()), query);
        PageList<Product> pageList = new PageList<>(queryPage.getTotal(), queryPage.getRecords());
        return pageList;
    }

    /**
     * 根据productId获取商品的显示属性
     * @param productId
     * @return
     */
    @Override
    public List<Specification> getviewProperTies(Long productId) {
        List<Specification> specifications = null;
        Product product = baseMapper.selectById(productId);
        String viewProperties = product.getViewProperties();
        //StringUtils.isEmpty(viewProperties)表示商品表里面没有显示属性，需要去viewProperties表里面获取数据
        if (StringUtils.isEmpty(viewProperties)){
            Long productTypeId = product.getProductTypeId();
            //isSku=0表示是显示属性，1表示是sku属性
            specifications = specificationMapper.selectList(
                    new QueryWrapper<Specification>().eq("product_type_id", productTypeId).eq("isSku", 0));
        }else {
            specifications = JSONArray.parseArray(viewProperties, Specification.class);
        }
        return specifications;
    }
    /**
     * 根据productId在product表里面保存商品的显示属性（viewProperty）
     * @param productId
     * @param viewProperty
     * @return
     */
    @Override
    public void saveViewProperties(Long productId, List<Specification> viewProperty) {
        String specification = JSON.toJSONString(viewProperty);
        baseMapper.saveViewProperties(productId,specification);
    }
    /**
     * 根据productId获取商品的sku属性
     * @param productId
     * @return
     */
    @Override
    public List<Specification> getSkuProperties(Long productId) {
        Product product = baseMapper.selectById(productId);
        String skuProperties = product.getSkuProperties();
        List<Specification> specifications = null;
        // StringUtils.isEmpty(skuProperties表示product里面没有改商品sku属性，需要去属性表里面查询
        if (StringUtils.isEmpty(skuProperties)){
            Long productTypeId = product.getProductTypeId();
           specifications = specificationMapper.selectList(new QueryWrapper<Specification>()
                    .eq("isSku", 1).eq("product_type_id", productTypeId));
        }else {
            //原本数据里面已经有SKU属性了，但是是string格式的，需要更改一下格式变为json更是
            specifications = JSONArray.parseArray(skuProperties, Specification.class);
        }
        return specifications;
    }


}
