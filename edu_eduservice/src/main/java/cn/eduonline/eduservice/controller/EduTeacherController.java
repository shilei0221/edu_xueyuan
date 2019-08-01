package cn.eduonline.eduservice.controller;


import cn.eduonline.common.R;
import cn.eduonline.eduservice.entity.EduTeacher;
import cn.eduonline.eduservice.entity.query.TeacherQuery;
import cn.eduonline.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Alei
 * @since 2019-07-09
 */
@CrossOrigin  //该注解解决跨域问题导致的403状态码错误
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("/login")
    public R login() {
        return R.ok().data("token","admin");
    }

    @GetMapping("/info")
    public R info() {
        return R.ok().data( "roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");

    }

    @PostMapping("logout")
    public R logout() {
        return R.ok().data("token","admin");
    }



    //测试环境,查询所有讲师
//    @GetMapping
//    public List<EduTeacher> getAllTeacher() {
//        List<EduTeacher> list = eduTeacherService.list(null);
//        return list;
//    }

    /**
     * 查询所有讲师
     * @return
     */
    @GetMapping
    public R getAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    /**
     * 根据 id 逻辑删除讲师
     * @param id
     * @return
     */
    //逻辑删除讲师的方法
    @DeleteMapping("{id}")
    public R deleteById(@PathVariable("id")String id) {
        boolean b = eduTeacherService.removeTeacherById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 实现根据输入的当前页与当前页显示的信息进行分页
     * @param page 当前页
     * @param limit 显示的数量
     * @return 返回值
     */
    @GetMapping("getTeacherPage/{page}/{limit}")
    public R getTeacherPage(@PathVariable long page,
                            @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);

        eduTeacherService.page(pageTeacher,null);

        //pageTeacher 里边获取分页数据
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        //1.方式一
//        Map<String,Object> result = new HashMap<>();
//        result.put("total",total);
//        result.put("rows",records);
//        return R.ok().data(result);

        //2.方式二
        return R.ok().data("total",total).data("rows",records);
    }

    /**
     * 带条件的查询分页方法
     * @param page 当前页
     * @param limit 当前页显示的数量
     * @param teacherQuery 用于封装带条件查询的条件信息
     * @return
     */
    //@RequestBody TeacherQuery teacherQuery通过json格式传送数据，在json数据封装到对象里边
    //@RequestBody使用该注解必须使用POST请求，不能使用get请求
    @PostMapping("/getTeacherPageCondition/{page}/{limit}")
    public R getTeacherPageCondition(@PathVariable long page,
                                     @PathVariable long limit,
                                     @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> pageTeacher = new Page<>(page,limit);

        //调用service 方法
        eduTeacherService.getTeacherPage(pageTeacher,teacherQuery);

        long total = pageTeacher.getTotal();

        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total",total).data("rows",records);
    }

    /**
     * 新增讲师方法
     * @param eduTeacher
     * @return
     */
    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {

        boolean save = eduTeacherService.save(eduTeacher);

        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 根据 id 查询讲师的方法
     */
    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable String id) {

        EduTeacher byId = eduTeacherService.getById(id);

        return R.ok().data("teacher",byId);
    }

    /**
     * 根据 id 修改讲师信息
     */
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {

        boolean b = eduTeacherService.updateById(eduTeacher);

        if(b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

