package com.mdd.common.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class HttpClientUtil {

    // 日志打印
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);


    /**
     * get请求
     * @return
     */
    public static String doGet(String url) {
        try {
            HttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());

                return strResult;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * post请求(用于key-value格式的参数)
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map params){

        BufferedReader in = null;
        try {
            // 定义HttpClient
            HttpClient client = new DefaultHttpClient();
            // 实例化HTTP方法
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));

            //设置参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String value = String.valueOf(params.get(name));
                nvps.add(new BasicNameValuePair(name, value));

                log.debug("参数列表："+name +"-"+value);
            }
            request.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));

            HttpResponse response = client.execute(request);


            int code = response.getStatusLine().getStatusCode();


            log.debug("当前的状态码："+code);
            if(code == 200){    //请求成功
                in = new BufferedReader(new InputStreamReader(response.getEntity()
                        .getContent(),"utf-8"));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }

                in.close();

                log.debug("sb："+sb.toString());
                return sb.toString();
            }
            else{
                log.debug("状态码：" + code);
                return null;
            }
        }
        catch(Exception e){
            e.printStackTrace();

            return null;
        }
    }



    /**
     * 发送get请求。支持Restful风格、参数拼接URL，并提供请求参数拼接功能
     * @param httpUrl   请求URL
     * @param header    请求头字段信息
     * @param data      请求数据
     * @param timeout   请求超时时间设置，单位毫秒。默认30s
     * @return  result  返回请求结果
     */
    public static String doGetParams(String httpUrl,Map<String,String> header,Map<String,Object> data,int timeout){

        HttpURLConnection connect = null;
        //返回请求结果
        StringBuilder result = new StringBuilder();

        try {

            if (Objects.nonNull(data) && data.size() > 0) {
                //构造请求参数
                StringBuilder paramBuilder = new StringBuilder();
                paramBuilder.append(httpUrl).append("?");
                Set<Map.Entry<String, Object>> entries = data.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    paramBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                httpUrl = paramBuilder.substring(0,paramBuilder.length() - 1);
            }

            //构造访问地址的URL
            URL url = new URL(httpUrl);
            //获取网络访问对象
            connect = (HttpURLConnection)url.openConnection();

            //设置请求参数：请求方式、请求过期时间、是否允许写入和读入、是否使用缓存、是否自动执行HTTP重定向
            connect.setRequestMethod("GET");
            connect.setConnectTimeout(timeout <=0 ? 30000 : timeout);
            //connect.setReadTimeout(timeout <=0 ? 30000 : timeout);
            connect.setDoInput(true);
            connect.setDoOutput(false);
            connect.setUseCaches(false);
            connect.setInstanceFollowRedirects(true);

            //设置请求头,比如Content-Type、accept等
            if (!Objects.isNull(header) && header.size() > 0) {
                Set<Map.Entry<String, String>> entrySet = header.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    connect.setRequestProperty(entry.getKey(),entry.getValue());
                }
            }

            //连接请求
            connect.connect();

            //获取返回结果
            if (connect.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream(),StandardCharsets.UTF_8));
                String lines = null;
                while ((lines = reader.readLine()) != null) {
                    result.append(lines);
                }
            } else {
                return result.append("请求失败").toString();
            }
        } catch (IOException e) {
            log.error("发送get请求错误,请求URL：{},请求参数：{},错误信息：{}",httpUrl,data,e.getMessage());
        } finally {
            if (!Objects.isNull(connect)) {
                connect.disconnect();
            }
        }

        return result.toString();
    }



    /**
     * 发送get请求。支持Restful风格、参数拼接URL，并提供请求参数拼接功能
     * @param httpUrl   请求URL
     * @param header    请求头字段信息
     * @param data      请求数据
     * @param timeout   请求超时时间设置，单位毫秒。默认30s
     * @return  result  返回请求结果
     */
    public static String doPostParams(String httpUrl,Map<String,String> header,Map<String,Object> data,int timeout){

        HttpURLConnection connect = null;
        //返回请求结果
        StringBuilder result = new StringBuilder();

        try {

            if (Objects.nonNull(data) && data.size() > 0) {
                //构造请求参数
                StringBuilder paramBuilder = new StringBuilder();
                paramBuilder.append(httpUrl).append("?");
                Set<Map.Entry<String, Object>> entries = data.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    paramBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                httpUrl = paramBuilder.substring(0,paramBuilder.length() - 1);
            }

            //构造访问地址的URL
            URL url = new URL(httpUrl);
            //获取网络访问对象
            connect = (HttpURLConnection)url.openConnection();

            //设置请求参数：请求方式、请求过期时间、是否允许写入和读入、是否使用缓存、是否自动执行HTTP重定向
            connect.setRequestMethod("POST");
            connect.setConnectTimeout(timeout <=0 ? 30000 : timeout);
            //connect.setReadTimeout(timeout <=0 ? 30000 : timeout);
            connect.setDoInput(true);
            connect.setDoOutput(false);
            connect.setUseCaches(false);
            connect.setInstanceFollowRedirects(true);

            //设置请求头,比如Content-Type、accept等
            if (!Objects.isNull(header) && header.size() > 0) {
                Set<Map.Entry<String, String>> entrySet = header.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    connect.setRequestProperty(entry.getKey(),entry.getValue());
                }
            }

            //连接请求
            connect.connect();

            //获取返回结果
            if (connect.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream(),StandardCharsets.UTF_8));
                String lines = null;
                while ((lines = reader.readLine()) != null) {
                    result.append(lines);
                }
            } else {
                return result.append("请求失败").toString();
            }
        } catch (IOException e) {
            log.error("发送get请求错误,请求URL：{},请求参数：{},错误信息：{}",httpUrl,data,e.getMessage());
        } finally {
            if (!Objects.isNull(connect)) {
                connect.disconnect();
            }
        }

        return result.toString();
    }

    /**
     * 发送post请求。必须通过header参数设置Content-Type类型
     * @param httpUrl   请求url
     * @param header    请求头设置
     * @param data      请求数据
     * @param timeout   请求超时时间
     * @return result   请求结果
     */
    public static String doPost(String httpUrl,Map<String,String> header,String data,int timeout){

        HttpURLConnection connection = null;
        BufferedWriter writer = null;
        BufferedReader reader = null;
        //返回请求结果
        StringBuilder result = new StringBuilder();

        try {
            //构造访问地址的URL
            URL url = new URL(httpUrl);
            //获取网络访问对象
            connection = (HttpURLConnection)url.openConnection();

            //设置请求参数：请求方式、请求过期时间、是否允许写入和读入、请求参数为JSON
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(timeout <=0 ? 30000 : timeout);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");

            //设置请求头,比如Content-Type、accept等
            if (!Objects.isNull(header) && header.size() > 0) {
                Set<Map.Entry<String, String>> entries = header.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    connection.setRequestProperty(entry.getKey(),entry.getValue());
                }
            }

            //获取HttpURLConnection对象对应的输出流，进行请求接口
            writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
            writer.write(data);
            writer.flush();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //获取HttpURLConnection对象对应的输入流，获取返回结果
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String lines = null;
                while ((lines = reader.readLine()) != null) {
                    result.append(lines);
                }
            } else {
                result.append("请求失败");
            }
        } catch (IOException e) {
            log.error("发送post请求错误,请求URL：{},请求参数：{},错误信息：{}",httpUrl,data,e.getMessage());
        } finally {
            //释放链接
            releaseConnection(reader,writer,connection);
        }

        return result.toString();
    }

    /**
     * 释放链接
     * @param reader   读取流
     * @param writer   写出流
     * @param connection    连接
     */
    public static void releaseConnection(BufferedReader reader,BufferedWriter writer,HttpURLConnection connection) {
        if (!Objects.isNull(reader)) {
            try {
                reader.close();
            } catch (IOException e) {
                log.error("关闭读取流异常");
            }
        }

        if (!Objects.isNull(writer)) {
            try {
                writer.close();
            } catch (IOException e) {
                log.error("关闭写出流异常");
            }
        }

        if (!Objects.isNull(connection)) {
            connection.disconnect();
        }
    }
}
