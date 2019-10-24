package cn.itsource.aigou.service;

import cn.itsource.aigou.domain.Brand;
import cn.itsource.aigou.query.BrandQuery;
import cn.itsource.aigou.vo.BrandVo;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 品牌信息 服务类
 * </p>
 *
 * @author rxl
 * @since 2019-10-12
 */
public interface IBrandService extends IService<Brand> {

    PageList<Brand> queryPage(BrandQuery query);
    /**
     * 商城界面根据ProductId获取当前类全部商品和大写字母
     * @param productId
     * @return
     */
    BrandVo getByProductId(Long productId);
}
