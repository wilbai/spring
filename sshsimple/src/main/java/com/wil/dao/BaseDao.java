package com.wil.dao;

import com.wil.util.Page;
import com.wil.util.RequestQuery;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by wil on 2017/11/30.
 * T:继承类的entity类型
 * PK:实体类的id类型（Integer,String[uuid]）
 */
public class BaseDao<T, PK extends Serializable> {

    @Autowired
    private SessionFactory sessionFactory;

    private Class<T> entityClazz;

    public BaseDao() {
        //通过反射机制获取子类服务的实体类的Class类型
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void saveOrUpdate(T entity) {
        getSession().saveOrUpdate(entity);
    }

    public void update(T entity) {
        getSession().update(entity);
    }

    public T findById(PK id) {
        return (T) getSession().get(entityClazz, id);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    public List<T> findAll() {
        Criteria criteria = getSession().createCriteria(entityClazz);
        return criteria.list();
    }

    public List<T> findByPage(Integer start, Integer size) {
        Criteria criteria = getSession().createCriteria(entityClazz);
        criteria.setFirstResult(start);
        criteria.setMaxResults(size);
        //criteria.addOrder(Order.desc("id"));
        return criteria.list();
    }


    public List<T> findByRequestQueryList(List<RequestQuery> queryList) {
        Criteria criteria = getSession().createCriteria(entityClazz);
        buildCriteriaCondition(queryList, criteria);
        return criteria.list();
    }

    private Criterion createCriterion(String paramName, String type, Object value) {
        if(paramName.contains("_or_")) {
            String[] params = paramName.split("_or_");
            Disjunction disjunction = Restrictions.disjunction();
            for(String name : params) {
                disjunction.add(getCriterionByEqualType(name, type, value));
            }
            return disjunction;
        } else {
            return getCriterionByEqualType(paramName, type, value);
        }
    }

    private Criterion getCriterionByEqualType(String paramName, String type, Object value) {
        if("eq".equalsIgnoreCase(type)) {
            return Restrictions.eq(paramName, value);
        } else if("like".equalsIgnoreCase(type)) {
            return Restrictions.like(paramName, value.toString(), MatchMode.ANYWHERE);
        } else if("gt".equalsIgnoreCase(type)) {
            return Restrictions.gt(paramName, value);
        } else if("ge".equalsIgnoreCase(type)) {
            return Restrictions.ge(paramName, value);
        } else if("lt".equalsIgnoreCase(type)) {
            return Restrictions.lt(paramName, value);
        } else if("le".equalsIgnoreCase(type)) {
            return Restrictions.le(paramName, value);
        }
        return null;
    }

    /**
     * 计算待条件的数据数量
     * @param queryList
     * @return
     */
    public Long count(List<RequestQuery> queryList) {
        Criteria criteria = getSession().createCriteria(entityClazz);
        buildCriteriaCondition(queryList, criteria);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    private void buildCriteriaCondition(List<RequestQuery> queryList, Criteria criteria) {
        for(RequestQuery requestQuery : queryList) {
            String paramName = requestQuery.getParameterName();
            String type = requestQuery.getEqualType();
            Object value = requestQuery.getValue();
            criteria.add(createCriterion(paramName, type, value));
        }
    }

    /**
     * 查询条件与分页整合
     * @param queryList 查询条件
     * @param pageNo 页码
     * @return
     */
    public Page<T> findByRequestQueryListAndPageNo(List<RequestQuery> queryList, Integer pageNo) {
        //1. 计算总数量
        Long count = count(queryList);
        //2. 计算总页数
        Page<T> page = new Page<>(count.intValue(), 15, pageNo);
        //3.计算起始页号

        Criteria criteria = getSession().createCriteria(entityClazz);
        buildCriteriaCondition(queryList, criteria);
        criteria.setFirstResult(page.getStart());
        criteria.setMaxResults(15);

        List<T> resultList = criteria.list();
        page.setItems(resultList);

        return page;
    }
}
