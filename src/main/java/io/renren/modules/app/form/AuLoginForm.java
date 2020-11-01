package io.renren.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "授权登录表单")
public class AuLoginForm {
    @ApiModelProperty(value = "授权Token")
    @NotBlank(message="授权Token不能为空")
    private String auToken;

    @ApiModelProperty(value = "登录类型")
    @NotBlank(message="登录类型只能为Alipay,Wechat,Weibo,QQ,OneKey")
    private String type;

    @ApiModelProperty(value = "手机号码")
    private String mobile;
}
