package cn.itsource.aigou.service;

import cn.itsource.aigou.domain.ProductExt;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品扩展 服务类
 * </p>
 *
 * @author rxl
 * @since 2019-10-17
 */
public interface IProductExtService extends IService<ProductExt> {

    ProductExt findOne(Long productId);
}
