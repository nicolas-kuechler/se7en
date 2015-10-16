package ch.uzh.se.se7en.client.mvp.events;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasTableDataReadyHandler extends HasHandlers{
	public HandlerRegistration addHasTableDataReadyHandler(TableDataReadyHandler handler);
}
