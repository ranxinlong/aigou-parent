package cn.itsource.aigou.mapper;

import cn.itsource.aigou.domain.Sku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * SKU Mapper 接口
 * </p>
 *
 * @author rxl
 * @since 2019-10-17
 */
@Component
public interface SkuMapper extends BaseMapper<Sku> {

    List<Sku> getSku(Long productId);
}
