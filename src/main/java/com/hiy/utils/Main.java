package com.hiy.utils;

import okhttp3.*;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    static String token = "s3fTe54VUofxew3dDaJiv045Nr7kkZFd";
    static Object object = new Object();
    static String host = "https://sm.ms/api/v2";
    static int downloadIndex = 0;


    static Object mObj = new Object();

    static void lock() {
        try {
            synchronized (mObj) {
                mObj.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void unLock() {
        synchronized (mObj) {
            mObj.notify();
        }
    }


    public static void main(String[] args) throws InterruptedException {
//        File file = FileUtils.newFile("xxx.txt");
//        ByteString byteString = ByteString.encodeString("hello world", Charset.forName("UTF-8"));
//        byte[] bytes = byteString.toByteArray();
//        FileUtils.write(file, byteString.toByteArray(), 0, 5);
//
//        FileUtils.write(file, byteString.toByteArray(), 5, bytes.length - 5);
//
//        FileUtils.write(file, byteString.toByteArray(), 0, bytes.length);

        apiRequestGetImageSize();
        System.out.println("程序结束了， 再见  ^^^^^");
    }


    static File readFile() {
        String fileName = Main.class.getClassLoader().getResource("./image/request_message.png").getPath();//获取文件路径
        System.out.println(new File(fileName).exists());
        String fileUtl = Main.class.getResource("image/test1.png").getFile();
        System.out.println(new File(fileUtl).exists());


        System.out.println("当前路径L" + fileName + "\n " + fileUtl);

        File file = new File(fileName);
        System.out.println("当前路径L" + file.exists());

        return new File(fileName);
    }

    public static void apiRequest() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://sm.ms/api/v2/history")
                .newBuilder();
        urlBuilder.addQueryParameter("format", "json");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("User-Agent", Main.getUserAgent())
                .addHeader("Authorization", "s3fTe54VUofxew3dDaJiv045Nr7kkZFd")
                .build();


        try {
            Response response = OkHttpManager.getInstance().getOkHttpClient().newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void apiRequestPost(String url) {
        RequestBody requestBody = new FormBody.Builder()
                .add("username", "liusaideng")
                .add("password", "l1994225")
                .add("key", "l1994225")
                .build();

//        MultipartBody requestBody = new MultipartBody.Builder()
//                .addFormDataPart("username", "liusaideng")
//                .addFormDataPart("password", "l1994225")
//                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "multipart/form-data;")
                .addHeader("User-Agent", Main.getUserAgent())
                .post(requestBody)
                .build();


        OkHttpManager.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("网络请求错误：" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();

                String retStr = responseBody.string();

                System.out.println("获取到结果是：" + retStr);
            }
        });
    }

    public static void apiPostFile() {
        MediaType mediaType = MediaType.parse("image/png");
        RequestBody filebody = MultipartBody.create(mediaType, readFile());

        RequestBody multipartBody = new MultipartBody.Builder()
                .addFormDataPart("smfile", String.valueOf(System.currentTimeMillis()) + ".png", filebody)
                .setType(MultipartBody.FORM)
                .build();


//    MultipartBody.create(mediaType, readFile());
//        multipartBody.
        Request request = new Request.Builder()
                .url(host + "/upload")
                .addHeader("Authorization", token)
                .addHeader("user-agent", Main.getUserAgent())
                .post(multipartBody)
                .build();

        OkHttpManager.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();

                String retStr = responseBody.string();

                System.out.println("上传图片的结构是：" + retStr);
            }
        });
    }

    public static void apiRequestGetImageSize() {
        Request request = new Request.Builder()
                .url("https://hbimg.huabanimg.com/2c7f99c1d8e42c100de8c92f1bdd6c92937c046a267f06-CP9vRv_fw658/format/webp")
                .head()
                .build();

        try {
            Response response = OkHttpManager.getInstance().getOkHttpClient().newCall(request).execute();
            long contentLength = Long.parseLong(response.header("content-length"));
//            long length =response.header("content-length");
            File file = FileUtils.newFile("download");
            int perSize = 100 * 1024;
            long count = contentLength / perSize + 1;
            System.out.print("需要下载次数 =》" + count);

            if (count == 2) {
                return;
            }
            for (int i = 0; i < count; i++) {
                long start, end;//每次一次下载的初始位置和结束位置
                if (i == count - 1) {
                    end = contentLength - 1;//因为从0开始的，所以要-1，比如10个字节，就是0-9
                } else {
                    end = (i + 1) * perSize - 1;
                }
                start = i * perSize;


                Request downloadRequest = new Request.Builder()
                        .addHeader("Range", String.format("bytes=%d-%d", start, end))
                        .url("https://hbimg.huabanimg.com/2c7f99c1d8e42c100de8c92f1bdd6c92937c046a267f06-CP9vRv_fw658/format/webp")
                        .build();

                int finalI = i;
                OkHttpManager.getInstance().getOkHttpClient().newCall(downloadRequest).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        synchronized (token) {
                            System.out.println("一波开始" + downloadIndex + "=>" + finalI);
                            if (downloadIndex != finalI) {
                                try {
                                    System.out.println("一波开始" + downloadIndex + "wait");
                                    token.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            System.out.println("一波开始" + downloadIndex + "执行");
                            InputStream inputStream = response.body().byteStream();//获取流
                            Source source = Okio.source(inputStream);
                            BufferedSource bufferedSource = Okio.buffer(source);
                            byte[] bytes = bufferedSource.readByteArray();
                            System.out.println("一波开始" + downloadIndex + "执行" + bytes.length);
                            FileUtils.write(file, bytes, 0, bytes.length);

                            response.close();
                            downloadIndex++;
                            token.notify();
                            System.out.println("一波结束" + downloadIndex);
                        }
                    }
                });
            }

            System.out.println("请求结果：" + contentLength);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static String getUserAgent() {
        return "PostmanRuntime/7.24.0";
    }
}
