package cn.eduonline.eduservice.client;

import cn.eduonline.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Alei
 * @create 2019-07-26 21:03
 */
@FeignClient("edu-eduvod")
@Component
public interface VodClient {

    //删除阿里云视频
    @DeleteMapping("/eduvod/vid/{videoId}")
    public R removeAliyunVideoId(@PathVariable("videoId") String videoId);

    //删除多个视频的方法
    @DeleteMapping("/eduvod/vid/removeMoreVideo")
    public R deleteMoreVideo(@RequestParam("videoList")List videoList);

}
