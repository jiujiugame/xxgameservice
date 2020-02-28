package com.jiujiu.xxgame.game.handler;

import com.jiujiu.xxgame.game.pay.request.GClientEvent;
import com.jiujiu.xxgame.game.tcp.protocol.NetHandler;
import com.jiujiu.xxgame.game.tcp.protocol.Packet;

@GClientEvent(0xf2)
public class PayPingHandler extends NetHandler {

	@Override
	public void execute(Packet pak) {
//		Integer sid = pak.getAttr(GGlobal._KEY_SERVER_ID);
	}

}
