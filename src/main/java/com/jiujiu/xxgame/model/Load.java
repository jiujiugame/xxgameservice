package com.jiujiu.xxgame.model;

public class Load {
    private Integer load = 0;
    private Integer appId = 80;
    private Integer logicServerId = 123;
    private Integer type = 259;
    private Integer acrossServerId = 0;

    public Integer getLoad() {
        return load;
    }

    public void setLoad(Integer load) {
        this.load = load;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getLogicServerId() {
        return logicServerId;
    }

    public void setLogicServerId(Integer logicServerId) {
        this.logicServerId = logicServerId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAcrossServerId() {
        return acrossServerId;
    }

    public void setAcrossServerId(Integer acrossServerId) {
        this.acrossServerId = acrossServerId;
    }

    @Override
    public String toString() {
        return "{" +
                "load=" + load +
                ", appId=" + appId +
                ", logicServerId=" + logicServerId +
                ", type=" + type +
                ", acrossServerId=" + acrossServerId +
                '}';
    }
}
