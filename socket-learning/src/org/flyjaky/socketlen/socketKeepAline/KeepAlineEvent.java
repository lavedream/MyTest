package org.flyjaky.socketlen.socketKeepAline;

import org.flyjaky.socketlen.event.Event;
import org.flyjaky.socketlen.event.SocketEventInterface;

public class KeepAlineEvent implements SocketEventInterface{

	@Override
	public void handler(Event event) {
		System.out.println(event.getEventCode());
	}

}
