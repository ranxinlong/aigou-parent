package cn.itsource.aigou.service;


import cn.itsource.aigou.domain.ProductType;
import cn.itsource.aigou.vo.ProductTypeCrumbVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品目录 服务类
 * </p>
 *
 * @author rxl
 * @since 2019-10-12
 */
public interface IProductTypeService extends IService<ProductType> {

    //加载类型树
    List<ProductType> loadTypeTree();

    /**
     * 初始化商城首页的方法
     */
    void genHomePage();
    /**
     * 根据前端home页传递producttypeID值查询type表里面当前商品数据和同级商品数据
     * @param productTypeId
     * @return
     */
    List<ProductTypeCrumbVo> loadTypeCrumb(Long productTypeId);
}
