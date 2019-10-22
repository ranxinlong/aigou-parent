package cn.itsource.aigou.service;

import cn.itsource.aigou.domain.Sku;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * SKU 服务类
 * </p>
 *
 * @author rxl
 * @since 2019-10-17
 */
public interface ISkuService extends IService<Sku> {

    /**
     * 根据productId的数据局获取t_sku表里面的数据
     * @param productId
     * @return
     */
    List<Sku> getSku(Long productId);
}
