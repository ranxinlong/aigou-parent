package cn.itsource.aigou.service.impl;


import cn.itsource.aigou.admin.ProductDoc;
import cn.itsource.aigou.client.ProductESClient;
import cn.itsource.aigou.client.StaticPageClient;
import cn.itsource.aigou.domain.*;
import cn.itsource.aigou.mapper.*;
import cn.itsource.aigou.query.ProductQuery;
import cn.itsource.aigou.service.IProductService;
import cn.itsource.aigou.vo.ProductTypeCrumbVo;
import cn.itsource.aigou.vo.SkuVo;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author rxl
 * @since 2019-10-12
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductExtMapper productExtMapper;

    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private ProductTypeMapper typeMapper;
    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private ProductESClient client;

    @Autowired
    private StaticPageClient pageClient;

    @Autowired
    private ProductTypeServiceImpl typeService;



    @Override
    @Transactional
    public boolean save(Product product) {
        product.setCreateTime(System.currentTimeMillis());
        baseMapper.insert(product);

        ProductExt ext = product.getExt();
        //mybatis自动返回新增数据的主键值
        ext.setProductId(product.getId());
        productExtMapper.insert(ext);
        return true;
    }

    @Override
    @Transactional
    public boolean updateById(Product product) {
        baseMapper.updateById(product);

        //修改ext表里面的数据
        productExtMapper.updateById(product.getExt());
        return true;
    }

    @Override
    @Transactional
    public boolean removeById(Serializable id) {
        //删除商品表的数据
        baseMapper.deleteById(id);

        //同时删除ext表里面对应的数据
        productExtMapper.deleteByProId(id);
        return true;
    }

    @Override
    public PageList<Product> queryPage(ProductQuery query) {
        IPage<Product> queryPage = baseMapper.queryPage(new Page(query.getPage(), query.getRows()), query);
        PageList<Product> pageList = new PageList<>(queryPage.getTotal(), queryPage.getRecords());
        return pageList;
    }

    /**
     * 根据productId获取商品的显示属性
     * @param productId
     * @return
     */
    @Override
    public List<Specification> getviewProperTies(Long productId) {
        List<Specification> specifications = null;
        Product product = baseMapper.selectById(productId);
        String viewProperties = product.getViewProperties();
        //StringUtils.isEmpty(viewProperties)表示商品表里面没有显示属性，需要去viewProperties表里面获取数据
        if (StringUtils.isEmpty(viewProperties)){
            Long productTypeId = product.getProductTypeId();
            //isSku=0表示是显示属性，1表示是sku属性
            specifications = specificationMapper.selectList(
                    new QueryWrapper<Specification>().eq("product_type_id", productTypeId).eq("isSku", 0));
        }else {
            specifications = JSONArray.parseArray(viewProperties, Specification.class);
        }
        return specifications;
    }
    /**
     * 根据productId在product表里面保存商品的显示属性（viewProperty）
     * @param productId
     * @param viewProperty
     * @return
     */
    @Override
    public void saveViewProperties(Long productId, List<Specification> viewProperty) {
        String specification = JSON.toJSONString(viewProperty);
        baseMapper.saveViewProperties(productId,specification);
    }
    /**
     * 根据productId获取商品的sku属性
     * @param productId
     * @return
     */
    @Override
    public List<Specification> getSkuProperties(Long productId) {
        Product product = baseMapper.selectById(productId);
        String skuProperties = product.getSkuProperties();
        List<Specification> specifications = null;
        // StringUtils.isEmpty(skuProperties表示product里面没有改商品sku属性，需要去属性表里面查询
        if (StringUtils.isEmpty(skuProperties)){
            Long productTypeId = product.getProductTypeId();
           specifications = specificationMapper.selectList(new QueryWrapper<Specification>()
                    .eq("isSku", 1).eq("product_type_id", productTypeId));
        }else {
            //原本数据里面已经有SKU属性了，但是是string格式的，需要更改一下格式变为json更是
            specifications = JSONArray.parseArray(skuProperties, Specification.class);
        }
        return specifications;
    }

    /**
     * 根据productId修改商品的sku属性，并在t_sku表里面添加数据进入
     * @param productId
     * @param skuVo
     */
    @Override
    @Transactional
    public void saveSkuProperties(Long productId, SkuVo skuVo) {
        //1.修改t_product里面的skuProperties字段的值
        String skupropertions = JSON.toJSONString(skuVo.getSkuproperties());
       baseMapper.saveSkuProperties(productId,skupropertions);

        //2.把skus里面的数据保存到t_sku表里面，
        //2.1 保存数据之前。把原有数据先删除
        skuMapper.delete(new QueryWrapper<Sku>().eq("product_id", productId));
        //2.2 在把现有的数据保存到t_sku表里面
        List<Map<String, String>> skus = skuVo.getSkus();
        Sku sku = null;
        for (Map<String, String> skumap : skus) {
            sku = new Sku();
            sku.setCreateTime(System.currentTimeMillis());
            sku.setProductId(productId);
            //拼接skuName的值
            StringBuilder skuname = new StringBuilder();
            //遍历map集合拼接skuname字段 128GB + 钛金
            for (Map.Entry<String, String> entry : skumap.entrySet()) {
                if(!"price".equals(entry.getValue()) && !"store".equals(entry.getValue())){
                    skuname.append(entry.getValue());
                }
            }
            sku.setSkuName(skuname.toString());
            //单价
            sku.setPrice((Integer.parseInt( skumap.get("price"))));
            //库存
            sku.setAvailableStock(Integer.parseInt(skumap.get("store")));
            //获取indexs
            sku.setIndexs(skumap.get("indexs"));
            skuMapper.insert(sku);
        }
    }
    /**
     * 批量下架
     * @param idlist
     * @return
     */
    @Override
    @Transactional
    public void offSale(List<Long> idlist) {
        //修改商品在数据库里面的状态和下架时间
        baseMapper.offSale(idlist,System.currentTimeMillis());
        //商品下架的同时，单出es的数据
        client.deleteBatch(idlist);

    }

    /**
     * 在商城里面搜索ES里面的商品
     * @param productParam
     * @return
     */
    @Override
    public PageList<Product> queryOnSale(ProductParam productParam) {
        //查询再es的数据
        PageList<ProductDoc> search = client.search(productParam);
        //封装成pagelist
        List<Product> list = new ArrayList<>();
        Product product = null;
        List<ProductDoc> rows = search.getRows();
        for (ProductDoc row : rows) {
            product = new Product();
            product.setId(row.getId());
            product.setMedias(row.getMedias());
            product.setName(row.getName());
            product.setSubName(row.getSubName());
            product.setSaleCount(row.getSaleCount());
            product.setMaxPrice(row.getMaxPrice());
            product.setMinPrice(row.getMinPrice());
            list.add(product);
        }
        return new PageList<>(search.getTotal(),list);
    }

    /**
     * 批量上架
     * @param idList
     * @return
     */
    @Override
    @Transactional
    public void onSale(List<Long> idList) {
        //1.先修改数据库里面这些数据的状态和上架时间
        baseMapper.onSale(idList,System.currentTimeMillis());
        //2.查询这些id在数据库里面的数据
        List<Product> products = baseMapper.selectBatchIds(idList);
        //3.再把查询出来的记过保存到ES里面
        List<ProductDoc> productDocList = products2Doc(products);
        //上架的同时生成该商品的静态页面
        staticDetailPage(products);
        client.saveBatch(productDocList);
    }

    /**
     * //上架的同时生成该商品的静态页面
     * @param products
     */
    private void staticDetailPage(List<Product> products) {
        for (Product product : products) {
            //模板路径
            String templatePath = "D:\\software\\JetBrains\\aigou-parent\\aigou-product-parent\\product-service\\src\\main\\resources\\template\\product.detail.vm";
            String targetPath = "D:\\software\\JetBrains\\aigou-web-parent\\ecommerce\\detail\\"+product.getId()+".html";
            Map<String,Object> model = new HashMap<>();
            //获取面包屑的数据
            List<ProductTypeCrumbVo> crumbVos = typeService.loadTypeCrumb(product.getProductTypeId());
            model.put("product", product);
            model.put("crumbs", crumbVos);
            //SKU属性
            String skuProperties = product.getSkuProperties();
            List<Specification> skus = JSONArray.parseArray(skuProperties, Specification.class);
            model.put("skus", skus);
            //viewProperties 显示属性
            String viewProperties = product.getViewProperties();
            List<Specification> vies = JSONArray.parseArray(viewProperties, Specification.class);
            model.put("vies", viewProperties);
            //商品详情
            ProductExt ext = productExtMapper.selectOne(new QueryWrapper<ProductExt>().eq("product_id", product.getId()));
            String richContent = ext.getRichContent();
            model.put("richContent", richContent);
            //商品的媒体属性需要的一份图三分数据[[aaaa,aaa,aaa],[bbbb,bbbbb,bbbb],[ccccccc,cccccc,ccccc]],[aaaa,aaa,aaa]代表同一张图
            String medias = product.getMedias();//aaa,bb,cc
            String[] mediaarr = medias.split(",");//[aaa,bb,cc]

            List<List<String>> medis = new ArrayList<>();
            for (String mediass : mediaarr) {
                List<String> oneMedis = new ArrayList<>();
                oneMedis.add("http://172.16.4.128"+mediass);
                oneMedis.add("http://172.16.4.128"+mediass);
                oneMedis.add("http://172.16.4.128"+mediass);
                medis.add(oneMedis);
            }
            String imags = JSON.toJSONString(medis);
            model.put("imags", imags);
            //skujsonstr
            model.put("skuJSON", product.getSkuProperties());

            pageClient.generateStaticPage(templatePath, targetPath, model);
        }
    }

    /**
     * 集合的转换，把product类型转换鞥装到productDoc里面
     * @param products
     * @return
     */
    private List<ProductDoc> products2Doc(List<Product> products) {
        ArrayList<ProductDoc> parductlist = new ArrayList<>();
        ProductDoc doc = null;
        for (Product product : products) {
          doc =product2Doc(product);
          parductlist.add(doc);
        }
        return parductlist;

    }

    /**
     * 类型的转化，主要是封装doc里面的参数,
     * @param product
     * @return
     */
    private ProductDoc product2Doc(Product product) {
        System.out.println(product);
        ProductDoc productDoc = new ProductDoc();
        Brand brand = brandMapper.selectById(product.getBrandId());
        ProductType productType = typeMapper.selectById(product.getProductTypeId());
        productDoc.setId(product.getId());
        StringBuilder builder = new StringBuilder();
        builder.append(product.getName()).append(" ")
                .append(product.getSubName()).append(" ")
                .append(productType.getName()).append(" ")
                .append(brand.getName());
        productDoc.setAll(builder.toString());

        productDoc.setProductTypeId(product.getProductTypeId());

        productDoc.setBrandId(product.getBrandId());
        //最高最低价格
        List<Sku> skus = skuMapper.selectList(new QueryWrapper<Sku>().
                eq("product_id", product.getId()));
        Integer MaxPrice = 0;
        Integer MinPrice = 0;
        //默认第一个的价格就是最低价
        if (skus !=null && skus.size() >0){
            MinPrice = skus.get(0).getPrice();
        }
        for (Sku sku : skus) {
            if (sku.getPrice() >MaxPrice){
                MaxPrice = sku.getPrice();
            }
            if (sku.getPrice() < MinPrice){
                MinPrice = sku.getPrice();
            }
        }
        productDoc.setMaxPrice(MaxPrice);
        productDoc.setMinPrice(MinPrice);

        productDoc.setSaleCount(product.getSaleCount());
        productDoc.setOnSaleTime(product.getOnSaleTime());
        productDoc.setCommentCount(product.getCommentCount());
        productDoc.setViewCount(product.getViewCount());
        productDoc.setName(product.getName());
        productDoc.setSubName(product.getSubName());
        productDoc.setMedias(product.getMedias());
        productDoc.setViewProperties(product.getViewProperties());
        productDoc.setSkuProperties(product.getSkuProperties());
        return productDoc;
    }


}
