package cn.eduonline.eduservice.controller.front;

import cn.eduonline.common.R;
import cn.eduonline.eduservice.entity.EduCourse;
import cn.eduonline.eduservice.entity.chaptervideodto.ChapterDto;
import cn.eduonline.eduservice.entity.coursedto.CourseBaseInfoDto;
import cn.eduonline.eduservice.service.EduChapterService;
import cn.eduonline.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Alei
 * @create 2019-07-28 20:12
 *
 * 前台系统课程接口
 */
@RestController
@RequestMapping("/eduservice/frontcourse")
@CrossOrigin
public class FrontCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    //课程分页列表
    @GetMapping("getCoursePage/{page}/{limit}")
    public R getCoursePate(@PathVariable long page,@PathVariable long limit) {

        Page<EduCourse> pageCourse = new Page<>(page,limit);

        Map<String,Object> map = eduCourseService.getPageCourse(pageCourse);

        return R.ok().data(map);
    }

    //根据课程id查询课程详细信息
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable String id) {

        //1.编写 sql 语句查询课程基本信息
        CourseBaseInfoDto courseBaseInfoDto = eduCourseService.getCourseBaseInfo(id);

        //2.调用方法得到章节和小节数据
        List<ChapterDto> allVideoChapter = eduChapterService.getAllVideoChapter(id);

        return R.ok().data("course",courseBaseInfoDto).data("chapterVideoList",allVideoChapter);
    }
}
