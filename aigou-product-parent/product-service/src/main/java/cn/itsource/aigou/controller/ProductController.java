package cn.itsource.aigou.controller;

import cn.itsource.aigou.domain.Product;
import cn.itsource.aigou.domain.Specification;
import cn.itsource.aigou.service.IProductService;
import cn.itsource.aigou.query.ProductQuery;
import cn.itsource.aigou.vo.SkuVo;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import cn.itsource.basic.util.StrUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public IProductService productService;


    /**
     * 批量上架
     * @param ids
     * @return
     */
    @GetMapping("/onSale")
    public AjaxResult onSale (@RequestParam("ids") String ids){
        try {
            List<Long> idList = StrUtils.splitStr2LongArr(ids);
            productService.onSale(idList);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("上架失败");
        }
    }

    /**
     * 批量下架
     * @param ids
     * @return
     */
    @GetMapping("/offSale")
    public AjaxResult offSale(@RequestParam("ids") String ids){
        try {
            List<Long> idlist = StrUtils.splitStr2LongArr(ids);
            productService.offSale(idlist);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("下架失败");
        }
    }



    /**
    * 保存和修改公用的
    * @param product  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Product product){
        try {
            if(product.getId()!=null){
                productService.updateById(product);
            }else{
                productService.save(product);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Integer id){
        try {
            productService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    /**
     * 根据productId获取商品的显性数据
     * @param productId
     * @return
     */
    @GetMapping("/getviewProperties/{productId}")
    public List<Specification> getviewProperties(@PathVariable("productId") Long productId){
        return productService.getviewProperTies(productId);
    }
    /**
     * 根据productId获取商品的SKU数据
     * @param productId
     * @return
     */
    @GetMapping("/getskuProperties/{productId}")
    public List<Specification> getSkuProperties(@PathVariable("productId") Long productId){
        return productService.getSkuProperties(productId);
    }


    /**
     * 根据productId在product表里面保存商品的显示属性（viewProperty）
     * @param productId
     * @param viewProperty
     * @return
     */
    @PostMapping("/saveViewProperties")
    public AjaxResult saveViewProperties(@RequestParam("productId") Long productId,@RequestBody List<Specification> viewProperty){
        try {
            productService.saveViewProperties(productId,viewProperty);
            return AjaxResult.me().setSuccess(true).setMessage("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("保存失败");
        }

    }

    @PostMapping("/saveSkuProperties")
    public AjaxResult saveSkuProperties(@RequestParam("productId") Long productId, @RequestBody SkuVo skuVo){
        productService.saveSkuProperties(productId,skuVo);
        return AjaxResult.me();
    }



    @RequestMapping(value="/deleteBatch",method=RequestMethod.DELETE)
    public AjaxResult delete(@RequestParam("ids") String ids){
        try {
            List<Long> longs = StrUtils.splitStr2LongArr(ids);
            productService.removeByIds(longs);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Product get(@PathVariable("id") Long id)
    {
        return productService.getById(id);
    }


    /**
    * 查看所有
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Product> list(){

        return productService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Product> json(@RequestBody ProductQuery query)
    {
        return productService.queryPage(query);
    }
}
