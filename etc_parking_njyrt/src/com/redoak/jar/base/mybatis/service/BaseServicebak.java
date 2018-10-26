package com.redoak.jar.base.mybatis.service;

import com.redoak.jar.base.model.Page;
import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class BaseServicebak extends SqlSessionDaoSupport
{
  protected String IBATIS_NAME_SPACE = "";

  public void setIBATIS_NAME_SPACE(String iBATIS_NAME_SPACE)
  {
    this.IBATIS_NAME_SPACE = iBATIS_NAME_SPACE;
  }

  protected String ns(String sql) {
    return this.IBATIS_NAME_SPACE + "." + sql;
  }

  public <T> List<T> findList(String statementName)
  {
    return getSqlSession().selectList(statementName);
  }

  public <T> List<T> findList(String statementName, Object paramObjectName) {
    return getSqlSession().selectList(statementName, paramObjectName);
  }

  public <T> List<T> findList(String statementName, Object paramObjectName, Page page) {
    if (page.getStartRow() < 0) {
      return getSqlSession().selectList(statementName, paramObjectName);
    }
    RowBounds bounds = new RowBounds(page.getStartRow(), page.getPageSize());
    return getSqlSession().selectList(statementName, paramObjectName, bounds);
  }

  public <T> List<T> findList(String statementName, Page page)
  {
    if (page.getStartRow() < 0) {
      return getSqlSession().selectList(statementName);
    }
    RowBounds bounds = new RowBounds(page.getStartRow(), page.getPageSize());
    return getSqlSession().selectList(statementName, bounds);
  }

  public int insert(String statementName, Object obj)
  {
    return getSqlSession().insert(statementName, obj);
  }

  public int update(String statementName, Object obj) {
    return getSqlSession().update(statementName, obj);
  }

  public int delete(String statementName, Object obj) {
    return getSqlSession().delete(statementName, obj);
  }

  public <T> T findObj(String statementName) {
    return getSqlSession().selectOne(statementName);
  }

  public <T> T findObj(String statementName, Object paramObjectName)
  {
    return getSqlSession().selectOne(statementName, paramObjectName);
  }

  public String findString(String statementName, Object paramObjectName) {
    List list = findList(statementName, paramObjectName);
    if ((list == null) || (list.size() == 0)) {
      return null;
    }
    return (String)list.get(0);
  }

  public Integer findInteger(String statementName)
  {
    Object obj = findObj(statementName);
    if (obj == null) {
      Integer a = null;
      return a;
    }
    return (Integer)obj;
  }

  public Integer findInteger(String statementName, Object paramObjectName)
  {
    Object obj = findObj(statementName, paramObjectName);
    if (obj == null) {
      Integer a = null;
      return a;
    }
    return (Integer)obj;
  }
}