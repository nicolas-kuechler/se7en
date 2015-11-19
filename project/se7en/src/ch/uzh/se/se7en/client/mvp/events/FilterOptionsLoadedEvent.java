package ch.uzh.se.se7en.client.mvp.events;

import com.google.gwt.event.shared.GwtEvent;

public class FilterOptionsLoadedEvent extends GwtEvent<FilterOptionsLoadedHandler> {
	
	private static Type<FilterOptionsLoadedHandler> TYPE = new Type<FilterOptionsLoadedHandler>();
	
	/**
	 * Gets the event type associated with load events.
	 * 
	 * @return the handler type
	 */
	public static Type<FilterOptionsLoadedHandler> getType() {
		return TYPE;
	}

	@Override
	public Type<FilterOptionsLoadedHandler> getAssociatedType() {
		return TYPE;
	}

	static protected void fire(HasFilterOptionsLoadedHandler source) {
		source.fireEvent(new FilterOptionsLoadedEvent());
	}
	
	@Override
	protected void dispatch(FilterOptionsLoadedHandler handler) {
		handler.onFilterOptionsLoadedEvent(this);
		
	}
}
