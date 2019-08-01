package cn.eduonline.eduservice.service;

import cn.eduonline.eduservice.entity.EduCourse;
import cn.eduonline.eduservice.entity.coursedto.CourseBaseInfoDto;
import cn.eduonline.eduservice.entity.coursedto.CourseInfoDto;
import cn.eduonline.eduservice.entity.form.CourseInfoForm;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Alei
 * @since 2019-07-17
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseForm(CourseInfoForm courseInfoForm);

    CourseInfoForm getInfoCourseId(String courseId);

    boolean updateCourseInfo(CourseInfoForm courseInfoForm);

    //根据课程id查询课程的信息(包含课程基本信息,描述,分类,讲师)
    CourseInfoDto getCourseInfoId(String courseId);

    //最终发布课程的方法
    boolean publishCourseStatus(String courseId);

    //删除课程的方法(章节小节视频)
    boolean removeCourseInChapterVideo(String courseId);

    //课程分页列表
    Map<String,Object> getPageCourse(Page<EduCourse> pageCourse);


    //1.编写 sql 语句查询课程基本信息
    CourseBaseInfoDto getCourseBaseInfo(String id);
}
