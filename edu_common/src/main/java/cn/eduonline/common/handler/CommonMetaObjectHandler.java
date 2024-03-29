package cn.eduonline.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * @author Alei
 * @create 2019-07-10 21:20
 */
@Component
@Slf4j
public class CommonMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {

      log.info("start insert fill ...");

      this.setFieldValByName("gmtCreate",new Date(),metaObject);

      this.setFieldValByName("gmtModified",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        log.info("start update fill ...");

        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}
