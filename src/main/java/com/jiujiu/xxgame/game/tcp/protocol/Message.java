package com.jiujiu.xxgame.game.tcp.protocol;

import com.jiujiu.xxgame.game.tcp.BufferUtil;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * 客户端消息基类(单播消息)
 * @author agui
 */
public abstract class Message {
	
	/** 包头 */
	protected Header header;
	
	/** 包体 */
	protected Body body;

	/**
	 * 消息内容
	 */
	protected ByteBuf content;

	public Message() {
		this(new Body());
	}

	public Message(Body body) {
		this.header = newHeader();
		this.body = body;
	}

	/**
	 * 包头构造
	 */
	protected Header newHeader() {
		return new Header();
	}

	/**
	 * 数据包
	 */
	protected ByteBuf newContent() {
		return BufferUtil.getAutoBuffer(body.getBuffer().readableBytes() + Header.SIZE);
	}
	
	/**
	 * 包头编码
	 */
	protected void encodeHeader() {
		header.setType(getType());
		header.setLength(body.getBuffer().readableBytes());
		header.encode(content);
	}

	/**
	 * 包体体编码
	 */
	protected void encodeBody() {
		content.writeBytes(body.getBuffer());
	}
	
	/**
	 * 获取消息内容
	 * 
	 * @return
	 */
	public ByteBuf getContent() {
		try {
			if (null == content) {

				write();

				content = newContent();
				encodeHeader();
				encodeBody();

				body = null;
				header = null;
			}

			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将消息内容写入(包体)输出管道
	 * 
	 * @throws IOException
	 */
	protected abstract void write() throws IOException;

	/**
	 * 下行消息编号
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract short getType();

}
