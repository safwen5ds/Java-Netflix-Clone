package org.fsb.FlixFlow.Views;

import javafx.event.Event;
import javafx.event.EventType;

public class OpenSaisonPageEvent extends Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final EventType<OpenSaisonPageEvent> OPEN_SAISON_PAGE = new EventType<>(ANY, "OPEN_SAISON_PAGE");

	public OpenSaisonPageEvent() {
		super(OPEN_SAISON_PAGE);
	}
}
