package com.wil.service;

import com.wil.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wil on 2017/10/30.
 */

public interface UserService {
    void save();

    Long count();

    void insert(User user);
}
