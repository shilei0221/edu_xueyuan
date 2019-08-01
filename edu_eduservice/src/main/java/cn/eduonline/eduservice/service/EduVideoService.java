package cn.eduonline.eduservice.service;

import cn.eduonline.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Alei
 * @since 2019-07-21
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean removeVideoInfo(String videoId);

    //根据课程 id 删除 小节和视频
    void removeVideoByCourseId(String courseId);
}
