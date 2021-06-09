package com.example.http;

import com.example.http.utils.OkHttpUtil;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: TestMain
 * @Description:
 * @Author: lixl
 * @Date: 2021/6/1 11:06
 */
public class TestMain {

    private static Logger logger = LoggerFactory.getLogger(TestMain.class);


    public static void main(String[] args) {
        // 异步
        //asynGet();
        // 同步
        //synGet();
        //
        //postStr();

        httpUtil();
    }

    private static void httpUtil() {
        String response = OkHttpUtil.getIntance().get("http://baidu.com");
        System.out.println(response);
        Map<String, String> pamas = new HashMap<>();
        pamas.put("post", "post");
        String post = OkHttpUtil.getIntance().post("http://baidu.com", pamas);
        System.out.println(post);
    }

    private static void postStr() {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestBody = "I am Jdqm.";
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType, requestBody))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                logger.info("onFailure: {}", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                logger.info(response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    logger.info(headers.name(i) + ":" + headers.value(i));
                }
                logger.info("onResponse:  {}", response.body().string());
            }
        });
    }

    /**
     * get同步请求
     * 前面几个步骤和异步方式一样，只是最后一部是通过 Call#execute() 来提交请求，
     * 注意这种方式会阻塞调用线程
     */
    private static void synGet() {
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    logger.info("run: {}", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * get异步请求
     * -new OkHttpClient;
     * -构造Request对象；
     * -通过前两步中的对象构建Call对象；
     * -通过Call#enqueue(Callback)方法来提交异步请求；
     * 异步发起的请求会被加入到 Dispatcher 中的 runningAsyncCalls双端队列中通过线程池来执行
     */
    private static void asynGet() {
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                logger.info("onFailure: ", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                logger.info("onResponse: {}", response.body().string());
            }
        });
    }
}
