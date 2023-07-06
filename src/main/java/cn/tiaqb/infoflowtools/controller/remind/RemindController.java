package cn.tiaqb.infoflowtools.controller.remind;

import cn.tiaqb.infoflowtools.common.Response;
import cn.tiaqb.infoflowtools.entity.BaseRequest;
import cn.tiaqb.infoflowtools.entity.Message;
import cn.tiaqb.infoflowtools.entity.Remind;
import cn.tiaqb.infoflowtools.entity.UserMessageEntity;
import cn.tiaqb.infoflowtools.service.remind.RemindService;
import cn.tiaqb.infoflowtools.utils.Assert;
import cn.tiaqb.infoflowtools.utils.DataUtil;
import cn.tiaqb.infoflowtools.utils.MessageUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author tianqingbo_dxm
 * @date 2023/4/21 4:18 PM
 * @since 1.0
 */
@Controller
@RequestMapping("/infoflow/tools/remind")
@Slf4j
public class RemindController {

    @Autowired
    private RemindService remindService;
    @Value("${remind.group.token}")
    private String accessToken;
    @Value("${remind.group.aesKey}")
    private String aesKey;
    @Value("${remind.group.url}")
    private String url;


    @PostMapping
    @ResponseBody
    public Response<Boolean> remind(@RequestBody Remind remind) {
        Assert.isEmpty(remind);
        Assert.isEmpty(remind.getTimer(), remind.getContent(), remind.getUid(), remind.getGroupId());
        String timer = remind.getTimer();
        if (!DataUtil.webTimeReg(timer)) {
            return Response.checkError();
        }
        // 设置机器人地址
        remind.setRobotUrl(url);
        Message message = MessageUtils.buildMessage(remind);
        boolean ok = remindService.remind(message, timer);
        return Response.ok(ok);
    }

    /**
     * 消息处理接口
     *
     * @param baseRequest         基础认证参数
     * @param httpServletRequest  httpServletRequest
     * @param httpServletResponse httpServletResponse
     */
    @PostMapping("/receive")
    public void remind(BaseRequest baseRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String echoStr = baseRequest.getEchostr();
        if (echoStr != null && echoStr.length() != 0) {
            // 验证合法性
            if (checkSignature(baseRequest)) {
                httpServletResponse.getWriter().print(echoStr);
            }
        } else {
            // 获取消息
            Charset charset = StandardCharsets.UTF_8;
            byte[] msgBase64 = Base64.decodeBase64(IOUtils.toString(httpServletRequest.getInputStream(), charset));
            SecretKeySpec skeySpec = new SecretKeySpec(Base64.decodeBase64(aesKey), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] decrypted = cipher.doFinal(msgBase64);
            // 通过AES解密后得到回调消息数据
            String msgJsonStr = new String(decrypted, charset);
            log.info("接收到用户发送的消息, msg = {}", msgJsonStr);
            UserMessageEntity userMessageEntity = JSONObject.parseObject(msgJsonStr, UserMessageEntity.class);
            // 设置机器人地址
            userMessageEntity.setRobotUrl(url);
            remindService.apply(userMessageEntity);
        }
    }

    private boolean checkSignature(BaseRequest request) {
        String signature = request.getSignature();
        String timestamp = request.getTimestamp();
        String rn = request.getRn();
        String str = DigestUtils.md5Hex((rn + timestamp + accessToken).getBytes(StandardCharsets.UTF_8));
        return signature.equals(str);
    }
}
