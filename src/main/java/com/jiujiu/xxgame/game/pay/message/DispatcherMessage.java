package com.jiujiu.xxgame.game.pay.message;

import com.jiujiu.xxgame.game.tcp.protocol.Packet;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

/***
 * 功能描述：直接转发消息封装
 * @author agui
 */
public class DispatcherMessage extends MulticastMessage {

	private ByteBuf buffer;
	
	public DispatcherMessage(Packet packet) {
		this.header.decode(packet.getHeader());
		this.buffer = packet.getBody();
	}

	@Override
	protected void write() throws IOException {
		body.writeBuffer(buffer);
	}

	@Override
	public short getType() {
		return header.getType();
	}

}
