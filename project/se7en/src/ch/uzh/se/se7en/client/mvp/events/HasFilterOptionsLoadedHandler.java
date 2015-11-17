package ch.uzh.se.se7en.client.mvp.events;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasFilterOptionsLoadedHandler extends HasHandlers {
	public HandlerRegistration addHasFilterOptionsLoadedHandler(FilterOptionsLoadedHandler handler); 
}
