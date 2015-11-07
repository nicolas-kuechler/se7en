package ch.uzh.se.se7en.junit.client.presenter;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.impl.FilterPresenterImpl;
import ch.uzh.se.se7en.client.mvp.views.FilterView;

@RunWith(JukitoRunner.class)
public class FilterPresenterTest {

	@Inject 
	FilterPresenterImpl filterPresenter;	
	@Inject
	FilmDataModel filmDataModel;
	@Inject
	EventBus eventBus;
	@Inject
	FilterView filterView;
	@Inject
	HasWidgets container;
	
	@Test
	public void testBind() {
		verify(filterView).setPresenter(filterPresenter);
	}
	
	@Test
	public void testGo(){
		filterPresenter.go(container);
		verify(container).clear();
		verify(container).add(filterView.asWidget());
	}

}
