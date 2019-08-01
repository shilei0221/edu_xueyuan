package cn.eduonline.eduservice.controller.front;

import cn.eduonline.common.R;
import cn.eduonline.eduservice.entity.EduCourse;
import cn.eduonline.eduservice.entity.EduTeacher;
import cn.eduonline.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Alei
 * @create 2019-07-28 16:39
 *
 * 前台系统讲师接口
 */
@RestController
@RequestMapping("eduservice/frontteacher")
@CrossOrigin
public class FrontTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //讲师列表分页
    @GetMapping("listTeacherFront/{page}/{limit}")
    public R listTeacherPage(@PathVariable long page,@PathVariable long limit) {

        //创建page对象
        Page<EduTeacher> teacherPage = new Page<>(page,limit);

        //把分页所有数据都返回map
        Map<String,Object> map = eduTeacherService.findTeacherFrontPage(teacherPage);

        return R.ok().data(map);

    }

    //根据讲师 id 查询讲师信息和所讲课程
    @GetMapping("getTeacherInfoCourse/{id}")
    public R getTeacherInfoCourse(@PathVariable String id) {
        //1.根据讲师 id 查询讲师基本信息
        EduTeacher eduTeacher = eduTeacherService.getById(id);

        //2.根据讲师 id 查询讲师所讲课程
        List<EduCourse> list = eduTeacherService.getCourseByTeacherId(id);


        return R.ok().data("teacherInfo",eduTeacher).data("courseList",list);
    }

}
