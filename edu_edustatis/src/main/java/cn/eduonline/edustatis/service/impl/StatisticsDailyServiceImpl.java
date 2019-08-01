package cn.eduonline.edustatis.service.impl;

import cn.eduonline.common.R;
import cn.eduonline.edustatis.client.UcenterClient;
import cn.eduonline.edustatis.entity.StatisticsDaily;
import cn.eduonline.edustatis.mapper.StatisticsDailyMapper;
import cn.eduonline.edustatis.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Alei
 * @since 2019-07-27
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    //根据某一天获取注册人数 添加到数据库中
    @Override
    public void createStatisData(String day) {

        //删除添加的日期数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();

        wrapper.eq("date_calculated",day);

        baseMapper.delete(wrapper);

        R r = ucenterClient.countRegisterNumDay(day);

        Integer count = (Integer)r.getData().get("count");

        //得到某天注册人数  添加到统计分析表里
        StatisticsDaily daily = new StatisticsDaily();

        daily.setDateCalculated(day);

        daily.setRegisterNum(count);

        //课程数
        int courseNum = RandomUtils.nextInt(100, 200);

        daily.setCourseNum(courseNum);

        //登录人数
        int loginNum = RandomUtils.nextInt(100, 200);

        daily.setLoginNum(loginNum);

        int videoNum = RandomUtils.nextInt(100,200);

        daily.setVideoViewNum(videoNum);

        baseMapper.insert(daily);

    }

    //根据时间范围查询统计数据
    @Override
    public Map<String, Object> getChartsData(String type, String begin, String end) {

        //根据时间范围查询统计的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();

        wrapper.select("date_calculated",type);

        wrapper.between("date_calculated",begin,end);

        List<StatisticsDaily> statisticsDailies = baseMapper.selectList(wrapper);


        //日期 [2019-01-01,2019-02-02]
        List<String> dateList = new ArrayList<>();

        //数据[100,200]
        List<Integer> numList = new ArrayList<>();

        //遍历查询出来的集合 从集合获取数据封装到 list 集合中
        for (int i = 0; i < statisticsDailies.size(); i++) {

            //获取到每个对象
            StatisticsDaily daily = statisticsDailies.get(i);

            //得到每个对象中的日期
            String dateCalculated = daily.getDateCalculated();

            //放到list集合中
            dateList.add(dateCalculated);

            //判断从集合中获取具体数据
            switch (type) {
                case "register_num" : //注册人数
                    numList.add(daily.getRegisterNum());
                    break;
                case "login_num" : //登录人数
                    numList.add(daily.getLoginNum());
                    break;
                case "video_view_num" : //视频播放数
                    numList.add(daily.getVideoViewNum());
                    break;
                case "course_num" : //课程数
                    numList.add(daily.getCourseNum());
                    break;

                 default:
                     break;
            }
        }


        //创建map集合 用于最终数据存储
        Map<String,Object> map = new HashMap<>();

        //把封装之后的两个list 集合放到 map 中
        map.put("dateList",dateList);
        map.put("numList",numList);

        return map;
    }
}
