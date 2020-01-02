package com.cms.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
/**
 *
 * OSS 工具类
 */
public class OSSUtils {

    public static void upload(String path, File file, String contentType){
        String endpoint = "http://fs.jrecms.com";    // Endpoint以杭州为例，其它Region请按实际情况填写。
        String accessId = "LTAIVonUUTMd0ieE";    //AccessKey ID 
        String accessKey = "2sBsWbCBfP1EvFeh6TlZeqPa6ehnga";   //Access Key Secret    
        String bucketName = "jrecms";  //桶名称
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            OSSClient ossClient = new OSSClient(endpoint, accessId, accessKey);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);
            objectMetadata.setContentLength(file.length());
            ossClient.putObject(bucketName, StringUtils.removeStart(path, "/"), inputStream, objectMetadata);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
