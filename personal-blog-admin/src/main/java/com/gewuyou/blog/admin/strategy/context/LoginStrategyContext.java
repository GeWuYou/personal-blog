package com.gewuyou.blog.admin.strategy.context;

import com.gewuyou.blog.admin.strategy.interfaces.LoginStrategy;
import com.gewuyou.blog.common.dto.UserInfoDTO;
import com.gewuyou.blog.common.enums.LoginTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录策略上下文
 *
 * @author gewuyou
 * @since 2024-04-25 下午8:32:04
 */
@Service
public class LoginStrategyContext {

    private final Map<String, LoginStrategy> loginStrategyMap;

    @Autowired
    public LoginStrategyContext(
            Map<String, LoginStrategy> loginStrategyMap
    ) {
        this.loginStrategyMap = loginStrategyMap;
    }

    public UserInfoDTO executeLoginStrategy(Object data, LoginTypeEnum loginTypeEnum) {
        return loginStrategyMap.get(loginTypeEnum.getStrategy()).login(data);
    }
}
