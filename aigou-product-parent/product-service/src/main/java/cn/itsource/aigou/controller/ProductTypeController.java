package cn.itsource.aigou.controller;

import cn.itsource.aigou.domain.ProductType;
import cn.itsource.aigou.service.IProductTypeService;
import cn.itsource.aigou.query.ProductTypeQuery;
import cn.itsource.aigou.vo.ProductTypeCrumbVo;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/productType")
public class ProductTypeController {
    @Autowired
    public IProductTypeService productTypeService;

    /**
     * 根据前端home页传递producttypeID值查询type表里面当前商品数据和同级商品数据
     * @param productTypeId
     * @return
     */
    @GetMapping("/getCrumb")
    public List<ProductTypeCrumbVo> loadTyeCrumb(@RequestParam("ProductTypeId") Long productTypeId){
        return  productTypeService.loadTypeCrumb(productTypeId);
    }

    /**
    * 保存和修改公用的
    * @param productType  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody ProductType productType){
        try {
            if(productType.getId()!=null){
                productTypeService.updateById(productType);
            }else{
                productTypeService.save(productType);
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
            productTypeService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    /**
     * 生成home页
     * @return
     */
    @GetMapping("/genHomePage")
    public AjaxResult genHomePage(){
        try {
            productTypeService.genHomePage();
            return AjaxResult.me().setSuccess(true).setMessage("成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(true).setMessage("失败失败！"+e.getMessage());
        }
    }

    //获取
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ProductType get(@PathVariable("id") Long id)
    {
        return productTypeService.getById(id);
    }


    /**
    * 查看所有
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<ProductType> list(){

        return productTypeService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<ProductType> json(@RequestBody ProductTypeQuery query)
    {
        Page<ProductType> page = new Page<ProductType>(query.getPage(),query.getRows());
        IPage<ProductType> ipage = productTypeService.page(page);
        return new PageList<ProductType>(ipage.getTotal(),ipage.getRecords());
    }

    /**
     * 加载类型数
     * @return
     */
    @RequestMapping(value = "/loadTypeTree",method = RequestMethod.GET)
    public List<ProductType> loadTypeTree(){
        return productTypeService.loadTypeTree();
    }
}
