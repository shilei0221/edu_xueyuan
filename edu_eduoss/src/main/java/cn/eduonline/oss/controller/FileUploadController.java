package cn.eduonline.oss.controller;

import cn.eduonline.common.R;
import cn.eduonline.oss.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Alei
 * @create 2019-07-14 12:51
 */
@RestController
@RequestMapping("/eduoss/upload")
@CrossOrigin
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * <input type="file" name="file"></input>
     * MultiparFile名称和表单文件上传项中name值一样
     * @return
     */
    //上传头像到阿里云服务器oss
    @PostMapping
    public R uploadFoleOss(MultipartFile file) {

        //获取上传文件
        //调用service中的实现
        //上传之后,返回阿里云oss路劲
        String fileUrl = fileUploadService.uploadFileOss(file);

        return R.ok().data("url",fileUrl);
    }
}
