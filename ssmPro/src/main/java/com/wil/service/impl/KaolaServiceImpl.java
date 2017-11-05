package com.wil.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wil.entity.Kaola;
import com.wil.entity.KaolaType;
import com.wil.entity.KaolaTypeExample;
import com.wil.mapper.KaolaMapper;
import com.wil.mapper.KaolaTypeMapper;
import com.wil.service.KaolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wil on 2017/11/5.
 */
@Service
public class KaolaServiceImpl implements KaolaService {

    @Autowired
    private KaolaMapper kaolaMapper;

    @Autowired
    private KaolaTypeMapper kaolaTypeMapper;

    @Override
    public List<KaolaType> findAllType() {
        return kaolaTypeMapper.selectByExample(new KaolaTypeExample());
    }

    @Override
    public List<String> findAllPlace() {
        return kaolaMapper.findAllPlace();
    }

    @Override
    public PageInfo<Kaola> findByPageNo(Map<String, Object> queryParam, Integer pageNo) {
        PageHelper.startPage(pageNo, 10);
        List<Kaola> kaolaList = kaolaMapper.findPageByParam(queryParam);
        return new PageInfo<Kaola>(kaolaList);
    }

    @Override
    public void save(Kaola kaola) {
        kaola.setCommentNum(0);
        kaolaMapper.insert(kaola);
    }

    @Override
    public void edit(Kaola kaola) {
        kaolaMapper.updateByPrimaryKeySelective(kaola);
    }

    @Override
    public void delete(Integer id) {
        kaolaMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Kaola findById(Integer id) {
        Kaola kaola = kaolaMapper.selectByPrimaryKey(id);
        kaola.setKaolaType(kaolaTypeMapper.selectByPrimaryKey(kaola.getTypeId()));
        return kaola;
    }
}
