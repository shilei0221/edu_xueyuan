package cn.eduonline.edustatis.controller;


import cn.eduonline.common.R;
import cn.eduonline.edustatis.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Alei
 * @since 2019-07-27
 */
@RestController
@RequestMapping("/edustastics/countData")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService dailyService;

    //根据时间范围查询统计数据
    @GetMapping("/showCharts/{type}/{begin}/{end}")
    public R showCharts(@PathVariable String type,
                        @PathVariable String begin,
                        @PathVariable String end){

        Map<String,Object> map = dailyService.getChartsData(type,begin,end);

        return R.ok().data(map);

    }

    //根据某一天获取注册人数 添加到数据库中
    @GetMapping("createData/{day}")
    public R createData(@PathVariable String day) {

        dailyService.createStatisData(day);

        return R.ok();
    }
}

