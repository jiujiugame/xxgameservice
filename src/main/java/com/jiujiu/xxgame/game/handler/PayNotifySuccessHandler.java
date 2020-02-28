package com.jiujiu.xxgame.game.handler;

import com.jiujiu.xxgame.game.api.rpc.RpcManager;
import com.jiujiu.xxgame.game.api.rpc.RpcResponse;
import com.jiujiu.xxgame.game.pay.request.GClientEvent;
import com.jiujiu.xxgame.game.tcp.protocol.NetHandler;
import com.jiujiu.xxgame.game.tcp.protocol.Packet;

/**
 * 充值通知成功回调
 * 
 * @author lxm
 */
@GClientEvent(0xf5)
public class PayNotifySuccessHandler extends NetHandler {

	@Override
	public void execute(Packet packet) {
		final long key = packet.getLong();
		String result = packet.getString();
		RpcResponse response = RpcManager.removeRpc(key);
		if (response != null) {
			response.setResult(result);
			response.getCounter().countDown();
		}
	}
}
