package cn.itsource.aigou.service.impl;


import cn.itsource.aigou.domain.Brand;
import cn.itsource.aigou.mapper.BrandMapper;
import cn.itsource.aigou.query.BrandQuery;
import cn.itsource.aigou.service.IBrandService;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
        System.out.println("-------------------------");
        System.out.println(query.getPage()+":"+query.getRows());
        IPage<Brand> brandIPage =baseMapper.queryPage(new Page(query.getPage(), query.getRows()), query);
        PageList<Brand> pageList = new PageList<>(brandIPage.getTotal(), brandIPage.getRecords());
        return pageList;
    }
}
