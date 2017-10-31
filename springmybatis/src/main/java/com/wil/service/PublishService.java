package com.wil.service;

import com.wil.entity.Publish;

/**
 * Created by wil on 2017/10/31.
 */
public interface PublishService {

    int save(Publish publish);
    Publish findById(Integer id);

}
