package com.leyou.page.controller;

import com.leyou.page.service.GoodsHtmlService;
import com.leyou.page.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author: MoHan
 * @Description: MoHan
 * @Date: 2021/1/25 12:57
 */
@Controller
@RequestMapping("item")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsHtmlService goodsHtmlService;

    /**
     * 跳转到商品详情页
     * @param model
     * @param id
     * @return
     */
    @GetMapping("{id}.html")
    public String toItemPage(Model model, @PathVariable("id")Long id){
        // 加载所需的数据
        Map<String, Object> map = this.goodsService.loadModel(id);
        // 把数据放入数据模型
        model.addAllAttributes(map);

        // 页面静态化
        this.goodsHtmlService.asyncExcute(id);

        return "item";
    }

}
