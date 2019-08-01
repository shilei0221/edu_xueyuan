package cn.eduonline.eduservice.service.impl;

import cn.eduonline.common.handler.EduException;
import cn.eduonline.eduservice.entity.EduCourse;
import cn.eduonline.eduservice.entity.EduTeacher;
import cn.eduonline.eduservice.entity.query.TeacherQuery;
import cn.eduonline.eduservice.mapper.EduTeacherMapper;
import cn.eduonline.eduservice.service.EduCourseService;
import cn.eduonline.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Alei
 * @since 2019-07-09
 */
@Service
@Slf4j
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    private EduCourseService eduCourseService;

    //条件查询
    @Override
    public void getTeacherPage(Page<EduTeacher> pageTeacher, TeacherQuery teacherQuery) {

        //TODO 只是为了测试使用,运行时候这行代码去掉

//        try {
//
//            int i = 1 / 0;
//
//        } catch (Exception e){
//            log.error(e.getMessage());
//            throw new EduException(520,"分母为零了,亲~");
//        }


        //1.判断条件值是否为空,条件值不为空,封装条件
        //获取出来条件值
        String name = teacherQuery.getName();

        String level = teacherQuery.getLevel();

        String begin = teacherQuery.getBegin();

        String end = teacherQuery.getEnd();


        //判断条件是否为空，不为空，封装条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //name 不为空，封装条件

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name",name);
        }

        if (!StringUtils.isEmpty(level)) {
            wrapper.like("level",level);
        }

        if (!StringUtils.isEmpty(begin)) {
            wrapper.gt("gmt_create",begin);
        }

        //gmt_create > ? and gmt_create < ?

        if (!StringUtils.isEmpty(end)) {
            wrapper.gt("gmt_create",end);
        }
        //2.调用方法传递对象

        baseMapper.selectPage(pageTeacher,wrapper);
    }

    //删除讲师
    @Override
    public boolean removeTeacherById(String id) {

        //调用mapper里边的方法
        int i = baseMapper.deleteById(id);
        return i>0;
    }


    //讲师列表分页   前台系统
    @Override
    public Map<String, Object> findTeacherFrontPage(Page<EduTeacher> teacherPage) {

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //根据字段排序 当条件传入
        wrapper.orderByAsc("sort");

        //调用方法分页查询 把分页所有数据封装到 teacherPage 对象里面
        baseMapper.selectPage(teacherPage,wrapper);

        //从 teacherPage 对象中获取分页数据封装到 map 集合
        List<EduTeacher> records = teacherPage.getRecords();

        long pages = teacherPage.getPages(); //总页数

        long size = teacherPage.getSize(); //每页记录数

        long total = teacherPage.getTotal(); //总记录数

        long current = teacherPage.getCurrent(); //当前页

        boolean hasNext = teacherPage.hasNext(); //是否有下一页

        boolean hasPrevious = teacherPage.hasPrevious(); //是否有上一页

        //放到 map 中
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    //2.根据讲师 id 查询讲师所讲课程
    @Override
    public List<EduCourse> getCourseByTeacherId(String id) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        wrapper.eq("status","Normal");

        wrapper.eq("teacher_id",id);

        List<EduCourse> list = eduCourseService.list(wrapper);

        return list;
    }
}
