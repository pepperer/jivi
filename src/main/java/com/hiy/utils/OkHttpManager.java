package com.hiy.utils;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class OkHttpManager {


    private static class Host {
        static OkHttpManager instance = new OkHttpManager();
    }

    public static OkHttpManager getInstance() {
        return Host.instance;
    }


    // ---- 成员函数

    private OkHttpManager() {
        initClient();
    }


    private OkHttpClient mOkHttpClient;


    private File readFile(String path) {
        String fileName = Main.class.getClassLoader().getResource("./cache").getPath();//获取文件路径
        System.out.println(new File(fileName).exists());
//        String fileUtl = Main.class.getResource("image/test1.png").getFile();
//        System.out.println(new File(fileUtl).exists());


//        System.out.println("当前路径L" + fileName + "\n " + fileUtl);

//        File file = new File(fileName);
//        System.out.println("当前路径L" + file.exists());

        return new File(fileName);
    }

    private void initClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    //设置缓存文件路径，和文件大小
//                    .cache(new Cache(FileUtils.newFile("cache/"), 50 * 1024 * 1024))
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
//                    .sslSocketFactory(SSLSocketFactory1.createSSLSocketFactory())
//                    .hostnameVerifier(new SSLSocketFactory1.TrustAllHostnameVerifier())
                    //这里是网上对cookie的封装 github : https://github.com/franmontiel/PersistentCookieJar
                    //如果你的项目没有遇到cookie管理或者你想通过网络拦截自己存储，那么可以删除persistentcookiejar包
//                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApplication.getContext())))
//                    .addInterceptor(OfflineCacheInterceptor.getInstance())
//                    .addNetworkInterceptor(NetCacheInterceptor.getInstance())
                    .addNetworkInterceptor(this.getHttpLoggingInterceptor())
                    .build();
        }
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    // 私有

    /**
     * @return 获取http log 日志
     */
    private Interceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                System.out.println("日志 =>" + message);
            }
        });

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return httpLoggingInterceptor;
    }
}
