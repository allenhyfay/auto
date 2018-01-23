package com.auto.web.dao;

import com.auto.web.bo.UniqueReadBo;

/**
 * 包: com.auto.web.dao.impl
 * 源文件:UniqueReadDataService.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月23日
 */
public interface UniqueReadDataService
{
    void add(UniqueReadBo bo);

    UniqueReadBo get(String uuid);

}
