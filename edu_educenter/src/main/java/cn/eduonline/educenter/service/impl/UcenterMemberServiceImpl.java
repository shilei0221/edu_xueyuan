package cn.eduonline.educenter.service.impl;

import cn.eduonline.educenter.entity.UcenterMember;
import cn.eduonline.educenter.mapper.UcenterMemberMapper;
import cn.eduonline.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Alei
 * @since 2019-07-27
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {


    //统计某一天注册的人数
    @Override
    public Integer countNumRegister(String day) {

        return baseMapper.countRegister(day);
    }

    ///根据 openId 查询
    @Override
    public UcenterMember getOpenId(String openId) {

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();

        wrapper.eq("openid",openId);

        return baseMapper.selectOne(wrapper);
    }

    //添加扫描人的信息到用户表中
    @Override
    public void addUser(UcenterMember member) {

        baseMapper.insert(member);
    }
}
