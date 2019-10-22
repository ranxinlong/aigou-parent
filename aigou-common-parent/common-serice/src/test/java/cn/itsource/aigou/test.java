package cn.itsource.aigou;


import cn.itsource.aigou.admin.ProductDoc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonApplication.class)
public class test {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 基本测试类
     * @throws Exception
     */
    @Test
    public void test01() throws Exception{
        elasticsearchTemplate.deleteIndex(ProductDoc.class);
        elasticsearchTemplate.createIndex(ProductDoc.class);
        elasticsearchTemplate.putMapping(ProductDoc.class);
    }
}
