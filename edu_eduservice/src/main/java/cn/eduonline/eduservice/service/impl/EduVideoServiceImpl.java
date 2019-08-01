package cn.eduonline.eduservice.service.impl;

import cn.eduonline.eduservice.client.VodClient;
import cn.eduonline.eduservice.entity.EduVideo;
import cn.eduonline.eduservice.mapper.EduVideoMapper;
import cn.eduonline.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Alei
 * @since 2019-07-21
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    //删除小节，同时删除小节中的视频
    @Override
    public boolean removeVideoInfo(String id) {

        //根据小节id查询小节里面视频id
        EduVideo eduVideo = baseMapper.selectById(id);

        String videoSourceId = eduVideo.getVideoSourceId();

        //判断小节里面有没有视频 进行删除
        if (!StringUtils.isEmpty(videoSourceId)) {

            vodClient.removeAliyunVideoId(videoSourceId);
        }

        int result = baseMapper.deleteById(id);

        return result > 0;
    }

//  根据课程 id 删除 小节和视频
    @Override
    public void removeVideoByCourseId(String courseId) {

        //删除课程里面所有视频
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();

        wrapperVideo.eq("course_id",courseId);

        wrapperVideo.select("video_source_id");

        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);

        //List<EduVideo>转换成List<String>
        List<String> vids = new ArrayList<>();

        for (int i = 0; i < eduVideoList.size(); i++) {

            EduVideo eduVideo = eduVideoList.get(i);

            String videoSourceId = eduVideo.getVideoSourceId();

            //判读如果id不为空 进行添加
            if (!StringUtils.isEmpty(videoSourceId)) {

                vids.add(videoSourceId);
            }
        }

        //判读 如果集合中有id数据 进行删除
        if (vids.size() > 0) {

            vodClient.deleteMoreVideo(vids);
        }

//        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
//
//        wrapper.eq("course_id",courseId);

//        baseMapper.delete(wrapper);
        baseMapper.delete(wrapperVideo);
    }
}
