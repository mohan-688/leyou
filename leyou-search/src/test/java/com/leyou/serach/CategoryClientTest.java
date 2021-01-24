package com.leyou.serach;

import com.leyou.LeyouSearchServiceApplication;
import com.leyou.serach.client.BrandClient;
import com.leyou.serach.client.CategoryClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/22 16:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeyouSearchServiceApplication.class)
public class CategoryClientTest {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Test
    public void testQueryCategories() {
        List<String> names = this.categoryClient.queryNamesByIds(Arrays.asList(1L, 2L, 3L));
        names.forEach(System.out::println);
    }

    @Test
    public void testBrandId(){
        System.out.println(this.brandClient.queryBrandIdByName("华为（HUAWEI）"));
    }
}
