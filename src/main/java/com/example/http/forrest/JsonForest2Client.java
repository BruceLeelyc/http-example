package com.example.http.forrest;

import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.example.http.pojo.MyUserDto;

import java.util.Map;

/**
 * @BaseRequest 为配置接口层级请求信息的注解，
 * 其属性会成为该接口下所有请求的默认属性，
 * 但可以被方法上定义的属性所覆盖
 * @BaseRequest注解中的所有字符串属性都可以通过模板表达式引用全局变量或方法中的参数。
 */
@BaseRequest(
        // 默认域名
        baseURL = "${baseUrl}",
        headers = {
            // 默认请求头
            "Accept:${accept}"
        }
)
public interface JsonForest2Client {
    /**
     * 将对象参数解析为JSON字符串，并放在请求的Body进行传输
     */
    @Post(
        url = "/register",
        headers = {
            // 覆盖接口层级配置的请求头信息
            "Accept:application/json"
        }
    )
    String registerUser(@JSONBody MyUserDto user);

    /**
     * 将Map类型参数解析为JSON字符串，并放在请求的Body进行传输
     */
    @Get(
        url = "/getJson",
        headers = {
            // 覆盖接口层级配置的请求头信息
            "Accept:application/json"
        }
    )
    String postJsonMap(@JSONBody Map mapObj);

    /**
     * 直接传入一个JSON字符串，并放在请求的Body进行传输
     */
    @Post("/getStr")
    String postJsonText(@JSONBody String jsonText);
}
