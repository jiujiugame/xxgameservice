package com.jiujiu.xxgame.model;

public class Server {
    private Integer appId = 80;
    private Integer areaId = 0;
    private String host = "";
    private Boolean isHot = false;
    private Boolean isNew = false;
    private Boolean isRecommend = false;
    private Integer load = 1;
    private Integer logicServerId = 123;
    private String name = "";
    private Integer olCount = 0;
    private Integer olLimit = 2000;
    private Long pingtime = 0L;
    private Integer port = 3015;
    private Integer show = 2;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean hot) {
        isHot = hot;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Boolean getRecommend() {
        return isRecommend;
    }

    public void setRecommend(Boolean recommend) {
        isRecommend = recommend;
    }

    public Integer getLoad() {
        return load;
    }

    public void setLoad(Integer load) {
        this.load = load;
    }

    public Integer getLogicServerId() {
        return logicServerId;
    }

    public void setLogicServerId(Integer logicServerId) {
        this.logicServerId = logicServerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOlCount() {
        return olCount;
    }

    public void setOlCount(Integer olCount) {
        this.olCount = olCount;
    }

    public Integer getOlLimit() {
        return olLimit;
    }

    public void setOlLimit(Integer olLimit) {
        this.olLimit = olLimit;
    }

    public Long getPingtime() {
        return pingtime;
    }

    public void setPingtime(Long pingtime) {
        this.pingtime = pingtime;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public String getServerString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.getLogicServerId());
        sb.append(",");
        sb.append("\"");
        sb.append(this.getName());
        sb.append("\"");
        sb.append(",");
        sb.append("\"");
        sb.append(this.getHost());
        sb.append("\"");
        sb.append(",");
        sb.append(this.getPort());
        sb.append(",");
        sb.append(this.getLoad());
        sb.append(",");
        sb.append(this.isNew? 1: 0);
        sb.append(",");
        sb.append(this.isHot? 1: 0);
        sb.append(",");
        sb.append(this.isRecommend? 1: 0);
        sb.append(",");
        sb.append("null,");
        sb.append(this.getShow());
        sb.append(",");
        sb.append("[]");
        sb.append("]");
        return sb.toString();
    }
}
