package cn.eduonline.eduservice.service;

import cn.eduonline.eduservice.entity.EduCourse;
import cn.eduonline.eduservice.entity.EduTeacher;
import cn.eduonline.eduservice.entity.query.TeacherQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Alei
 * @since 2019-07-09
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //条件查询带带分页
    void getTeacherPage(Page<EduTeacher> pageTeacher, TeacherQuery teacherQuery);

    //删除讲师
    boolean removeTeacherById(String id);

    //讲师列表分页   前台系统
    Map<String,Object> findTeacherFrontPage(Page<EduTeacher> teacherPage);

    //2.根据讲师 id 查询讲师所讲课程
    List<EduCourse> getCourseByTeacherId(String id);
}
