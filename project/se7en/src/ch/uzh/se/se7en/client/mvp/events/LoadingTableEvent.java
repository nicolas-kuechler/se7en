package ch.uzh.se.se7en.client.mvp.events;

import com.google.gwt.event.shared.GwtEvent;

public class LoadingTableEvent extends GwtEvent<LoadingTableHandler>{

	private static Type<LoadingTableHandler> TYPE = new Type<LoadingTableHandler>();
	
	/**
	 * Gets the event type associated with load events.
	 * 
	 * @return the handler type
	 */
	public static Type<LoadingTableHandler> getType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(LoadingTableHandler handler) {
		handler.onLoadingTableEvent(this);
	}

	static protected void fire(HasLoadingTableHandler source) {
		source.fireEvent(new LoadingTableEvent());
	}

	@Override
	public Type<LoadingTableHandler> getAssociatedType() {
		return TYPE;
	}

}
