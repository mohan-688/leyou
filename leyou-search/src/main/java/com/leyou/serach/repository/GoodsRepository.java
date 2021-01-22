package com.leyou.serach.repository;

import com.leyou.serach.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/22 16:32
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
