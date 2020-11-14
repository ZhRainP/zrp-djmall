package com.dj.mall.common.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.InputStream;

/**
 * 七牛云工具类
 *
 */
public class QiNiuUtils {

    /**
     * 密钥AK
     */
    private static final String ACCESSKEY = "ZIAQdlvJs3U1jObd4IwlXE5U-TNsQhYrLnkvCpPL";

    /**
     * 密钥SK
     */
    private static final String SECRETKEY = "ZJ9eGZvLI3GFpDd2dM6BFBqVCHxAkv6AiZTMuNWQ";

    /**
     * 存储空间名称
     */
    private static final String BUCKET = "zrp-demo";
    /**
     * 生成签名
     */
    private static Auth auth = Auth.create(ACCESSKEY, SECRETKEY);
    /**
     * 上传令牌
     */

    private static String upToken = auth.uploadToken(BUCKET);

    /**
     * 链接
     */
    public static final String URL = "http://qjrxidgn4.hb-bkt.clouddn.com//";

    /**
     * 配置类
     */
    private static Configuration cfg = new Configuration(Region.autoRegion());
    /**
     * 文件管理器
     */
    private static UploadManager uploadManager = new UploadManager(cfg);


    /**
     * 通过输入流上传
     *
     * @param inputStream 要上传的文件
     * @param fileName    文件名
     */
    public static void uploadByInputStream(InputStream inputStream, String fileName) {
        try {
            uploadManager.put(inputStream, fileName, upToken, null, null);
            System.out.println("上传成功");
        } catch (QiniuException ex) {
            System.err.println("上传失败");
            ex.printStackTrace();
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
            }
        }
    }

    /**
     * 通过字节数组上传
     *
     * @param file     要上传的文件
     * @param fileName 文件名
     */
    public static void uploadByByte(byte[] file, String fileName) {
        try {
            uploadManager.put(file, fileName, upToken);
            System.out.println("上传成功");
        } catch (QiniuException ex) {
            System.err.println("上传失败");
            ex.printStackTrace();
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
            }
        }
    }

    /**
     * 根据文件名删除文件
     *
     * @param fileName 文件名
     */
    public static void deleteFile(String fileName) {
        try {
            BucketManager bucketManager = new BucketManager(auth, cfg);
            bucketManager.delete(BUCKET, fileName);
            System.out.println("删除成功");
        } catch (QiniuException ex) {
            System.out.println("删除失败");
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

}
