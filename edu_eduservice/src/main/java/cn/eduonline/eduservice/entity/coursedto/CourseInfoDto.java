package cn.eduonline.eduservice.entity.coursedto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Alei
 * @create 2019-07-22 22:30
 */
@Data
public class CourseInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
