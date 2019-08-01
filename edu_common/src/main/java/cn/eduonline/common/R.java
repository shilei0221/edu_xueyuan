package cn.eduonline.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alei
 * @create 2019-07-09 23:12
 */
// 定义统一结果  返回结果为this  为 链式编程 R.ok().massage().error()/可以一直使用当前对象点下去调用方法

@Data
public class R {

    //定义属性 格式名称
    private  Boolean success;

    private Integer code;

    private String message;

    private Map<String,Object> data = new HashMap<>();

    private R() {}

    //操作成功的方法
    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }



    //操作失败的方法
    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }


    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
