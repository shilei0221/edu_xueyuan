package cn.eduonline.eduservice.entity.coursedto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Alei
 * @create 2019-07-22 22:30
 */
@Data
public class CourseBaseInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title; //课程标题
    private String cover; //课程封面
    private Integer lessonNum; //课时数
    private String subjectLevelOne; //一级分类
    private String subjectLevelTwo; //二级分类
    private String teacherName; //讲师名称
    private String price;//只用于显示 课程价格
    private String description; //课程描述
    private Integer buyCount; //购买人数
    private Integer viewCount; //浏览数
    private String avatar; //讲师头像
    private String intro; //讲师资历
}
