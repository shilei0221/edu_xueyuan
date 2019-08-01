package cn.eduonline.eduservice.controller;


import cn.eduonline.common.R;
import cn.eduonline.eduservice.entity.EduChapter;
import cn.eduonline.eduservice.entity.chaptervideodto.ChapterDto;
import cn.eduonline.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Alei
 * @since 2019-07-21
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //1.返回当前课程里面的章节和小节的分装数据
    @GetMapping("getAllChapterVideo/{courseId}")
    public R getAllChapterVideo(@PathVariable String courseId) {

        List<ChapterDto> list = eduChapterService.getAllVideoChapter(courseId);

        return R.ok().data("items",list);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {

        boolean save = eduChapterService.save(eduChapter);

        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据章节 id 查询信息
    @GetMapping("{chapterId}")
    public R getChapterId(@PathVariable String chapterId) {

        EduChapter eduChapter = eduChapterService.getById(chapterId);

        return R.ok().data("eduChapter",eduChapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {

        boolean result = eduChapterService.updateById(eduChapter);

        if (result) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //删除章节
    @DeleteMapping("{chapterId}")
    public R deleteChapterId(@PathVariable String chapterId) {

        boolean flag = eduChapterService.removeChapterId(chapterId);

        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

