package cn.itsource.aigou.controller;


import cn.itsource.aigou.admin.ProductDoc;
import cn.itsource.aigou.repository.ProductDocRepository;
import cn.itsource.basic.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductESController {

    @Autowired
    private ProductDocRepository repository;

    /**
     * 批量保存
     * @param productDocList
     * @return
     */
    @PostMapping("/es/saveBatch")
    public void saveBatch(@RequestBody List<ProductDoc> productDocList){
            repository.saveAll(productDocList);
    }

    /**
     * 批量删除
     * @param ids
     */
    @PostMapping("/es/deleteBatch")
    public void deleteBatch (@RequestBody List<Long> ids){
        for (Long id : ids) {
            repository.deleteById(id);
        }
    }

}
