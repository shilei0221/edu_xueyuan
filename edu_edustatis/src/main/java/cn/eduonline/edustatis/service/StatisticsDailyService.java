package cn.eduonline.edustatis.service;

import cn.eduonline.edustatis.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Alei
 * @since 2019-07-27
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //根据某一天获取注册人数 添加到数据库中
    void createStatisData(String day);

    //根据时间范围查询统计数据
    Map<String,Object> getChartsData(String type, String begin, String end);
}
