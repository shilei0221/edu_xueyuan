package cn.eduonline.eduservice.entity.subjectdto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alei
 * @create 2019-07-14 22:56
 */
@Data
public class SubjectOne {

    private String id;

    private String title; //一级分类名称

    //一级分类的所有二级分类、
    private List<SubjectTwo> children = new ArrayList<>();
}
