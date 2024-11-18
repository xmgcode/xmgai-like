package com.mdd.front.service;

import com.mdd.common.entity.user.User;
import com.mdd.front.vo.login.LoginTokenVo;
import com.mdd.front.vo.login.WechatOauthInfo;

import javax.servlet.http.HttpSession;

/**
 * 登录服务接口类
 */
public interface ILoginService {

    /**
     * 账号注册
     *
     * @author fzr
     * @param username 账号
     * @param password 密码
     * @param terminal 终端
     */
    void register(String username, String password, Integer terminal);



    /**
     * 手机号注册
     *
     * @author fzr
     * @param username 账号
     * @param password 密码
     * @param terminal 终端
     */
    void registerMobile(String username, String password, String mobile,Integer terminal);


    /**
     * 账号登录
     *
     * @author fzr
     * @param username 账号
     * @param password 密码
     * @param terminal 终端
     * @return LoginTokenVo
     */
    LoginTokenVo accountLogin(String username, String password, Integer terminal);

    /**
     * 手机登录
     *
     * @author fzr
     * @param mobile 手机号
     * @param code 验证码
     * @param terminal 终端
     * @return LoginTokenVo
     */
    LoginTokenVo mobileLogin(String mobile, String code, Integer terminal);



    /**
     * 校验手机号是否存在
     *
     * @author fzr
     * @param mobile 手机号
     * @return LoginTokenVo
     */
    User validatePhone(String mobile);

    /**
     * 微信登录
     *
     * @author fzr
     * @param code 微信code
     * @param terminal 终端
     * @return LoginTokenVo
     */
    LoginTokenVo mnpLogin(String code, Integer terminal);

    /**
     * 公众号登录
     *
     * @author fzr
     * @param code 微信Code
     * @param terminal 终端
     * @return LoginTokenVo
     */
    LoginTokenVo officeLogin(String code, Integer terminal);

    /**
     * 公众号跳转url
     *
     * @author fzr
     * @param url 连接
     * @return String
     */
    String oaCodeUrl(String url);

    /**
     * 扫码链接
     *
     * @author fzr
     * @param session session
     * @return String
     */
    String scanCodeUrl(String url, HttpSession session);

    /**
     * 扫码登录
     *
     * @param code 编码
     * @param state 标识
     * @param terminal 终端
     * @param session 会话
     * @return LoginTokenVo
     */
    LoginTokenVo scanLogin(String code, String state, Integer terminal, HttpSession session);


    /**
     * 获取微信用户信息并注册
     * @param wechatOauthInfoData
     * @return
     */
    LoginTokenVo getWechatOauthInfo(WechatOauthInfo wechatOauthInfo);

}
