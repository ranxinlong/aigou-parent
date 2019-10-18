package cn.itsource.aigou.service.impl;

import cn.itsource.aigou.domain.ProductComment;
import cn.itsource.aigou.mapper.ProductCommentMapper;
import cn.itsource.aigou.service.IProductCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品评价 服务实现类
 * </p>
 *
 * @author rxl
 * @since 2019-10-17
 */
@Service
public class ProductCommentServiceImpl extends ServiceImpl<ProductCommentMapper, ProductComment> implements IProductCommentService {

}
