package com.wil.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * Created by wil on 2017/11/24.
 */
public class MyRealm implements Realm {
    @Override
    public String getName() {
        return "myRealm";
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String userName = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());
        if(!"seber".equals(userName)) {
            throw new UnknownAccountException("帐号不存在");
        }
        if(!"123".equals(password)) {
            throw new IncorrectCredentialsException("帐号或密码错误");
        }
        return new SimpleAuthenticationInfo(userName,password,getName());

    }
}
