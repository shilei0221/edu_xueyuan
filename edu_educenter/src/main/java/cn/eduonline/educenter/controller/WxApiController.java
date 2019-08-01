package cn.eduonline.educenter.controller;

import cn.eduonline.educenter.entity.UcenterMember;
import cn.eduonline.educenter.service.UcenterMemberService;
import cn.eduonline.educenter.utils.ConstantPropertiesUtil;
import cn.eduonline.educenter.utils.HttpClientUtils;
import cn.eduonline.educenter.utils.JwtUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author Alei
 * @create 2019-07-30 22:28
 */
@Controller  //我们是为了重定向跳转页面 不是为了返回json数据 所以使用 RestController不可以重定向
@RequestMapping("/api/ucenter/wx") //TODO 为了多人测试 所有必须和老师一致
@CrossOrigin
public class WxApiController {


    @Autowired
    private UcenterMemberService memberService;

    //回调方法
    //获取微信扫描人的信息
    @GetMapping("callback")
    public String callback(String code, String state, HttpSession session) {

        //1.获取临时票据  在回调方法中创建参数  参数名必须是 code
        //code 就是临时票据


        //2.拿着临时票据 code 值  请求腾讯提供的固定的地址
        //向认证服务器发送请求换取两个值  access_token 访问凭证 和 openId 微信唯一标识
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        //3.拼接地址中的参数
        String accessTokenURL = String.format(baseAccessTokenUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID, ConstantPropertiesUtil.WX_OPEN_APP_SECRET, code);

        //使用 httpClient 请求拼接之后的地址  获取 AccessToken 凭证和openid 微信唯一标识
        //提供 httpclient 工具类 直接使用就可以了

        String result = null;

        try {

            result = HttpClientUtils.get(accessTokenURL);
            System.out.println("result : " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //从返回 result 里面获取到 AccessToken 凭证和 openId 微信唯一标识
        //result 是 json 数据格式  使用 json 解析工具 Gson
        Gson gson = new Gson();

        //把 json 数据转换 map 集合
        HashMap map = gson.fromJson(result, HashMap.class);

        //从map集合获取数据
        //access_token 微信访问凭证  openid  微信唯一标识id  这两个值是微信提供的固定的  不可以更改
        String accessToken = (String) map.get("access_token");

        String openId = (String) map.get("openid");

        System.out.println("accessToken : " + accessToken);
        System.out.println("openId : " + openId);

        //判断 用户表里面是否存在相同的 openId 如果没有相同的 openId 调用地址获取扫描人信息 添加到数据库中
        UcenterMember member = memberService.getOpenId(openId);

        if (member == null) { //如果没有相同的openId 进添加到数据库

            //得到授权临时票据code
            System.out.println("code = " + code);
            System.out.println("state = " + state);

            //3.拿着获取到的 AccessToken 访问凭证和 openId 微信唯一标识再去访问一个固定地址,获取扫描人的信息
            //访问微信的资源服务器  获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";

            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openId);

            //使用 HTTPClient 请求地址
            String userInfo = null;

            try {
                userInfo = HttpClientUtils.get(userInfoUrl);

                System.out.println("userInfo : " + userInfo);
            } catch (Exception e) {

            }

            //从返回数据中获取扫描人信息
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);

            // nickname 微信扫描人名称  headimgurl 微信头像  这两个值都是微信提供的固定的 不可以更改
            String nickName = (String) userInfoMap.get("nickname");

            String headImgUrl = (String) userInfoMap.get("headimgurl");

            System.out.println("nickName : " + nickName);
            System.out.println("headimgurl : " + headImgUrl);

            member = new UcenterMember();

            member.setAvatar(headImgUrl);
            member.setNickname(nickName);
            member.setOpenid(openId);

            //添加到数据库中
            memberService.addUser(member);
        }

        //根据Jwt工具类生成 token值
        String token = JwtUtils.geneJsonWebToken(member);

        return "redirect:http://localhost:3000?token=" + token;
    }


    //生成登录的二维码
    //重定向到路径里面 所有返回值写String类型
    @GetMapping("login")
    public String createWxLogin() {

        //1.拼接地址后面参数
        // %s 就是 ？ 问号 占位符  拼接参数用
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //2.获取回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址

        //3.对获取回调地址进行 url 编码 为了防止地址里面有特殊符号
        try {

            //encode 两个参数  第一个参数是要进行编码的字符串  第二个参数是编码的格式
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");

        } catch (Exception e) {
            e.printStackTrace();
        }

        //4.设置 state
        //内网穿透前置域名
        String state = "alei";

        //5.最终参数的拼接
        String wxUrl = String.format(baseUrl, ConstantPropertiesUtil.WX_OPEN_APP_ID, redirectUrl, state);

        //6.重定向到拼接之后地址里面
        return "redirect:" + wxUrl;
    }
}
