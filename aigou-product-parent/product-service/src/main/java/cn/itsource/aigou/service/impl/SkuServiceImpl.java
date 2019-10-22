package cn.itsource.aigou.service.impl;

import cn.itsource.aigou.domain.Sku;
import cn.itsource.aigou.mapper.SkuMapper;
import cn.itsource.aigou.service.ISkuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * SKU 服务实现类
 * </p>
 *
 * @author rxl
 * @since 2019-10-17
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements ISkuService {
    /**
     * 根据productId的数据局获取t_sku表里面的数据
     * @param productId
     * @return
     */
    @Override
    public List<Sku> getSku(Long productId) {
        return baseMapper.getSku(productId);
    }
}
