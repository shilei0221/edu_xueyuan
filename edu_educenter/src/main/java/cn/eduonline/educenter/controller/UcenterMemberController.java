package cn.eduonline.educenter.controller;


import cn.eduonline.common.R;
import cn.eduonline.educenter.entity.UcenterMember;
import cn.eduonline.educenter.service.UcenterMemberService;
import cn.eduonline.educenter.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Alei
 * @since 2019-07-27
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    //根据token值获取用户信息 进行返回
    @GetMapping("{token}")
    public R getUserInfoByToken(@PathVariable String token) {

        Claims claims = JwtUtils.checkJWT(token);

        String id = (String)claims.get("id");
        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");

        UcenterMember member = new UcenterMember();

        member.setId(id);
        member.setNickname(nickname);
        member.setAvatar(avatar);


        return R.ok().data("member",member);
    }

    //统计某一天注册的人数
    @GetMapping("countRegisterNum/{day}")
    public R countRegisterNumDay(@PathVariable String day) {

        Integer num = ucenterMemberService.countNumRegister(day);

        return R.ok().data("count",num);
    }
}

