package cn.eduonline.eduvod.service.impl;

import cn.eduonline.eduvod.service.VodService;
import cn.eduonline.eduvod.util.AliyunVodUtils;
import cn.eduonline.eduvod.util.ConstantPropertiesUtil;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alei
 * @create 2019-07-25 21:47
 */
@Service
public class VodServiceImpl implements VodService {

    //上传视频
    @Override
    public String uploadAliyunVideo(MultipartFile file) {

        try {
            String fileName = file.getOriginalFilename();

            String title = fileName.substring(0,fileName.lastIndexOf("."));

            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request =
                    new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                            title, fileName, inputStream);

            /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
            //request.setShowWaterMark(true);
            /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
            //request.setCallback("http://callback.sample.com");
            /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
            //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
            /* 视频分类ID(可选) */
            //request.setCateId(0);
            /* 视频标签,多个用逗号分隔(可选) */
            //request.setTags("标签1,标签2");
            /* 视频描述(可选) */
            //request.setDescription("视频描述");
            /* 封面图片(可选) */
            //request.setCoverURL("http://cover.sample.com/sample.jpg");
            /* 模板组ID(可选) */
            //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
            /* 工作流ID(可选) */
            //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
            /* 存储区域(可选) */
            //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
            /* 开启默认上传进度回调 */
            // request.setPrintProgress(true);
            /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
            // request.setProgressListener(new PutObjectProgressListener());
            /* 设置应用ID*/
            //request.setAppId("app-1000000");
            /* 点播服务接入点 */
            //request.setApiRegionId("cn-shanghai");
            /* ECS部署区域*/
            // request.setEcsRegionId("cn-shanghai");

            UploadVideoImpl uploader = new UploadVideoImpl();

            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = "";

            if (response.isSuccess()) {

                videoId = response.getVideoId();

            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因

                videoId = response.getVideoId();

            }

            return videoId;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        }
    }

    //删除阿里云视频
    @Override
    public void deleteAliyunVideoById(String videoId) {

        try {

            //初始化对象
            DefaultAcsClient defaultAcsClient = AliyunVodUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                        ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //把要删除视频的id 设置到request 对象中
            request.setVideoIds(videoId);

            //调用方法实现删除
            DeleteVideoResponse response = defaultAcsClient.getAcsResponse(request);

            System.out.println("RequestId = " + response.getRequestId() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据多个视频id删除多个视频的方法
    @Override
    public void removeVideoMore(List<String> videoList) {

        try {

            //初始化对象
            DefaultAcsClient client = AliyunVodUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            //创建删除视频 request 对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //把要删除的视频 id 设置到request中
            //最大支持删除20 个,1,2,3...
            String join = StringUtils.join(videoList.toArray(), ",");

            request.setVideoIds(join);

            //调用方法实现删除
            DeleteVideoResponse acsResponse = client.getAcsResponse(request);

            System.out.println("RequestId = " + acsResponse.getRequestId() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //根据视频id获取视频播放凭证
    @Override
    public String getPlayAuthVideo(String vid) {

        try {

            //1.初始化操作
            DefaultAcsClient client = AliyunVodUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            //创建request对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            request.setVideoId(vid);

            //调用方法返回request对象
            GetVideoPlayAuthResponse acsResponse = client.getAcsResponse(request);

            //通过response获取播放凭证
            String playAuth = acsResponse.getPlayAuth();

            return playAuth;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }

    }


    public static void main(String[] args) {

        List<String> list = new ArrayList<>();

        list.add("11");
        list.add("22");
        list.add("33");

        System.out.println(StringUtils.join(list.toArray(), ","));


    }
}
