package com.auto.web.bo;

import java.util.Date;

/**
 * 包: com.auto.web.bo
 * 源文件:UniqueReadBo.java
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2018年01月23日
 */
public class UniqueReadBo
{
    private String uuid;
    private String devices;
    private String title;
    private Date createTime;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getDevices()
    {
        return devices;
    }

    public void setDevices(String devices)
    {
        this.devices = devices;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
}
