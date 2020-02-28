package com.jiujiu.xxgame.game.handler;

import com.jiujiu.xxgame.game.GGlobal;
import com.jiujiu.xxgame.game.GServer;
import com.jiujiu.xxgame.game.pay.request.GClientEvent;
import com.jiujiu.xxgame.game.tcp.protocol.NetHandler;
import com.jiujiu.xxgame.game.tcp.protocol.Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@GClientEvent(0xf1)
public class PayJoinHandler extends NetHandler {
	private final static Logger logger = LogManager.getLogger(PayJoinHandler.class);

	@Override
	public void execute(Packet pak) {
		int sid = pak.getInt();
		pak.setAttr(GGlobal._KEY_SERVER_ID, sid);
		GServer.getInstance().addChannel(pak.getSession());
		logger.info("sid:{} pay join!", sid);
	}

}
