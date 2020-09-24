package com.hiy.utils;

import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class FileUtils {

    private static final String TAG = "null";

    public static HashMap<String, Long> mMap = new HashMap();

    static File newFile(String fileName) {
        String rootPath = Main.class.getClassLoader().getResource("./image").getPath();//获取文件路径
        File file = new File(rootPath + "/" + fileName);
        try {
            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    static void write(File file, byte[] bytes, int offset, int byteCount) {
        BufferedSink bufferedSink = null;
        try {
            Sink sink = Okio.appendingSink(file);
            bufferedSink = Okio.buffer(sink);
            bufferedSink.write(bytes, offset, byteCount);
            bufferedSink.flush();
            bufferedSink.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }

//    public static void appendFileWithInstram(String fileName, InputStream inputStream, long start) {
//        File file = new File(fileName);//获取文件，我在下载之前就删除了原文件
//        synchronized (TAG) {//设置线程锁
//            try {
//                while (true) {//通过线程锁，对线程进行排序
//                    long fileLength = mMap.get(fileName) ? mMap.get(fileName) : 0;
//                    //记录文件长度
////                    JLog.d("FileUtil", "fileLength = " + fileLength +"\n start = "+ start);
//                    if (fileLength == start) {//当文件长度，与下载文件的初始值一样，则开始写入文件，否则等待
//                        break;
//                    }
//                    TAG.wait();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            RandomAccessFile randomAccessFile = null;
//            try {
//                if (!file.exists()) {
//                    file.createNewFile();
//                }
//                randomAccessFile = new RandomAccessFile(file, "rw");
//
//                final long M = 1024;
//                long total = mMap.get(fileName);
//                for (; ; ) {//循环读取写入文件
//                    byte[] bytes = new byte[(int) M];
//                    int readCount = inputStream.read(bytes);
//                    if (readCount == 0) {//因为请求是一部分，一部分的，不是一次性的，所以会存在0的情况,注意0！=-1
//                        continue;
//                    }
//                    if (readCount == -1) {//读取完成，结束循环
//                        mMap.put(fileName, total);//将写入的总长度记录下来
//                        inputStream.close();
//                        randomAccessFile.close();
//                        break;
//                    }
//
//                    randomAccessFile.seek(total);//设置偏移量
//                    total += readCount;
//                    //写入读取的总长度，不能只写randomAccessFile.write(bytes）会导致文件长度大于读取的长度
//                    randomAccessFile.write(bytes, 0, readCount);
//                }
//
//                TAG.notifyAll();//结束循环之后，唤醒其他线程进行写入
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
