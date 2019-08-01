package cn.eduonline.eduservice.entity.chaptervideodto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alei
 * @create 2019-07-21 13:57
 *
 * 章节 dto
 */
@Data
public class ChapterDto {

    private String id;
    private String title;

    //章节里边的很多小节
    private List<VideoDto> children = new ArrayList<>();

}
