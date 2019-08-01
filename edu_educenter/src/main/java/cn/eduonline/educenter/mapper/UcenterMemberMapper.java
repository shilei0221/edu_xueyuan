package cn.eduonline.educenter.mapper;

import cn.eduonline.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Alei
 * @since 2019-07-27
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    //统计某一天注册的人数
    Integer countRegister(String day);
}
