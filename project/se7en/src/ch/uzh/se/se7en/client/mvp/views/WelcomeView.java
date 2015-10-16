package ch.uzh.se.se7en.client.mvp.views;

import com.google.gwt.user.client.ui.IsWidget;

import ch.uzh.se.se7en.client.mvp.presenters.WelcomePresenter;

public interface WelcomeView extends IsWidget {
	public void setPresenter(WelcomePresenter presenter);
}
