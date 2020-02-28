package com.jiujiu.xxgame.game.tcp.client;


import com.jiujiu.xxgame.game.tcp.NetEvent;
import com.jiujiu.xxgame.game.tcp.protocol.Packet;

/**
 * 处理事件接口
 * @author agui
 */
public interface ClientCallback extends NetEvent {

	/** 接收到信息包 */
	void handlePacket(Packet packet);
	
}
