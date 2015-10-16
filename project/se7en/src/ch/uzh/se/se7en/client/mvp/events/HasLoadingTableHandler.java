package ch.uzh.se.se7en.client.mvp.events;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasLoadingTableHandler extends HasHandlers{
	 public HandlerRegistration addHasLoadingTableHandler(LoadingTableHandler handler); 
}
