package cn.eduonline.eduservice.service.impl;

import cn.eduonline.common.handler.EduException;
import cn.eduonline.eduservice.entity.EduChapter;
import cn.eduonline.eduservice.entity.EduVideo;
import cn.eduonline.eduservice.entity.chaptervideodto.ChapterDto;
import cn.eduonline.eduservice.entity.chaptervideodto.VideoDto;
import cn.eduonline.eduservice.mapper.EduChapterMapper;
import cn.eduonline.eduservice.service.EduChapterService;
import cn.eduonline.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Alei
 * @since 2019-07-21
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterDto> getAllVideoChapter(String courseId) {
        //1.根据课程 id 查询课程所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();

        wrapperChapter.eq("course_id",courseId);

        List<EduChapter> eduChapters = baseMapper.selectList(wrapperChapter);

        //2.根据课程 id 查询课程里面所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();

        wrapperChapter.eq("course_id",courseId);

        List<EduVideo> eduVideos = eduVideoService.list(wrapperVideo);

        //3.遍历所有的章节进行数据封装
        //创建 list 集合,用于存储最终数据
        List<ChapterDto> finalDataList = new ArrayList<>();

        for (int i = 0; i < eduChapters.size(); i++) {

            //得到每个小节
            EduChapter eduChapter = eduChapters.get(i);

            //转换成 chapterDto 对象
            ChapterDto chapterDto = new ChapterDto();

            BeanUtils.copyProperties(eduChapter,chapterDto);

            //最终放入list集合中
            finalDataList.add(chapterDto);

            //定义 list 集合用来存储章节里面所有的小节
            List<VideoDto> videoDtoList = new ArrayList<>();

            //4.遍历所有的小节,判断 id 值,获取章节里面小节,进行封装
            for (int m = 0; m < eduVideos.size(); m++) {

                //获取每个小节
                EduVideo eduVideo = eduVideos.get(m);

                //判断小节里面 chapterId是否和章节里面id相同
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {

                    //获取小节 转换 videoDto对象
                    VideoDto videoDto = new VideoDto();

                    BeanUtils.copyProperties(eduVideo,videoDto);

                    //放到list集合中
                    videoDtoList.add(videoDto);
                }
            }
            //把遍历转换之后封装小节 list 集合放到章节里面
            chapterDto.setChildren(videoDtoList);
        }

        return finalDataList;
    }

    //删除章节
    @Override
    public boolean removeChapterId(String chapterId) {
        //判断章节里面是否有小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();

        wrapper.eq("chapter_id",chapterId);

        int count = eduVideoService.count(wrapper);

        if (count > 0){ //章节里面有小节
            //不进行删除
            throw new EduException(20001,"章节里面有小节,不能删除");
        } else {
            //没有小节
            int result = baseMapper.deleteById(chapterId);

            return result > 0;
        }
    }

    //根据课程 id 删除 章节
    @Override
    public void removeChapterByCourseId(String courseId) {

        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();

        wrapper.eq("course_id",courseId);

        baseMapper.delete(wrapper);
    }
}
