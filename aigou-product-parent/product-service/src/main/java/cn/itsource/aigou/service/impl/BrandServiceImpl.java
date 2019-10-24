package cn.itsource.aigou.service.impl;


import cn.itsource.aigou.domain.Brand;
import cn.itsource.aigou.mapper.BrandMapper;
import cn.itsource.aigou.query.BrandQuery;
import cn.itsource.aigou.service.IBrandService;
import cn.itsource.aigou.vo.BrandVo;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>
 * 品牌信息 服务实现类
 * </p>
 *
 * @author rxl
 * @since 2019-10-12
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    @Override
    public PageList<Brand> queryPage(BrandQuery query) {
        IPage<Brand> brandIPage =baseMapper.queryPage(new Page(query.getPage(), query.getRows()), query);
        PageList<Brand> pageList = new PageList<>(brandIPage.getTotal(), brandIPage.getRecords());
        return pageList;
    }
    /**
     * 商城界面根据ProductId获取当前类全部商品和大写字母
     * @param productId
     * @return
     */
    @Override
    public BrandVo getByProductId(Long productId) {
        List<Brand> brands = baseMapper.selectList(new QueryWrapper<Brand>()
                .eq("product_type_id", productId));
        BrandVo brandVo = new BrandVo();
        brandVo.setBrands(brands);
        Set<String> letters = new TreeSet<>();
        for (Brand brand : brands) {
            letters.add(brand.getFirstLetter());
        }
        brandVo.setLetters(letters);
        return brandVo;
    }
}
