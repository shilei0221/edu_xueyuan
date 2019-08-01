package cn.eduonline.eduservice.service.impl;

import cn.eduonline.common.handler.EduException;
import cn.eduonline.eduservice.entity.EduSubject;
import cn.eduonline.eduservice.entity.subjectdto.SubjectOne;
import cn.eduonline.eduservice.entity.subjectdto.SubjectTwo;
import cn.eduonline.eduservice.mapper.EduSubjectMapper;
import cn.eduonline.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Alei
 * @since 2019-07-14
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    //导入课程分类使用poi实现
    @Override
    public List<String> importData(MultipartFile file) {
        try {
            //1.创建workbook，传递文件输入流
            InputStream inputStream = file.getInputStream();

            //只支持 Excel3 的格式
//            Workbook workbook = new HSSFWorkbook(inputStream);
            //只支持 Excel7 的格式
//            Workbook workbook = new XSSFWorkbook(inputStream);

            //支持 Excel3 与 Excel7 两种格式
            Workbook workbook = WorkbookFactory.create(inputStream);

            //2.获取sheet
            Sheet sheet = workbook.getSheetAt(0);

            //创建 list 集合用于存储错误信息
            List<String> msg = new ArrayList<>();

            //3.获取 row
            //sheet.getRow(0);
            //因为有多少行不确定，所有行不能通过下标获取，需要遍历获取
            //获取表格最后一行数据下标方法
//            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();//获取物理行的行数
            int lastRowNum = sheet.getLastRowNum();

            //遍历从第二行开始，因为第一行是表头，不需要添加到数据库中
            for (int i = 1; i <= lastRowNum; i++) {
                //获取每一行
                Row row = sheet.getRow(i);

                //4.获取cell
                //excel 列必须是固定的，可以只根据下标获取
                //第一列  一级分类
                Cell cellOne = row.getCell(0);

                //判断列是否为空
                if (cellOne == null) {

                    msg.add("第"+(i+1)+"行,第一列数据为空");

                    //跳出当前循环
                    continue;
                }

                //5.获取cell中的值
                String oneCellValue = cellOne.getStringCellValue();

                //判断值是否为空
                if (StringUtils.isEmpty(oneCellValue)) {

                    msg.add("第"+(i+1)+"行,第一列数据为空");

                    //跳出当前循环
                    continue;

                }

                //6.把一级分类值oneCellValue添加到数据库中
                //添加之前进行判断，如果数据库已经存在相同名称的一级分类，不进行添加，否则进行添加
                EduSubject existOneSubject = this.existOneSubjectName(oneCellValue);

                //定义变量为了存储一级分类id
                String pid = null ;

                if (existOneSubject == null ){ //数据库表没有一级分类

                    //添加
                    EduSubject subject1 = new EduSubject();
                    subject1.setTitle(oneCellValue);

                    //设置parentId是0
                    subject1.setParentId("0");

                    //调用方法添加
                    baseMapper.insert(subject1);

                    //获取添加之后的一级分类id
                    pid = subject1.getId();

                } else {
                    //数据库表存在相同一级分类，不进行添加
                    //如果表已近存在一级分类，把查询出来的一级对象，从里面获取id值
                    pid = existOneSubject.getId();

                }

                //第二列  二级分类
                Cell cellTwo = row.getCell(1);

                if (cellTwo == null) {

                    msg.add("第"+(i+1)+"行,第二列数据为空");

                    continue;
                }

                //5.获取cell中的值
                String twoCellValue = cellTwo.getStringCellValue();

                if (cellTwo == null) {

                    msg.add("第"+(i+1)+"行,第二列数据为空");

                    continue;
                }

                //判断数据库是否存在二级分类
                EduSubject existTwoSubject = this.existTwoSubjectName(twoCellValue, pid);

                if (existTwoSubject == null ){
                    //添加
                    EduSubject subject2 = new EduSubject();

                    subject2.setTitle(twoCellValue);

                    subject2.setParentId(pid);

                    //调用方法添加
                    baseMapper.insert(subject2);
                }
            }
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //返回所有的分类数据，按照要求的固定的格式
    @Override
    public List<SubjectOne> getAllSubjectData() {

        //1.查询所有的一级分类
        //parent_id = 0 为一级分类
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();

        //eq 代表等于
        oneWrapper.eq("parent_id","0");

        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);
        
       

        //2.查询所有的二级分类
        //parent_id != 0 为二级分类
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();

        //ne 代表不等于
        twoWrapper.ne("parent_id","0");

        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);


        //创建 list 集合，用于存储最终数据
        List<SubjectOne> finalSubjectData = new ArrayList<>();

        //构建一级分类数据
        //遍历查询出来的所有一级分类集合 list    oneSubjectList
        for (int i = 0; i < oneSubjectList.size(); i++) {
            //获取集合中每个 eduSubject 对象
            EduSubject eduSubject = oneSubjectList.get(i);

            //把每个eduSubject的值复制到 dto对象SubjectOne里面
            SubjectOne subjectOne = new SubjectOne();
            BeanUtils.copyProperties(eduSubject,subjectOne);

            //把最终复制之后的subjectOne 对象放到外面我们定义的最终集合里面 finalSubjectData
            finalSubjectData.add(subjectOne);


            /**
             * 伪代码实现功能:
             *      定义List<二级分类dto对象>
             *      嵌套for循环，遍历所有二级分类集合twoSubjectList
             *          twoSubject = twoSubjectList 得到每个二级分类
             *       if(当前二级分类parentId == 一级分类id) {
             *          做对象对拷复制
             *          房费外面定义list 集合中
             *       }
             *      一级分类对象.setChildren(List二级分类dto对象)
             */
            //定义二级分类list集合
            List<SubjectTwo> twoSubjectData = new ArrayList<>();

            //遍历所有的二级分类
            for (int m = 0; m < twoSubjectList.size(); m++) {
                //得到每个二级分类
                EduSubject eduSubjectTwo = twoSubjectList.get(m);

                //当前二级分类parentId == 一级分类id
                if (eduSubjectTwo.getParentId().equals(eduSubject.getId())) {
                    //做对象对拷 复制
                    SubjectTwo subjectTwo = new SubjectTwo();
                    BeanUtils.copyProperties(eduSubjectTwo,subjectTwo);

                    //放到外面定义存储二级分类list集合中
                    twoSubjectData.add(subjectTwo);
                }

            }
            // 一级分类对象.setChildren(List二级分类dto对象)
            subjectOne.setChildren(twoSubjectData);

        }
        return finalSubjectData;
    }

    //删除分类方法
    @Override
    public boolean deleteSubjectId(String id) {
        //1.判断当前分类下面是否有子分类
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();

        wrapper.eq("parent_id",id);

        //查询分类里面是否有子分类，如果返回值大于0则有子分类，否则没有子分类
        Integer count = baseMapper.selectCount(wrapper);

        if (count > 0) { //有子分类
            //不进行删除
            throw new EduException(20001,"当前分类有子分类,不能删除");
//            return false;
        } else { //没有子分类
            //进行删除
            int result = baseMapper.deleteById(id);

            return result > 0;
        }
    }

    //添加一级分类
    @Override
    public boolean saveEduSubject(EduSubject eduSubject) {

        EduSubject subject = this.existOneSubjectName(eduSubject.getTitle());

        if (subject == null) {

            eduSubject.setParentId("0");//表示一级分类

            int insert = baseMapper.insert(eduSubject);

            //父类中写了保存的方法  底层也是调用mapper的增加方法进行添加
//            super.save(eduSubject);



            return insert>0;
        }

        return false;
    }

    //添加二级分类
    @Override
    public boolean saveEduSubjectTwo(EduSubject eduSubject) {

        //判断是否存在
        EduSubject subject = this.existTwoSubjectName(eduSubject.getTitle(), eduSubject.getParentId());

        if (subject == null) {
            int insert = baseMapper.insert(eduSubject);

            return insert > 0;
        }
        return false;
    }


    //判断表里面是否相同名称一级分类
    //因为表里边的一级分类是有title和id区分，所有根据这两个条件查询返回一个对象
    private EduSubject existOneSubjectName(String name) {

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();

        wrapper.eq("title",name);

        wrapper.eq("parent_id","0");

        EduSubject eduSubject = baseMapper.selectOne(wrapper);

        return eduSubject;
    }

    //判断是否存在二级分类
    private EduSubject existTwoSubjectName(String name, String pid) {

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();

        wrapper.eq("title",name);

        wrapper.eq("parent_id",pid);

        EduSubject subject = baseMapper.selectOne(wrapper);

        return subject;

    }
}
