package com.xy.usercenter.service;

import com.xy.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.servlet.http.HttpServletRequest;

/**
* @author Xiao
* @description 针对表【user】的数据库操作Service
* @createDate 2023-03-08 16:41:47
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册.
     *
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录.
     *
     * @param userAccount 账号
     * @param userPassword 密码
     * @param request 用户请求
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);
}
