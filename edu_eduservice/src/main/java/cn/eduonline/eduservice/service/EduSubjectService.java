package cn.eduonline.eduservice.service;

import cn.eduonline.eduservice.entity.EduSubject;
import cn.eduonline.eduservice.entity.subjectdto.SubjectOne;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Alei
 * @since 2019-07-14
 */
public interface EduSubjectService extends IService<EduSubject> {

    List<String>  importData(MultipartFile file);

    List<SubjectOne> getAllSubjectData();

    //删除分类方法
    boolean deleteSubjectId(String id);

    boolean saveEduSubject(EduSubject eduSubject);

    boolean saveEduSubjectTwo(EduSubject eduSubject);
}
