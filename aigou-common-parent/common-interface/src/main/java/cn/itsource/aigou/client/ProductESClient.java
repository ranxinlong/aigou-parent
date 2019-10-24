package cn.itsource.aigou.client;

import cn.itsource.aigou.admin.ProductDoc;
import cn.itsource.aigou.domain.ProductParam;
import cn.itsource.basic.util.PageList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("AIGOU-COMMON")
@Component
public interface ProductESClient {
    /**
     * 批量保存
     * @param productDocList
     * @return
     */
    @PostMapping("/es/saveBatch")
    public void saveBatch(@RequestBody List<ProductDoc> productDocList);

    /**
     * 批量删除
     * @param ids
     */
    @PostMapping("/es/deleteBatch")
    public void deleteBatch (@RequestBody List<Long> ids);

    @PostMapping("/es/search")
    public PageList<ProductDoc> search(@RequestBody ProductParam productParam);

}
