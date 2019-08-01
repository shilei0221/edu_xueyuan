package cn.eduonline.oss.service.impl;

import cn.eduonline.oss.service.FileUploadService;
import cn.eduonline.oss.util.ConstantPropertiesUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import lombok.val;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author Alei
 * @create 2019-07-14 12:59
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    //上传文件到阿里云oss服务
    @Override
    public String uploadFileOss(MultipartFile file) {

        try {
            //oss相关值
            String endpoint = ConstantPropertiesUtil.END_POINT;
            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
            String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

            // 创建OSSClient实例。
            OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

            //上传文件流
            InputStream inputStream = file.getInputStream();

            // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（fileName）。
            String fileName = file.getOriginalFilename();

            //第一个优化  优化名字的重复覆盖bug
            //uuid 让每个文件上传的名称都是唯一的
            String uuid = UUID.randomUUID().toString();

            //将uuid的值拼接到文件名的前面,注意不可以拼接在后面,因为会覆盖后面的后缀名

            fileName = uuid+fileName;

            //优化二： 将上传的文件按照天进行存储  2019/07/14
            String dataUrl = new DateTime().toString("yyyy/MM/dd");

            fileName = dataUrl+"/"+fileName;

            ossClient.putObject(bucketName, fileName,inputStream );

            //拼接上传文件后生成的oss文件地址
            String ossUrl = "https://"+bucketName+"."+endpoint+"/"+fileName;

            // 关闭OSSClient。
            ossClient.shutdown();

            return ossUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
