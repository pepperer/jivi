import com.hiy.utils.ThreadUtils;
import okhttp3.*;

import java.io.File;
import java.io.IOException;

public class Main {
    static String token = "s3fTe54VUofxew3dDaJiv045Nr7kkZFd";
    static Object object = new Object();
    static String host = "https://sm.ms/api/v2";

    static File readFile() {


        String fileName = Main.class.getClassLoader().getResource("./image/test1.png").getPath();//获取文件路径
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
//        ThreadUtils.printCurThreadId();
        HiyExecutorService.getThreadPoolExecutor().execute(new Runnable() {
            @Override
            public void run() {
//                apiRequest();
//                ThreadUtils.printCurThreadId();
//                apiRequestPost("https://nei.netease.com/api/apimock-v2/4a12794f695e0b5d92226048f6b59774/post");
//                apiRequestPost("https://sm.ms/api/v2/token");

                apiPostFile();
            }
        });

//        System.out.println("Arrive one piece");
    }

    public static void apiRequest() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://sm.ms/api/v2/history")
                .newBuilder();
        urlBuilder.addQueryParameter("format", "json");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
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
                .addFormDataPart("smfile", "lsd.png", filebody)
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
