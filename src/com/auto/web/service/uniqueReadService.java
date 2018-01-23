package com.auto.web.service;

import com.auto.web.bo.UniqueReadBo;
import com.auto.web.dao.UniqueReadDataService;
import com.jeeframework.logicframework.biz.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 包: com.auto.web.service
 * 源文件:uniqueReadService.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月23日
 */
@Service("uniqueReadService")
public class UniqueReadService extends BaseService
{
    @Resource
    private UniqueReadDataService uniqueReadDataService;

    public void add(UniqueReadBo bo){
        uniqueReadDataService.add(bo);
    }

    public boolean isNotEmpty(String uuid){
        UniqueReadBo bo=uniqueReadDataService.get(uuid);
        if(bo==null)
            return false;
        return true;
    }
}
