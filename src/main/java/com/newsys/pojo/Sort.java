package com.newsys.pojo;

import java.io.Serializable;

public class Sort implements Serializable {
    private Integer sortId;

    private String sortName;

    private Integer sortFid;

    private Integer sortStatus;

    private static final long serialVersionUID = 1L;

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName == null ? null : sortName.trim();
    }

    public Integer getSortFid() {
        return sortFid;
    }

    public void setSortFid(Integer sortFid) {
        this.sortFid = sortFid;
    }

    public Integer getSortStatus() {
        return sortStatus;
    }

    public void setSortStatus(Integer sortStatus) {
        this.sortStatus = sortStatus;
    }

    @Override
    public String toString() {
        return "Sort{" +
                "sortId=" + sortId +
                ", sortName='" + sortName + '\'' +
                ", sortFid=" + sortFid +
                ", sortStatus=" + sortStatus +
                '}';
    }
}