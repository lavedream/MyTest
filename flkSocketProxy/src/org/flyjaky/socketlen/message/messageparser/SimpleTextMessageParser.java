package org.flyjaky.socketlen.message.messageparser;

public class SimpleTextMessageParser implements MessageParser{

	@Override
	public String parseMessage(byte[] message) {
		return new String(message);
	}

}
