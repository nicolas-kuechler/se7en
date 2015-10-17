package ch.uzh.se.se7en.client.mvp.presenters.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;

import ch.uzh.se.se7en.client.mvp.ClientFactory;
import ch.uzh.se.se7en.client.mvp.presenters.WelcomePresenter;
import ch.uzh.se.se7en.client.mvp.views.WelcomeView;

public class WelcomePresenterImpl implements WelcomePresenter {

	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private EventBus eventBus;
	private WelcomeView welcomeView;
	
	public WelcomePresenterImpl(final WelcomeView welcomeView)
	{
		eventBus = clientFactory.getEventBus();
		this.welcomeView = welcomeView;
		bind();
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(welcomeView.asWidget());
	}

	@Override
	public void bind() {
		welcomeView.setPresenter(this);
	}

	@Override
	public void setLoadingState(String state) {
		// TODO Auto-generated method stub
		
	}

}
