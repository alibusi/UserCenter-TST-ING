package com.xy.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.usercenter.model.domain.User;
import com.xy.usercenter.service.UserService;
import com.xy.usercenter.mapper.UserMapper;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xy.usercenter.constant.UserConstant.LOGIN_STATE;

/**
* @author Xiao
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-03-08 16:41:47
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    private static final String SALT = "mone";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        if (userAccount.length() < 8) {
            return -1;
        }
        if (userPassword.length() < 8) {
            return -1;
        }
        String mailPattern = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.(com|cn|net)$";
        Matcher matcher = Pattern.compile(mailPattern).matcher(userAccount);
        if (!matcher.matches()) {
            return -1;
        }
        // 密码与校验密码不相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        // 账号不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        // 密码加密
        String encrpytPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encrpytPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getUserId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 8) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }

        String encrpytPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encrpytPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return null;
        }
        request.getSession().setAttribute(LOGIN_STATE, user);
        return user;
    }
}




