package com.xy.usercenter.service;
import java.util.Date;

import com.xy.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUserId(0L);
        user.setUserName("sxy");
        user.setUserAccount("373363602@qq.com");
        user.setAvatarUrl("");
        user.setUserPassword("freedom0915");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        boolean result = userService.save(user);
        System.out.println(user.getUserId());
        Assertions.assertTrue(result);
    }

    @Test
    public void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "123@.cn";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "3333@qq.com";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "3333qq.com";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
    }
}
