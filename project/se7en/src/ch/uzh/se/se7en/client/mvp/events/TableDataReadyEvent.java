package ch.uzh.se.se7en.client.mvp.events;

import com.google.gwt.event.shared.GwtEvent;

public class TableDataReadyEvent extends GwtEvent<TableDataReadyHandler>{
	
	private static Type<TableDataReadyHandler> TYPE = new Type<TableDataReadyHandler>();
	
	/**
	 * Gets the event type associated with load events.
	 * 
	 * @return the handler type
	 */
	public static Type<TableDataReadyHandler> getType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(TableDataReadyHandler handler) {
		handler.onTableDataReadyEvent(this);
	}

	static protected void fire(HasTableDataReadyHandler source) {
		source.fireEvent(new LoadingTableEvent());
	}

	@Override
	public Type<TableDataReadyHandler> getAssociatedType() {
		return TYPE;
	}

}
