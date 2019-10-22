package cn.itsource.aigou.repository;

import cn.itsource.aigou.admin.ProductDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductDocRepository  extends ElasticsearchRepository<ProductDoc,Long>{
}
