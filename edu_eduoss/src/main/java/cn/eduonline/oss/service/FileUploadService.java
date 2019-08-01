package cn.eduonline.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Alei
 * @create 2019-07-14 12:59
 */
public interface FileUploadService {

    String uploadFileOss(MultipartFile file);
}
