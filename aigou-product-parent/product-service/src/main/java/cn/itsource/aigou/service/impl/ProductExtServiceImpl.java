package cn.itsource.aigou.service.impl;

import cn.itsource.aigou.domain.ProductExt;
import cn.itsource.aigou.mapper.ProductExtMapper;
import cn.itsource.aigou.service.IProductExtService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品扩展 服务实现类
 * </p>
 *
 * @author rxl
 * @since 2019-10-17
 */
@Service
public class ProductExtServiceImpl extends ServiceImpl<ProductExtMapper, ProductExt> implements IProductExtService {


    @Override
    public ProductExt findOne(Long productId) {

        return baseMapper.findOne(productId);
    }
}
