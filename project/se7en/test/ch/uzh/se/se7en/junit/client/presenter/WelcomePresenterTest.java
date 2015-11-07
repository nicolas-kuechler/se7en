package ch.uzh.se.se7en.junit.client.presenter;

import static org.mockito.Mockito.verify;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.mvp.presenters.impl.WelcomePresenterImpl;
import ch.uzh.se.se7en.client.mvp.views.WelcomeView;

@RunWith(JukitoRunner.class)
public class WelcomePresenterTest {

	@Inject 
	WelcomePresenterImpl welcomePresenter;	
	@Inject
	WelcomeView welcomeView;
	@Inject
	HasWidgets container;
	
	@Test
	public void testBind() {
		verify(welcomeView).setPresenter(welcomePresenter);
	}
	
	@Test
	public void testGo(){
		welcomePresenter.go(container);
		verify(container).clear();
		verify(container).add(welcomeView.asWidget());
	}

}
