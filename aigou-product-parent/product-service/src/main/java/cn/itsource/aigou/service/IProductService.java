package cn.itsource.aigou.service;

import cn.itsource.aigou.domain.Product;
import cn.itsource.aigou.domain.Specification;
import cn.itsource.aigou.query.ProductQuery;
import cn.itsource.aigou.vo.SkuVo;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author rxl
 * @since 2019-10-12
 */
public interface IProductService extends IService<Product> {
    PageList<Product> queryPage(ProductQuery query);

    /**
     * 根据productId获取商品的显示属性
     * @param productId
     * @return
     */
    List<Specification> getviewProperTies(Long productId);
    /**
     * 根据productId在product表里面保存商品的显示属性（viewProperty）
     * @param productId
     * @param viewProperty
     * @return
     */
    void saveViewProperties(Long productId, List<Specification> viewProperty);
    /**
     * 根据productId在product表里面保存商品的SKU属性
     * @param productId
     * @return
     */
    List<Specification> getSkuProperties(Long productId);

    void saveSkuProperties(Long productId, SkuVo skuVo);
    /**
     * 批量上架
     * @param idList
     * @return
     */
    void onSale(List<Long> idList);
    /**
     * 批量下架
     * @param idlist
     * @return
     */
    void offSale(List<Long> idlist);
}
