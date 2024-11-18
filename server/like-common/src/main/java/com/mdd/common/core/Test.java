package com.mdd.common.core;

import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class Test {


    public static void main(String[] args) {
        // 七牛云存储空间相关信息
        String accessKey = "JZ4KMVfmkE8-FkSOZarSDqmltbVxG-2K30FmpjW_";
        String secretKey = "J8mh4K_sSVpM3doK5FG_gmctPo2MHVSwSLuHs9l6";
        String bucket = "xmgai";

        // 初始化七牛云配置
        Configuration cfg = new Configuration();
        UploadManager uploadManager = new UploadManager(cfg);

        try {
            // 生成上传凭证
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            // 要上传的字节数组
            byte[] data = "Hello, Qiniu Cloud!".getBytes();

            // 指定上传的文件名
            String key = "hello.txt";

            // 上传字节数据
            Response response = uploadManager.put(data, key, upToken);

            // 解析上传结果
            if (response.isOK()) {
                System.out.println("字节数据上传成功！");
            } else {
                System.err.println("字节数据上传失败，错误信息：" + response.error);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
