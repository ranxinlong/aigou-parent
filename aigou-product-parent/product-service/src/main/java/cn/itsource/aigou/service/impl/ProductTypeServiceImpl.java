package cn.itsource.aigou.service.impl;


import cn.itsource.aigou.client.RedisClient;
import cn.itsource.aigou.client.StaticPageClient;
import cn.itsource.aigou.domain.ProductType;
import cn.itsource.aigou.mapper.ProductTypeMapper;
import cn.itsource.aigou.service.IProductTypeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.xdevapi.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author rxl
 * @since 2019-10-12
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {


    @Autowired
    private RedisClient redisClient;

    @Autowired
    private StaticPageClient staticPageClient;

    /*初始化商城首页的方法*/
    @Override
    public void genHomePage() {
        //先生成product.type.vm.html
        //模板路径
        String templatePath = "D:\\software\\JetBrains\\aigou-parent\\aigou-product-parent\\product-service\\src\\main\\resources\\template\\product.type.vm";
        //页面生成保存路径
        String targetPath = "D:\\software\\JetBrains\\aigou-parent\\aigou-product-parent\\product-service\\src\\main\\resources\\template\\product.type.vm.html";
        //数据
        List<ProductType> productTypes = loadTypeTree();
        //生成模板
        staticPageClient.generateStaticPage(templatePath, targetPath, productTypes);

        //在上传home。htm
        templatePath = "D:\\software\\JetBrains\\aigou-parent\\aigou-product-parent\\product-service\\src\\main\\resources\\template\\home.vm";
        targetPath = "D:\\software\\JetBrains\\aigou-web-parent\\ecommerce\\home.html";
        Map<String,Object> model = new HashMap<>();
        model.put("staticRoot", "D:\\software\\JetBrains\\aigou-parent\\aigou-product-parent\\product-service\\src\\main\\resources\\");
        staticPageClient.generateStaticPage(templatePath, targetPath, model);
    }
    @Override
    public List<ProductType> loadTypeTree() {

        //获取tree数据之前先查询redis里面是不是已经存在数据
        String productTypesStr = redisClient.get("productTypes");
        System.out.println("查询redis");
        if (productTypesStr != null){
            //redis里面有数据，直接使用返回给前端  JSONArray.parseArray把字符串转换为java的对象，list集合的
            List<ProductType> productTypes = JSONArray.parseArray(productTypesStr,ProductType.class);
            return productTypes;
        }else {
            //redis里面没有数据的话，数据库查询并把结果保存到redis里面:JSON.toJSONString 转换为json字符串
            List<ProductType> productTypes = loadTypeTreeLoop();
            System.out.println("查询数据库");
            productTypesStr = JSON.toJSONString(productTypes);
            redisClient.set("productTypes", productTypesStr);
            return productTypes;
        }
    }

    public List<ProductType> loadTypeTreeLoop(){
        //查询所有的数据
        List<ProductType> productTypes = baseMapper.selectList(null);

        //组装数据需要
        List<ProductType> firsLeve1Type = new ArrayList<>();
        Map<Long,ProductType> productTypeMap = new HashMap<>();

        //先遍历全部数据一次，把数据都装到map集合里面去
        for (ProductType productType : productTypes) {
            productTypeMap.put(productType.getId(), productType);
        }
        //再次循环，判断是父菜单还是菜单，如果是子菜单，就找到父菜单并添加子菜单
        for (ProductType productType : productTypes) {
            if(productType.getPid() == 0){//==0表示就是父级菜单，直接保存到list集合里面
                firsLeve1Type.add(productType);
            }else {//如果是子级菜单
                //1.先找到他的父级菜单
                ProductType parent = productTypeMap.get(productType.getPid());
                //parent就是当前菜单的父级菜单
                //2.像实体类里面的children添加当前的菜单
                parent.getChildren().add(productType);
            }
        }
        return firsLeve1Type;
    }

    /**
     * redis的数据和数据库的数据同步的方法
     */
    public void synchronizedOption(){
        List<ProductType> productTypes = baseMapper.selectList(null);
        String productTypesStr = JSON.toJSONString(productTypes);
        redisClient.set("productTypes", productTypesStr);
        /*当我们的菜单有增删改以后，同步我们生成静态页面*/
        genHomePage();
    }

    /**
     * 重写我们的增、删、改的方法，在数据改变的时候和redis的数据同步
     */
    @Override
    public boolean save(ProductType entity) {
        boolean result = super.save(entity);
        synchronizedOption();
        return result;
    }

    @Override
    public boolean removeById(Serializable id) {
        boolean result = super.removeById(id);
        synchronizedOption();
        return result;
    }

    @Override
    public boolean updateById(ProductType entity) {
        boolean result =  super.updateById(entity);
        synchronizedOption();
        return result;
    }
}
