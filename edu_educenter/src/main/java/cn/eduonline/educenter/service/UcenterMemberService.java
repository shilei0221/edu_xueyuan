package cn.eduonline.educenter.service;

import cn.eduonline.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Alei
 * @since 2019-07-27
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //统计某一天注册的人数
    Integer countNumRegister(String day);

    //根据 openId 查询
    UcenterMember getOpenId(String openId);

    //添加扫描人的信息到用户表中
    void addUser(UcenterMember member);
}
