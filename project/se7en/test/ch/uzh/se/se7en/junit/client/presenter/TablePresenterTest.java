package ch.uzh.se.se7en.junit.client.presenter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.mvp.events.FilterAppliedEvent;
import ch.uzh.se.se7en.client.mvp.events.FilterAppliedHandler;
import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.impl.TablePresenterImpl;
import ch.uzh.se.se7en.client.mvp.views.TableView;
import ch.uzh.se.se7en.client.rpc.FilmListExportServiceAsync;
import ch.uzh.se.se7en.client.rpc.FilmListServiceAsync;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;

@RunWith(JukitoRunner.class)
public class TablePresenterTest {

	
	TablePresenterImpl tablePresenter;
	@Inject
	FilmDataModel filmDataModel;
	@Inject
	TableView tableView;
	@Inject
	HasWidgets container;
	@Inject
	EventBus eventBus;
	
	@Inject FilmListServiceAsync filmService;
	@Inject FilmListExportServiceAsync filmExportService;
	FilmFilter demoFilterWithResult = new FilmFilter("filter with result.size()>0");
	FilmFilter demoFilterWithoutResult = new FilmFilter("result.size()==0");
	
	@Before
	public void setup()
	{
		//imitate the onSuccess method of the rpc call getFilmList()
		doAnswer(new Answer<List<Film>>(){
			@Override
			public List<Film> answer(InvocationOnMock invocation) throws Throwable {
				AsyncCallback<List<Film>> callback = (AsyncCallback) invocation.getArguments()[1];
				FilmFilter filter = (FilmFilter) invocation.getArguments()[0];
				List<Film> filmsMatchingFilter = new ArrayList<Film>();
				if(filter.getName().equals("filter with result.size()>0"))
				{
					filmsMatchingFilter.add(new Film("Film1"));
					filmsMatchingFilter.add(new Film("Film2"));
				}
				callback.onSuccess(filmsMatchingFilter);
				return null;
			}
		}).when(filmService).getFilmList(Matchers.any(FilmFilter.class),(AsyncCallback<List<Film>>) Mockito.any());
		
		tablePresenter = new TablePresenterImpl(eventBus, tableView, filmDataModel, filmService, filmExportService);
	}
	
	@Test
	public void testSetupTableUpdate()
	{
		//verify that eventHandler is added
		verify(eventBus).addHandler(Matchers.eq(FilterAppliedEvent.getType()), Matchers.any(FilterAppliedHandler.class));
	}
	
	@Test
	public void testFetchData()
	{
		//result from server is a list with at least one film -> give this film list to tableView
		when(filmDataModel.getAppliedFilter()).thenReturn(demoFilterWithResult);
		tablePresenter.fetchData();
		//1st called from constructor (no results info), 2nd called when rpc starts loading (loading info),3rd the result from the rpc
		verify(tableView, times(3)).setTable(Matchers.anyListOf(Film.class));
		
		
		//result from server is empty list -> tableView receives pseudo film list with information
		when(filmDataModel.getAppliedFilter()).thenReturn(demoFilterWithoutResult);
		tablePresenter.fetchData();
		//1st called from constructor (no results info), 2nd the result of the empty rpc 
		verify(tableView, times(2)).setTable(Matchers.eq(tablePresenter.createPseudoFilmList("No Search Results Found")));
	}
	
	@Test
	public void testPseudoFilmList() {

		List<Film> expected = Arrays.asList(new Film("Test"));
		List<Film> notExpected = Arrays.asList(new Film("Test2"));

		assertThat(tablePresenter.createPseudoFilmList("Test"), is(expected));
		assertThat(tablePresenter.createPseudoFilmList("Test"), is(not(notExpected)));
	}

	@Test
	public void testUpdateTable() {
		List<Film> films = Arrays.asList(new Film("Test"));
		tablePresenter.updateTable(films);
		verify(tableView).setTable(films);
	}

	@Test
	public void testBind() {
		verify(tableView).setPresenter(tablePresenter);
	}

	@Test
	public void testGo() {
		tablePresenter.go(container);
		verify(container).clear();
		verify(container).add(tableView.asWidget());
	}

}
