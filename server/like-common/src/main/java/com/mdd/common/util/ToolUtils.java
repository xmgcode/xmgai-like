package com.mdd.common.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.mdd.common.config.GlobalConfig;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * 常用工具集合
 */
public class ToolUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final int API_KEY_LENGTH = 32; // API key length
    private static final int API_SECRET_LENGTH = 64; // API secret length

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    /**
     * 制作UUID
     *
     * @author fzr
     * @return String
     */
    public static String makeUUID(){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,32);
    }

    /**
     * 制作MD5
     *
     * @author fzr
     * @param data 需加密的数据
     * @return String
     */
    public static String makeMd5(String data){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte [] array = md5.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 生成唯一Token
     *
     * @author fzr
     * @return String
     */
    public static String makeToken() {
        long millisecond =  System.currentTimeMillis();
        String randStr =  ToolUtils.randomString(8);
        String secret  = GlobalConfig.secret;
        String token   = ToolUtils.makeMd5(ToolUtils.makeUUID() + millisecond + randStr);
        return ToolUtils.makeMd5(token + secret) + ToolUtils.randomString(6);
    }

    /**
     * 返回随机字符串
     *
     * @author fzr
     * @param length 要生成的长度
     * @return String
     */
    public static String randomString(int length) {
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();
        String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int strLength = str.length();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(strLength);
            stringBuffer.append(str.charAt(index));
        }
        return stringBuffer.toString();
    }

    /**
     * 返回随机数字字符串
     *
     * @author fzr
     * @param length 要生成的长度
     * @return String
     */
    public static String randomInt(int length) {
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();
        String str = "0123456789";
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(10);
            stringBuffer.append(str.charAt(index));
        }
        return stringBuffer.toString();
    }

    /**
     * 转换存储单位: KB MB GB TB
     *
     * @author fzr
     * @return String
     */
    public static String storageUnit(Long size) {
        if (size == null) {
            return "0B";
        }
        if (size < 1024) {
            return size + "B";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            return size + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            size = size * 100;
            return (size / 100) + "." + (size % 100) + "MB";
        } else {
            size = size * 100 / 1024;
            return (size / 100) + "." + (size % 100) + "GB";
        }
    }

    /**
     * 下载文件
     *
     * @author fzr
     * @param urlStr   (文件网址)
     * @param savePath (保存路径,如: /www/uploads/aa.png)
     * @throws IOException IO异常
     */
    public static void download(String urlStr, String savePath) throws IOException {
        ByteArrayOutputStream bos = null;
        FileOutputStream fos = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5*1000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inputStream = conn.getInputStream();

            // 获取数组数据
            byte[] buffer = new byte[4*1024];
            int len;
            bos = new ByteArrayOutputStream();
            while((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            byte[] getData = bos.toByteArray();

            // 新创建文件夹
            String fileName = StringUtils.substringAfterLast(savePath, "/");
            String path = savePath.replace("/"+fileName, "");
            File saveDir = new File(path);
            if(!saveDir.exists()) {
                if (!saveDir.mkdirs()) {
                    throw new IOException("创建存储文件夹失败");
                }
            }
            // 保存文件数据
            File file = new File(savePath);
            fos = new FileOutputStream(file);
            fos.write(getData);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException ignored) {}
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ignored) {}
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {}
            }
        }
    }

    /**
     * 创建签名
     * @return
     */
    public static String createSign(String apiKey,String apiSecret){
        Map<String,String> map = new HashMap<>();
        map.put("apiKey",apiKey);
        map.put("apiSecret",apiSecret);
        String jsonParams =  JSON.toJSONString(map);
        String sign = md5(jsonParams);
        return sign;

    }




    /**
     * 创建签名
     * @return
     */
    public static String createSignWithNonce(String nonce,String timestamp,String apiKey,String apiSecret){
        Map<String,String> map = new HashMap<>();
        map.put("nonce",nonce);
        map.put("timestamp",timestamp);
        map.put("apiKey",apiKey);
        map.put("apiSecret",apiSecret);
        String jsonParams =  JSON.toJSONString(map);
        String sign = md5(jsonParams);
        return sign;

    }


    /**
     * MD5加密
     * @param input
     * @return
     */
    public static String md5(String input) {
        try {
            // 创建MD5加密对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 执行加密操作
            byte[] messageDigest = md.digest(input.getBytes());
            // 将字节数组转换为16进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            // 返回加密后的字符串
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }







    public static String getApiKey() {
        StringBuilder sb = new StringBuilder(API_KEY_LENGTH);
        for (int i = 0; i < API_KEY_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public static String getApiSecret() {
        StringBuilder sb = new StringBuilder(API_SECRET_LENGTH);
        for (int i = 0; i < API_SECRET_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }


    public static String imageToBase64(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        byte[] imageBytes = Files.readAllBytes(path);
        return Base64.getEncoder().encodeToString(imageBytes);
    }


    /**
     * 将 URL 转换为 MultipartFile 对象。
     *
     * @param imageUrl 图片的 URL 地址
     * @return MultipartFile 对象
     * @throws IOException 如果发生 I/O 错误
     */
    public static MultipartFile convertUrlToMultipartFile(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0"); // 添加User-Agent

        try (InputStream in = connection.getInputStream()) {
            // 使用UUID生成唯一的文件名
            String fileName = UUID.randomUUID().toString() + ".png";
            File file = new File(fileName);
            Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

            MultipartFile multipartFile = new MockMultipartFile(
                    file.getName(),
                    file.getName(),
                    "image/png",
                    Files.readAllBytes(file.toPath())
            );

            // 确保在使用完毕后删除文件
            if (!file.delete()) {
                file.deleteOnExit(); // 如果删除失败，则在JVM退出时删除
            }

            return multipartFile;
        }
    }







    public static String packageSign(Map<String, String> params, boolean urlEncoder) {
        // 先将参数以其参数名的字典序升序进行排序
        TreeMap<String, String> sortedParams = new TreeMap<String, String>(params);
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> param : sortedParams.entrySet()) {
            String value = param.getValue();
            if (StringUtils.isBlank(value)) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(param.getKey()).append("=");
            if (urlEncoder) {
                try {
                    value = urlEncode(value);
                } catch (UnsupportedEncodingException e) {
                }
            }
            sb.append(value);
        }
        return sb.toString();
    }

    public static String urlEncode(String src) throws UnsupportedEncodingException {
        return URLEncoder.encode(src, Charsets.UTF_8.name()).replace("+", "%20");
    }

    public static String createSignMap(Map<String, String> params, String partnerKey) {
        // 生成签名前先去除sign
        params.remove("sign");
        String stringA = packageSign(params, false);
        String stringSignTemp = stringA + "&key=" + partnerKey;
        return md5(stringSignTemp).toUpperCase();
    }

    public static void main(String []args){
        HashMap map = new HashMap();

        HashMap jsonMap = new HashMap();
        jsonMap.put("xmg","xmgcode");
        String jsonStr = JSON.toJSONString(jsonMap);

        map.put("mch_id","1679843739");
        map.put("callback_url","http://localhost:123456");
//        map.put("params",jsonStr);
        map.put("sign","");
        String sign = createSignMap(map,"D65472414418413BA1C90B6148DEDBA7");
        System.out.println(sign);
    }





    /**
     * 获取结果集的内容
     *
     * @param result
     * @return
     */
    public static <T> T getData(String result, Class<T> beanType) {
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        Map<String, Object> dataMap = (Map<String, Object>) jsonToObject(result, Map.class);
        if ("success".equals(dataMap.get("code"))) {
            Map<String, Object> data = (Map<String, Object>) dataMap.get("data");
            T t = mapToPojo(data, beanType);
            return t;
        }
        return null;
    }



    /**
     * 将map转换成pojo
     *
     * @param map
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T mapToPojo(Map<String, Object> map, Class<T> beanType) {

        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();

        JsonElement jsonElement = gson.toJsonTree(map);
        T pojo = gson.fromJson(jsonElement, beanType);

        return pojo;
    }



    /**
     * 把json字符串转化为对象
     *
     * @param jsonString
     * @param clazz
     * @return
     */
    public static Object jsonToObject(String jsonString, Class<?> clazz) {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Object obj = null;
        try {
            obj = gson.fromJson(jsonString, clazz);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return obj;
    }





    public static String objectToJson(Object obj) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            String json = gson.toJson(obj);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将json结果集转化为对象
     *
     * @param jsonData
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
