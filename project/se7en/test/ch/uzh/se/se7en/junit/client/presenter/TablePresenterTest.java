package ch.uzh.se.se7en.junit.client.presenter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.mvp.model.FilmDataModel;
import ch.uzh.se.se7en.client.mvp.presenters.impl.TablePresenterImpl;
import ch.uzh.se.se7en.client.mvp.views.TableView;
import ch.uzh.se.se7en.shared.model.Film;


@RunWith(JukitoRunner.class)
public class TablePresenterTest {
	
	@Inject 
	TablePresenterImpl tablePresenter;	
	@Inject
	FilmDataModel filmDataModel;
	@Inject
	TableView tableView;
	@Inject
	HasWidgets container;
	
	
	
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
		verify(filmDataModel).setFilmList(films);
		verify(tableView).setTable(films);
	}
	
	@Test
	public void testBind() {
		verify(tableView).setPresenter(tablePresenter);
	}
	
	@Test
	public void testGo(){
		tablePresenter.go(container);
		verify(container).clear();
		verify(container).add(tableView.asWidget());
	}
	
}
