package com.za.console.service.dto.base;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class CreateAndUpdateTime {

    private Date createTime;

    private Date updateTime;

    public Date getCreateTime() {
        if (createTime != null) {
            return (Date) createTime.clone();
        }
        return null;
    }

    public void setCreateTime(Date createTime) {
        if (createTime != null) {
            this.createTime = (Date) createTime.clone();
        } else {
            this.createTime = null;
        }
    }

    public Date getUpdateTime() {
        if (updateTime != null) {
            return (Date) updateTime.clone();
        }
        return null;
    }

    public void setUpdateTime(Date updateTime) {
        if (updateTime != null) {
            this.updateTime = (Date) updateTime.clone();
        } else {
            this.updateTime = null;
        }
    }

}
