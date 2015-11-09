package ch.uzh.se.se7en.client.mvp.presenters.impl;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.mvp.presenters.WelcomePresenter;
import ch.uzh.se.se7en.client.mvp.views.WelcomeView;

public class WelcomePresenterImpl implements WelcomePresenter {

	private WelcomeView welcomeView;

	@Inject
	public WelcomePresenterImpl(final WelcomeView welcomeView)
	{
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
}
