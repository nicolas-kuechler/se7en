package ch.uzh.se.se7en.client.mvp.events;

import com.google.gwt.event.shared.EventHandler;

public interface FilterAppliedHandler extends EventHandler {
	public void onFilterAppliedEvent(FilterAppliedEvent event);

}
