package cn.eduonline.edustatis.client;

import cn.eduonline.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Alei
 * @create 2019-07-27 18:41
 */
@Component
@FeignClient("edu-educenter")
public interface UcenterClient {

    //统计某一天注册的人数
    @GetMapping("/educenter/member/countRegisterNum/{day}")
    public R countRegisterNumDay(@PathVariable("day") String day);
}
