import okhttp3.*;

import java.io.File;
import java.io.IOException;

public class Main {
    static String token = "s3fTe54VUofxew3dDaJiv045Nr7kkZFd";
    static Object object = new Object();
    static String host = "https://sm.ms/api/v2";


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

    public static void main(String[] args) throws InterruptedException {
        readFile();
//        apiRequest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    unLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        lock();

        System.out.println("程序结束了， 再见");
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

    public static String getUserAgent() {
        return "PostmanRuntime/7.24.0";
    }


}
