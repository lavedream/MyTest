package org.flyjaky.socketlen.event;

/**
 * 
 * 事件时编程，当链接出现，响应的事件时，传递给应用层，进行业务处理
 * 
 * @author liushuaic
 * @date 2018-03-20 10:48
 * 
 **/
public class Event {

	private int eventCode;

	private String eventMessage;

	public Event(int eventCode, String eventMessage) {
		this.eventCode = eventCode;
		this.eventMessage = eventMessage;
	}

	public int getEventCode() {
		return eventCode;
	}

	public void setEventCode(int eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventMessage() {
		return eventMessage;
	}

	public void setEventMessage(String eventMessage) {
		this.eventMessage = eventMessage;
	}

}
