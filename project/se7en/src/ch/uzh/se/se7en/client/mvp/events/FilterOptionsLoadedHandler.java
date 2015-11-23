package ch.uzh.se.se7en.client.mvp.events;

import com.google.gwt.event.shared.EventHandler;

public interface FilterOptionsLoadedHandler extends EventHandler {
	public void onFilterOptionsLoadedEvent(FilterOptionsLoadedEvent event);
}
