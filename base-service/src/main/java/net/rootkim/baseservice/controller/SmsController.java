package net.rootkim.baseservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.rootkim.baseservice.domain.dto.sms.SendSmsCodeDTO;
import net.rootkim.baseservice.service.SmsService;
import net.rootkim.core.domain.vo.ResultVO;
import net.rootkim.core.utils.HttpHeaderUtil;
import net.rootkim.core.utils.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/2
 */
@RestController
@RequestMapping("sms")
@Api(tags = "短信")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @PostMapping("sendSmsCode")
    @ApiOperation("发送短信验证码")
    public ResultVO sendSmsCode(HttpServletRequest request, @RequestAttribute(value = HttpHeaderUtil.USER_ID_KEY, required = false) String userId,
                                @Valid @RequestBody SendSmsCodeDTO sendSmsCodeDTO) throws Exception {
        smsService.sendSmsCode(sendSmsCodeDTO.getPhone(), IPUtil.getIpAddr(request), sendSmsCodeDTO.getSmsCodeType(), userId);
        return ResultVO.success();
    }
}
