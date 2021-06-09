package com.example.http.forrest;

import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Request;

import java.util.Map;
import java.util.concurrent.Future;

public interface AmapForestClient {
    /**
     * @Get注解代表该方法专做GET请求
     * 在url中的${0}代表引用第一个参数，${1}引用第二个参数
     */
    @Get("http://ditu.amap.com/service/regeo?longitude=${0}&latitude=${1}")
    Map getLocation(String longitude, String latitude);

    @Request("http://ditu.amap.com/service/regeo?longitude=${0}&latitude=${1}")
    Map getLocation2(String longitude, String latitude);

    @Request(
            url = "http://ditu.amap.com/service/regeo?longitude=${0}&latitude=${1}",
            headers = "Accept: text/plain"
    )
    Map getLocation3(String longitude, String latitude);

    @Request(
            url = "http://ditu.amap.com/service/regeo?longitude=${0}&latitude=${1}",
            async = true,
            headers = "Accept: text/plain"
    )
    void getasync(String longitude, String latitude);

    @Request(
            url = "http://ditu.amap.com/service/regeo?longitude=${0}&latitude=${1}",
            async = true,
            headers = "Accept: text/plain"
    )
    Future<Map> asyncFuture();
}
