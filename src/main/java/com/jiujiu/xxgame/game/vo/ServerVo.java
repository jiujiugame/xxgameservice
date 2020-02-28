package com.jiujiu.xxgame.game.vo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import com.jiujiu.xxgame.game.GServer;
import com.jiujiu.xxgame.game.tcp.protocol.Message;
import io.netty.channel.Channel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServerVo
{
    public static enum Icon
    {
        DEFAULT,  OPEN,  CLOSE;
    }

    public static enum State
    {
        MAINTAIN(0, "维护"),  SMOOTH(1, "流畅"),  BUSY(2, "繁忙"),  FULL(3, "爆满");

        public final int value;

        private State(int state, String desc)
        {
            this.value = state;
        }
    }

    private int appId = GServer.__APP_ID;
    private int logicServerId;
    private int serverId;
    private String serverName;
    private int olCount;
    private int olLimit = 1000;
    private int areaId;
    private int state;
    private int show = 2;
    private boolean isNew;
    private boolean isHot;
    private boolean isRecommend;
    public List<ServerVo> children = new ArrayList(2);
    private Icon iconCls = Icon.DEFAULT;
    private String ip;
    private int port;
    private Date openDate;
    private String desc;

    public ServerVo(String name)
    {
        this.serverName = name;
    }

    public ServerVo(int id, String name)
    {
        this.logicServerId = id;
        this.serverName = name;
    }

    public ServerVo(int id, String name, int areaId)
    {
        this.logicServerId = id;
        this.serverName = name;
        this.areaId = areaId;
    }

    @JSONField(name="id")
    public int getLogicServerId()
    {
        return this.logicServerId;
    }

    public void setLogicServerId(int serverId)
    {
        this.logicServerId = serverId;
    }

    @JSONField(name="text")
    public String getServerName()
    {
        return this.serverName;
    }

    public void setServerName(String serverName)
    {
        this.serverName = serverName;
    }

    public String getIconCls()
    {
        switch (this.iconCls)
        {
            case DEFAULT:
                return "icon-ok";
            case OPEN:
                return "icon-no";
        }
        return null;
    }

    @JSONField(serialize=false)
    public int getOlCount()
    {
        return this.olCount;
    }

    public void setOlCount(int olCount)
    {
        this.olCount = olCount;
        if (olCount < 0) {
            this.state = 0;
        } else if (olCount >= this.olLimit / 2) {
            this.state = 3;
        } else if (olCount > this.olLimit / 3) {
            this.state = 3;
        } else if (olCount > this.olLimit / 8) {
            this.state = 2;
        } else {
            this.state = 1;
        }
        setIconCls(this.state == 0 ? Icon.CLOSE : Icon.OPEN);
    }

    @JSONField(serialize=false)
    public int getAppId()
    {
        return this.appId;
    }

    public void setAppId(int appId)
    {
        this.appId = appId;
    }

    @JSONField(serialize=false)
    public int getAreaId()
    {
        return this.areaId;
    }

    public void setAreaId(int areaId)
    {
        this.areaId = areaId;
    }

    @JSONField(serialize=false)
    public int getServerId()
    {
        return this.serverId;
    }

    public void setServerId(int serverId)
    {
        this.serverId = serverId;
    }

    public void setIconCls(Icon icon)
    {
        this.iconCls = icon;
    }

    @JSONField(serialize=false)
    public String getIp()
    {
        return this.ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    @JSONField(serialize=false)
    public int getPort()
    {
        return this.port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    @JSONField(serialize=false)
    public Date getOpenDate()
    {
        return this.openDate;
    }

    public void setOpenDate(Date openDate)
    {
        this.openDate = openDate;
    }

    @JSONField(serialize=false)
    public String getDesc()
    {
        return this.desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    @JSONField(serialize=false)
    public int getState()
    {
        return this.state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    @JSONField(serialize=false)
    public boolean isNew()
    {
        return this.isNew;
    }

    public void setNew(boolean isNew)
    {
        this.isNew = isNew;
    }

    @JSONField(serialize=false)
    public boolean isHot()
    {
        return this.isHot;
    }

    public void setHot(boolean isHot)
    {
        this.isHot = isHot;
    }

    @JSONField(serialize=false)
    public boolean isRecommend()
    {
        return this.isRecommend;
    }

    public void setRecommend(boolean isRecommend)
    {
        this.isRecommend = isRecommend;
    }

    @JSONField(serialize=false)
    public int getOlLimit()
    {
        return this.olLimit;
    }

    public void setOlLimit(int olLimit)
    {
        this.olLimit = olLimit;
    }

    @JSONField(serialize=false)
    public int getShow()
    {
        return this.show;
    }

    public void setShow(int show)
    {
        this.show = show;
    }

    public boolean request(final long key, short op, final String json)
    {
        Channel channel = GServer.getInstance().getChannel(this.logicServerId);
        if (channel != null)
        {
            channel.writeAndFlush(new Message()
            {
                protected void write()
                        throws IOException
                {
                    this.body.writeLong(key);
//                    this.body.writeShort(json);
                    this.body.writeString(json);
                }

                public short getType()
                {
                    return 2748;
                }
            }.getContent());
            return true;
        }
        return false;
    }

    public boolean write(Message message)
    {
        Channel channel = GServer.getInstance().getChannel(this.logicServerId);
        if (channel != null)
        {
            channel.writeAndFlush(message.getContent());
            return true;
        }
        return false;
    }

    public JSONObject toList()
    {
        JSONObject json = new JSONObject();
        json.put("id", Integer.valueOf(this.logicServerId));
        json.put("serverId", Integer.valueOf(this.serverId));
        json.put("name", this.serverName);
        json.put("areaId", Integer.valueOf(this.areaId));
        json.put("host", this.port > 0 ? this.ip + ":" + this.port : "");
        json.put("openDate", this.openDate);
        json.put("desc", this.desc);
        json.put("olCount", Integer.valueOf(this.olCount));
        json.put("state", Integer.valueOf(this.state));
        json.put("isNew", Integer.valueOf(this.isNew ? 1 : 0));
        json.put("isHot", Integer.valueOf(this.isHot ? 1 : 0));
        json.put("isRecommend", Integer.valueOf(this.isRecommend ? 1 : 0));
        json.put("olLimit", Integer.valueOf(this.olLimit));
        json.put("show", Integer.valueOf(this.show));
        return json;
    }
}