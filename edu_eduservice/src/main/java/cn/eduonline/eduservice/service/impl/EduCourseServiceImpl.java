package cn.eduonline.eduservice.service.impl;

import cn.eduonline.common.handler.EduException;
import cn.eduonline.eduservice.entity.EduCourse;
import cn.eduonline.eduservice.entity.EduCourseDescription;
import cn.eduonline.eduservice.entity.coursedto.CourseBaseInfoDto;
import cn.eduonline.eduservice.entity.coursedto.CourseInfoDto;
import cn.eduonline.eduservice.entity.form.CourseInfoForm;
import cn.eduonline.eduservice.mapper.EduCourseMapper;
import cn.eduonline.eduservice.service.EduChapterService;
import cn.eduonline.eduservice.service.EduCourseDescriptionService;
import cn.eduonline.eduservice.service.EduCourseService;
import cn.eduonline.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Alei
 * @since 2019-07-17
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //注入描述的service
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduChapterService eduChapterService; //章节的service

    @Autowired
    private EduVideoService eduVideoService; //小节的service

    //添加课程信息
    @Override
    public String saveCourseForm(CourseInfoForm courseInfoForm) {

        //向两张表添加数据：课程基本信息表和课程描述表
        //1.添加课程基本信息
        //courseInfoForm 转换 eduCourse 对象
        EduCourse eduCourse = new EduCourse();

        BeanUtils.copyProperties(courseInfoForm,eduCourse);

        int insert = baseMapper.insert(eduCourse);

        if (insert <= 0) { //表示添加失败
            throw new EduException(20001,"添加课程信息失败");
        }

        String courseId = eduCourse.getId();

        //2.如果说课程信息添加成功,添加课程描述信息
        String description = courseInfoForm.getDescription();

        if (!StringUtils.isEmpty(description)) {

            EduCourseDescription eduCourseDescription = new EduCourseDescription();

            eduCourseDescription.setId(courseId); //设置课程id到描述对象中

            eduCourseDescription.setDescription(courseInfoForm.getDescription());

            boolean save = eduCourseDescriptionService.save(eduCourseDescription);

            return courseId;
        }

        return courseId;
    }


    //根据课程 id 查询课程信息
    @Override
    public CourseInfoForm getInfoCourseId(String courseId) {

        //1.查询课程表，得到课程基本信息
        EduCourse eduCourse = baseMapper.selectById(courseId);

        //把eduCourse对象的值 复制 到CourseInfoForm对象中
        CourseInfoForm courseInfoForm = new CourseInfoForm();

        BeanUtils.copyProperties(eduCourse,courseInfoForm);

        //2.根据课程id查询描述信息
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);

        if (courseDescription != null) {

            String description = courseDescription.getDescription();

            courseInfoForm.setDescription(description);
        }

        return courseInfoForm;
    }


    //修改课程信息的方法
    @Override
    public boolean updateCourseInfo(CourseInfoForm courseInfoForm) {

        //1.修改课程基本信息表
        //courseInfoForm 转换为 eduCourse 对象
        EduCourse eduCourse = new EduCourse();

        BeanUtils.copyProperties(courseInfoForm,eduCourse);

        int result = baseMapper.updateById(eduCourse);

        if (result <= 0) {
            throw new EduException(20001,"修改失败");
        }

        //2.修改课程描述表
        String description = courseInfoForm.getDescription();

        if (!StringUtils.isEmpty(description)) {

            EduCourseDescription eduCourseDescription = new EduCourseDescription();

            eduCourseDescription.setDescription(description);

            eduCourseDescription.setId(courseInfoForm.getId());

            boolean update = eduCourseDescriptionService.updateById(eduCourseDescription);

            return update;
        }
        return true;
    }

    //根据课程id查询课程的信息(包含课程基本信息,描述,分类,讲师)
    @Override
    public CourseInfoDto getCourseInfoId(String courseId) {

        //调用mapper里面的方法
        return baseMapper.getAllCourseInfo(courseId);
    }

    //最终发布课程的方法
    @Override
    public boolean publishCourseStatus(String courseId) {

        EduCourse eduCourse = new EduCourse();

        eduCourse.setId(courseId);

        eduCourse.setStatus("Normal");

        int result = baseMapper.updateById(eduCourse);

        return result > 0;
    }

    //删除课程的方法(章节小节视频)
    @Override
    public boolean removeCourseInChapterVideo(String courseId) {

        //1.根据课程 id 删除 小节和视频
        eduVideoService.removeVideoByCourseId(courseId);

        //2.根据课程 id 删除 章节
        eduChapterService.removeChapterByCourseId(courseId);

        //3.根据课程 id 删除 描述
        eduCourseDescriptionService.removeById(courseId);

        //4.根据课程 id 删除 课程
        int result = baseMapper.deleteById(courseId);

        return result > 0;
    }

    //课程分页列表
    @Override
    public Map<String, Object> getPageCourse(Page<EduCourse> pageCourse) {

        //查询已经发布课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        wrapper.eq("status","Normal");

        //根据字段排序 当条件传入
        wrapper.orderByAsc("gmt_modified");

        //调用方法分页查询 把分页所有数据封装到 pageCourse 对象里面
        baseMapper.selectPage(pageCourse,wrapper);

       //从 pageCourse 对象中获取分页数据封装到 map 集合
        List<EduCourse> records = pageCourse.getRecords();

        long current = pageCourse.getCurrent();

        long total = pageCourse.getTotal();

        long size = pageCourse.getSize();

        long pages = pageCourse.getPages();

        boolean hasNext = pageCourse.hasNext();

        boolean hasPrevious = pageCourse.hasPrevious();

        Map<String,Object> map = new HashMap<>();

        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    //1.编写 sql 语句查询课程基本信息
    @Override
    public CourseBaseInfoDto getCourseBaseInfo(String id) {

        return baseMapper.getBaseCourseInfo(id);
    }
}
