package com.wil.service;

import com.github.pagehelper.PageInfo;
import com.wil.entity.Kaola;
import com.wil.entity.KaolaType;

import java.util.List;
import java.util.Map;

/**
 * Created by wil on 2017/11/5.
 */
public interface KaolaService {

    List<KaolaType> findAllType();
    List<String> findAllPlace();
    PageInfo<Kaola> findByPageNo(Map<String, Object> queryParam, Integer pageNo);

    void save(Kaola kaola);
    void edit(Kaola kaola);
    void delete(Integer id);

    Kaola findById(Integer id);
}
