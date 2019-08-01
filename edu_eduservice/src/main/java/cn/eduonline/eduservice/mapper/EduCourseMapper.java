package cn.eduonline.eduservice.mapper;

import cn.eduonline.eduservice.entity.EduCourse;
import cn.eduonline.eduservice.entity.coursedto.CourseBaseInfoDto;
import cn.eduonline.eduservice.entity.coursedto.CourseInfoDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Alei
 * @since 2019-07-17
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //根据课程id查询课程的信息(包含课程基本信息,描述,分类,讲师)
    CourseInfoDto getAllCourseInfo(String courseId);

    //1.编写 sql 语句查询课程基本信息
    CourseBaseInfoDto getBaseCourseInfo(String id);
}
