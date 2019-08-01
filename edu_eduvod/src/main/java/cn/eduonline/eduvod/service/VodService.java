package cn.eduonline.eduvod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Alei
 * @create 2019-07-25 21:47
 */
public interface VodService {
    //上传视频
    String uploadAliyunVideo(MultipartFile file);

    //删除阿里云视频
    void deleteAliyunVideoById(String videoId);

    //删除多个视频的方法
    void removeVideoMore(List<String> videoList);

    //根据视频id获取视频播放凭证
    String getPlayAuthVideo(String vid);
}
