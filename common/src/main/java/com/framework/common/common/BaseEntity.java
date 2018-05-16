package com.framework.common.common;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity基类
 */
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1599925460585783120L;

    protected String createUserId;
    protected Date createDate;
    protected String lastUpdateUserId;
    protected Date lastUpdateDate;
    protected int revision = 1;

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    public void setLastUpdateUserId(String lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }
}
