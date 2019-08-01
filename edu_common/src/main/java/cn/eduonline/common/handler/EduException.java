package cn.eduonline.common.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alei
 * @create 2019-07-10 23:04
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduException extends RuntimeException{

    private Integer code; //状态码

    private String msg; //异常信息

}
