package cn.itsource.aigou.mapper;

import cn.itsource.aigou.domain.Product;
import cn.itsource.aigou.query.ProductQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

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
}
