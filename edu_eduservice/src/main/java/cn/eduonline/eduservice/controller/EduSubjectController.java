package cn.eduonline.eduservice.controller;


import cn.eduonline.common.R;
import cn.eduonline.eduservice.entity.EduSubject;
import cn.eduonline.eduservice.entity.subjectdto.SubjectOne;
import cn.eduonline.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Alei
 * @since 2019-07-14
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //1.读取上传的Excel里面的分类数据,添加到数据库中
    @PostMapping("/import")
    public R importSubJectData(MultipartFile file) {

       List<String> msg = eduSubjectService.importData(file);

       if (msg.size() == 0) {
           return R.ok().message("批量导入成功");
       } else {
           return R.error().message("部分数据导入失败").data("messageList",msg);
       }
    }


    //2.返回所有的分类信息(一级与二级)
    @GetMapping("/getAllSubject")
    public R getAllSubject() {

        List<SubjectOne> list = eduSubjectService.getAllSubjectData();

        return R.ok().data("items",list);
    }

    //3.分类删除的方法
    @DeleteMapping("{id}")
    public R removeSubjectId(@PathVariable String id) {

       boolean flag =  eduSubjectService.deleteSubjectId(id);

       if (flag) {
           return R.ok();
       } else {
           return R.error();
       }
    }

    //添加一级分类
    @PostMapping("addLevelOne")
    public R addOneLevel(@RequestBody EduSubject eduSubject) {
        boolean save = eduSubjectService.saveEduSubject(eduSubject);

        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    //添加二级分类
    @PostMapping("addLevelTwo")
    public R addOneLevelTwo(@RequestBody EduSubject eduSubject) {
        boolean save = eduSubjectService.saveEduSubjectTwo(eduSubject);

        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

