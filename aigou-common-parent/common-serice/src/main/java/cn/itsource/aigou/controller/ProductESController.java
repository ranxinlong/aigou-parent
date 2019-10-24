package cn.itsource.aigou.controller;


import cn.itsource.aigou.admin.ProductDoc;
import cn.itsource.aigou.domain.ProductParam;
import cn.itsource.aigou.repository.ProductDocRepository;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductESController {

    @Autowired
    private ProductDocRepository repository;

    @PostMapping("/es/search")
    public PageList<ProductDoc> search(@RequestBody ProductParam productParam){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //查询和过滤
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //关键字查询
        if(StringUtils.isNotEmpty(productParam.getKeyword())){
            boolQueryBuilder.must(new MatchQueryBuilder("all",productParam.getKeyword()));
        }
        //类型编号
        if (productParam.getProductTypeId() != null){
            boolQueryBuilder.must(new TermQueryBuilder("productTypeId",productParam.getProductTypeId()));
        }
        //品牌编号
        if (productParam.getBrandId() != null){
            boolQueryBuilder.must(new TermQueryBuilder("brandId",productParam.getBrandId()));
        }
        //最高价格和最低价格
        if(productParam.getMinPrice() != null){
            boolQueryBuilder.must(new RangeQueryBuilder("maxPrice").gte(productParam.getMinPrice()));
        }
        if(productParam.getMinPrice() != null){
            boolQueryBuilder.must(new RangeQueryBuilder("minPrice").lte(productParam.getMaxPrice()));
        }
        builder.withQuery(boolQueryBuilder);
        //排序 默认使用销售量排序
        String sortColumn = "saleCount";
        if(StringUtils.isNotEmpty(productParam.getSortFilId())){
            sortColumn = productParam.getSortFilId();
        }
        //排序方式
        SortOrder sortOrder = SortOrder.DESC;
        if ("asc".equals(productParam.getSortType())){
            sortOrder = SortOrder.ASC;
        }
        builder.withSort(new FieldSortBuilder(sortColumn).order(sortOrder));
        //分页
        builder.withPageable(PageRequest.of(productParam.getPage()-1, productParam.getRows()));
        Page<ProductDoc> productDocs = repository.search(builder.build());
        return new PageList<>(productDocs.getTotalElements(),productDocs.getContent());
    }
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
