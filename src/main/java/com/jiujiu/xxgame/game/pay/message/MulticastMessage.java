package com.jiujiu.xxgame.game.pay.message;

import com.jiujiu.xxgame.game.tcp.protocol.Message;
import io.netty.buffer.ByteBuf;

/**
 * 组播消息
 * 
 * @author agui
 */
public abstract class MulticastMessage extends Message {

	@Override
	public ByteBuf getContent() {
		return super.getContent().slice();
	}

	public String getRoute() {
		return null;
	}
}
