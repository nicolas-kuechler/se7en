package ch.uzh.se.se7en.client.mvp.events;

import com.google.gwt.event.shared.EventHandler;

public interface TableDataReadyHandler extends EventHandler{
	public void onTableDataReadyEvent(TableDataReadyEvent event);

}
