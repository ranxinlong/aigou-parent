package cn.itsource.aigou.mapper;


import cn.itsource.aigou.domain.Brand;
import cn.itsource.aigou.query.BrandQuery;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 品牌信息 Mapper 接口
 * </p>
 *
 * @author rxl
 * @since 2019-10-12
 */
public interface BrandMapper extends BaseMapper<Brand> {

   /* PageList<Brand> queryPage(BrandQuery query);*/

     IPage<Brand> queryPage(Page page, @Param("query") BrandQuery query);


}
