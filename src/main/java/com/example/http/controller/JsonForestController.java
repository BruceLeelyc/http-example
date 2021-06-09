package com.example.http.controller;

import com.dtflys.forest.annotation.JSONBody;
import com.example.http.forrest.JsonForest2Client;
import com.example.http.forrest.JsonForestClient;
import com.example.http.forrest.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.StringJoiner;

/**
 * @ClassName: JsonForestController
 * @Description:
 * @Author: lixl
 * @Date: 2021/6/8 16:03
 */
@RestController
@RequestMapping("json")
public class JsonForestController {

    @Autowired
    private JsonForestClient jsonForestClient;

    @Autowired
    private JsonForest2Client jsonForest2Client;

    @RequestMapping("/testRegister")
    public Object testRegister(@RequestBody MyUser user) {
        return jsonForestClient.registerUser(user);
    }
    @RequestMapping("/register")
    public Object register(@RequestBody MyUser user) {
        user.setName(new StringJoiner("-",user.getName(),"test").toString());
        return user;
    }

    @RequestMapping("/test")
    public Object testJson(@RequestBody Map mapObj) {
        return jsonForestClient.postJsonMap(mapObj);
    }

    @RequestMapping("/getJson")
    public Object getJson(@RequestBody Map mapObj) {
        mapObj.put("flag", "这是测试数据");
        return mapObj;
    }

    @RequestMapping("/testStr")
    public Object testStr(String jsonStr) {
        return jsonForestClient.postJsonText(jsonStr);
    }

    @RequestMapping("/getStr")
    public Object getStr(@RequestBody String jsonStr) {
        return "hello:"+jsonStr;
    }

    @RequestMapping("/testStr2")
    public Object testStr2(String jsonStr) {
        return jsonForest2Client.postJsonText(jsonStr);
    }
}
