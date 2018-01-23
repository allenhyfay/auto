package com.auto.web.dao.impl;

import com.auto.web.bo.UniqueReadBo;
import com.auto.web.dao.UniqueReadDataService;
import com.jeeframework.logicframework.integration.dao.ibatis.BaseDaoiBATIS;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * 包: com.auto.web.dao.impl
 * 源文件:UniqueReadDAOibatis.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月23日
 */
@Scope("prototype")
@Repository("uniqueReadDataService")
public class UniqueReadDAOibatis extends BaseDaoiBATIS implements UniqueReadDataService
{
    @Override
    public void add(UniqueReadBo bo)
    {
        sqlSessionTemplate.insert("uniqueRead.addUniqueBo",bo);
    }

    @Override
    public UniqueReadBo get(String uuid)
    {
        return sqlSessionTemplate.selectOne("uniqueRead.getUniqueBo",uuid);
    }
}
