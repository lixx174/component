package com.component.wechat.core;

import com.component.wechat.model.AccessAndMobileRequest;
import com.component.wechat.model.AccessRequest;
import com.component.wechat.model.AccessResponse;
import com.component.wechat.model.LoginRequest;
import com.component.wechat.model.LoginResponse;
import com.component.wechat.model.MobileRequest;
import com.component.wechat.model.MobileResponse;

/**
 * @author jinx
 */
public interface Client {

    /**
     * 登录获取openid
     *
     * @param request 请求参数
     * @return 登录结果
     */
    LoginResponse login(LoginRequest request);

    /**
     * 获取token
     *
     * @param request 请求参数
     * @return 微信api调用权限token
     */
    AccessResponse access(AccessRequest request);

    /**
     * 查询手机号
     *
     * @param request 请求参数
     * @return 手机号
     */
    MobileResponse queryMobile(MobileRequest request);

    /**
     * 查询手机号
     *
     * @param request 请求参数
     * @return 手机号
     */
    MobileResponse queryMobile(AccessAndMobileRequest request);
}
