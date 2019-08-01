package cn.eduonline.eduservice.entity.chaptervideodto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Alei
 * @create 2019-07-21 13:55
 *
 * 小节 dto 对象
 */
@Data
public class VideoDto {

   private String id;
   private String title;
   //视频文件名称
   private String videoOriginalName;
   private String videoSourceId;
}
