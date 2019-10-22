package cn.itsource.aigou.mapper;

import cn.itsource.aigou.domain.Product;
import cn.itsource.aigou.query.ProductQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author rxl
 * @since 2019-10-12
 */
public interface ProductMapper extends BaseMapper<Product> {

    IPage<Product> queryPage(Page page, @Param("query")ProductQuery query);

    /**
     * 根据productId在product表里面保存商品的显示属性（specification）
     * @param productId
     * @param specification
     * @return
     */
    void saveViewProperties(@Param("productId") Long productId,@Param("specification") String specification);
        /**
        * 根据productId修改商品的sku属性，并在t_sku表里面添加数据进入
     * @param productId
         * @param skupropertions  数据
     */
    void saveSkuProperties(@Param("productId") Long productId,@Param("skupropertions") String skupropertions);
    /**
     * 批量上架
     * @param idList
     * @return
     */
    void onSale(@Param("idList") List<Long> idList,@Param("onSaleTime") Long onSaleTime);
    /**
     * 批量下架
     * @param idlist
     * @return
     */
    void offSale(@Param("idlist") List<Long> idlist,@Param("offSaleTime") Long offSaleTime);
}
