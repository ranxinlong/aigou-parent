package cn.itsource.aigou.service.impl;


import cn.itsource.aigou.domain.Product;
import cn.itsource.aigou.mapper.ProductMapper;
import cn.itsource.aigou.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
