package cn.eduonline.eduservice.service;

import cn.eduonline.eduservice.entity.EduChapter;
import cn.eduonline.eduservice.entity.chaptervideodto.ChapterDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Alei
 * @since 2019-07-21
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterDto> getAllVideoChapter(String courseId);

    boolean removeChapterId(String chapterId);

    //根据课程 id 删除 章节
    void removeChapterByCourseId(String courseId);
}
