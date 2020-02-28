package com.jiujiu.xxgame.game.tcp.protocol;

import com.jiujiu.xxgame.game.GConfig;
import com.jiujiu.xxgame.game.GGlobal;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * 请求消息解码类
 * 
 * @author agui
 */
public final class GDecoder extends ByteToMessageDecoder {
	private final static Logger logger = LogManager.getLogger(GDecoder.class);
	private static int __RESPONSE_MAX_LEN = GConfig.getInstance().getInt("gm.response.maxlen", GGlobal.__RESPONSE_MAX_LEN);

	public GDecoder() {

	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> packets) throws Exception {
		if (in.readableBytes() < Header.SIZE) {
			return;
		}
		in = in.order(GGlobal.__BYTE_ORDER);
		in.markReaderIndex();
		Packet packet = new Packet(ctx.channel());
		Header header = packet.getHeader();
		header.decode(in);

		int len = header.getLength();

		if (len > __RESPONSE_MAX_LEN || len < 0) {
			Channel session = ctx.channel();
			logger.warn(new StringBuilder().append("包体长度错误：").append(len).append(" 句柄：").append(header.getTypeHexString()).append(session.remoteAddress()).toString());
			session.close();
			return;
		}

		if (in.readableBytes() < len) {
			in.resetReaderIndex();
			header = null;
			packet = null;
			// Out.debug("长度不足：" + in.readableBytes() +" - "+ len);
			return;
		}

		ByteBuf body = packet.init();
		body.writeBytes(in, len);

		// byte[] body = new byte[len];
		// in.readBytes(body);
		// packet.fillBody(body);

		packets.add(packet);

		// decode(ctx, in, packets);
	}

}
