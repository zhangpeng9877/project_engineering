package com.itheima.health.test;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

/**
 * 测试七牛云文件上传
 */
public class TestQiniu {

    // 上传本地文件
    @Test
    public void uploadFile() {
        //构造一个带指定Zone对象的配置类
        // 参数里面配置和储存区域相关，目前使用的是华南区域，需要使用Zone.zone2()对象
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "XL_S-zzc_LKgS5lR-yuEDMxBJds9pwnvp23ntcsG";
        String secretKey = "gsoJamfPfuIDoR0UocoO-4DTQweyn2W_fhG73W-_";
        String bucket = "zpxiaoqi";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png，可支持中文
        String localFilePath = "D:\\Desktop\\班级座位表.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    // 删除空间中的文件
    @Test
    public void deleteFile(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        String accessKey = "XL_S-zzc_LKgS5lR-yuEDMxBJds9pwnvp23ntcsG";
        String secretKey = "gsoJamfPfuIDoR0UocoO-4DTQweyn2W_fhG73W-_";
        String bucket = "zpxiaoqi";
        String key = "FgbjzDZK4XOtFWsTbtyEB40ws6D_";    // 需要删除的文件名
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}