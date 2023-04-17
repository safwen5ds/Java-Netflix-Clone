package org.fsb.FlixFlow.Views;

import javafx.event.Event;
import javafx.event.EventType;

public class OpenSaisonPageEvent extends Event {
	public static final EventType<OpenSaisonPageEvent> OPEN_SAISON_PAGE = new EventType<>(ANY, "OPEN_SAISON_PAGE");

	public OpenSaisonPageEvent() {
		super(OPEN_SAISON_PAGE);
	}
}
