package cn.itsource.aigou.service.impl;


import cn.itsource.aigou.domain.Product;
import cn.itsource.aigou.domain.ProductExt;
import cn.itsource.aigou.mapper.ProductExtMapper;
import cn.itsource.aigou.mapper.ProductMapper;
import cn.itsource.aigou.query.ProductQuery;
import cn.itsource.aigou.service.IProductService;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

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
}
