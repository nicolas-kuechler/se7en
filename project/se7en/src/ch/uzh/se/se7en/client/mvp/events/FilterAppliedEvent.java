package ch.uzh.se.se7en.client.mvp.events;

import com.google.gwt.event.shared.GwtEvent;

public class FilterAppliedEvent extends GwtEvent<FilterAppliedHandler> {
	
	private static Type<FilterAppliedHandler> TYPE = new Type<FilterAppliedHandler>();
	
	/**
	 * Gets the event type associated with load events.
	 * 
	 * @return the handler type
	 */
	public static Type<FilterAppliedHandler> getType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(FilterAppliedHandler handler) {
		handler.onFilterAppliedEvent(this);
	}

	static protected void fire(HasFilterAppliedHandler source) {
		source.fireEvent(new FilterAppliedEvent());
	}

	@Override
	public Type<FilterAppliedHandler> getAssociatedType() {
		return TYPE;
	}

}
