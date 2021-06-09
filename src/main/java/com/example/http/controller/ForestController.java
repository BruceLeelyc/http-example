package com.example.http.controller;

import com.example.http.forrest.AmapForestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName: ForestController
 * @Description:
 * @Author: lixl
 * @Date: 2021/6/8 14:57
 */
@RestController
@RequestMapping("forest")
public class ForestController {

    @Autowired
    private AmapForestClient amapForestClient;

    @RequestMapping("/amap")
    public Object forestRequest() {
        // 调用高德地图API为栗子
        Map result = amapForestClient.getLocation("121.475078", "31.223577");
        System.out.println(result);
        return result;
    }
}

