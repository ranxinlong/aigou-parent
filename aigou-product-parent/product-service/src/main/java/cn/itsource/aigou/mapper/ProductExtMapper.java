package cn.itsource.aigou.mapper;

import cn.itsource.aigou.domain.ProductExt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;

/**
 * <p>
 * 商品扩展 Mapper 接口
 * </p>
 *
 * @author rxl
 * @since 2019-10-17
 */
public interface ProductExtMapper extends BaseMapper<ProductExt> {
    ProductExt findOne(Long productId);

    void deleteByProId(Serializable id);

}
