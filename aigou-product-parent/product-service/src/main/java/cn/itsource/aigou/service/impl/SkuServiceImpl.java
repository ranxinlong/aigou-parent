package cn.itsource.aigou.service.impl;

import cn.itsource.aigou.domain.Sku;
import cn.itsource.aigou.mapper.SkuMapper;
import cn.itsource.aigou.service.ISkuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Object> skuChang(Long productId, String indexs) {
        Sku sku = baseMapper.selectOne(new QueryWrapper<Sku>()
                .eq("product_id", productId).eq("indexs", indexs));
        Map<String,Object> skuMap = new HashMap<>();
        skuMap.put("price", sku.getPrice());
        skuMap.put("store", sku.getAvailableStock());
        return skuMap;
    }
}
