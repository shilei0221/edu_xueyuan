package cn.eduonline.common.handler;

import cn.eduonline.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Alei
 * @create 2019-07-10 22:44
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    //全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R  error(Exception e) {
        e.printStackTrace();
        return R.error().message("亲爱的,系统出错了~");
    }

    //特殊异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("0 不能被整除");
    }

    //自定义异常处理
    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R error(EduException e) {
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}
