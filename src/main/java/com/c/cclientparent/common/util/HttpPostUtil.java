package com.c.cclientparent.common.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpPostUtil {
	URL url;  
	HttpURLConnection conn;
    String boundary = "--------httppost123";  
    Map<String, String> textParams = new HashMap<>();
    Map<String, FileInputStream> fileparams = new HashMap<>();
    DataOutputStream ds;  
    String filename;
    public HttpPostUtil(String url, String filename) throws Exception {
        this.url = new URL(url); 
        this.filename = filename;
    }
    /**
     * 重新设置要请求的服务器地址，即上传文件的地址
     */
    public void setUrl(String url) throws Exception {  
        this.url = new URL(url);  
    }

    /**
     * 增加一个普通字符串数据到form表单数据中
      * @param name
     * @param value
     */
    public void addTextParameter(String name, String value) {  
        textParams.put(name, value);  
    }

    /**
     * 增加一个文件到form表单数据中
      * @param name
     * @param value
     */
    public void addFileParameter(String name, FileInputStream value) {  
        fileparams.put(name, value);  
    }

    /**
     * 清空所有已添加的form表单数据
      */
    public void clearAllParameters() {  
        textParams.clear();  
        fileparams.clear();  
    }

    /**
     * 发送数据到服务器，返回一个字节包含服务器的返回结果的数组
     */
    public byte[] send() throws Exception {
        initConnection();  
        try {  
            conn.connect();  
        } catch (SocketTimeoutException e) {  
            // something  
            throw new RuntimeException();  
        }  
        ds = new DataOutputStream(conn.getOutputStream());  
        writeFileParams();  
        writeStringParams();  
        paramsEnd();  
        InputStream in = conn.getInputStream();  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int b;  
        while ((b = in.read()) != -1) {  
            out.write(b);  
        }  
        conn.disconnect();  
        return out.toByteArray();  
    }

    /**
     * 文件上传的connection的一些必须设置
      * @throws Exception
     */
    private void initConnection() throws Exception {  
        conn = (HttpURLConnection) this.url.openConnection();
        conn.setDoOutput(true);  
        conn.setUseCaches(false);
        //连接超时为20秒
        conn.setConnectTimeout(20000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type",  
                "multipart/form-data; boundary=" + boundary);
    }

    /**
     * 普通字符串数据
     * @throws Exception
     */
    private void writeStringParams() throws Exception {  
        Set<String> keySet = textParams.keySet();  
        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {  
            String name = it.next();  
            String value = textParams.get(name);  
            ds.writeBytes("--" + boundary + "\r\n");  
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name  
                    + "\"\r\n");  
            ds.writeBytes("\r\n");  
            ds.writeBytes(encode(value) + "\r\n");  
        }  
    }

    /**
     * 文件数据
      * @throws Exception
     */
    private void writeFileParams() throws Exception {  
        Set<String> keySet = fileparams.keySet();  
        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {  
            String name = it.next();  
            FileInputStream value = fileparams.get(name);  
            ds.writeBytes("--" + boundary + "\r\n");  
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name  
                    + "\"; filename=\"" + encode(filename) + "\"\r\n");  
            ds.writeBytes("Content-Type: application/octet-stream\r\n");
            ds.writeBytes("\r\n");  
            ds.write(getBytes(value));  
            ds.writeBytes("\r\n");  
        }  
    }

    /**
     * 获取文件的上传类型，图片格式为image/png,image/jpg等。非图片为application/octet-stream
      * @param f
     * @return
     * @throws Exception
     */
    private String getContentType(File f) throws Exception {  
          
        ImageInputStream imagein = ImageIO.createImageInputStream(f);
        if (imagein == null) {  
            return "application/octet-stream";  
        }  
        Iterator<ImageReader> it = ImageIO.getImageReaders(imagein);  
        if (!it.hasNext()) {  
            imagein.close();  
            return "application/octet-stream";  
        }  
        imagein.close();
        //将FormatName返回的值转换成小写，默认为大写
        return "image/" + it.next().getFormatName().toLowerCase();
  
    }

    /**
     * 把文件转换成字节数组
      * @param fis
     * @return
     * @throws Exception
     */
    private byte[] getBytes(FileInputStream fis) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];  
        int n;  
        while ((n = fis.read(b)) != -1) {  
            out.write(b, 0, n);  
        }  
        fis.close();  
        return out.toByteArray();  
    }

    /**
     * 添加结尾数据
      * @throws Exception
     */
    private void paramsEnd() throws Exception {  
        ds.writeBytes("--" + boundary + "--" + "\r\n");  
        ds.writeBytes("\r\n");  
    }

    /**
     * 对包含中文的字符串进行转码，此为UTF-8。服务器那边要进行一次解码
      * @param value
     * @return
     * @throws Exception
     */
    private String encode(String value) throws Exception{  
        return URLEncoder.encode(value, "UTF-8");  
    }  
    public static void main(String[] args) throws Exception {
        //请求地址
    	String requestUrl = "";
        HttpPostUtil u = new HttpPostUtil(requestUrl,"test.pdf");  
        u.addFileParameter("file",new FileInputStream(new File("g://test.pdf")));  
        u.addTextParameter("v1", "v123");  
        u.addTextParameter("v2", "v222");
        byte[] b = u.send();  
        String result = new String(b,"utf-8");  
        System.out.println(result);  
  
    }  
}
