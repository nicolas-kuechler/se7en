package ch.uzh.se.se7en.client.mvp.views;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

import ch.uzh.se.se7en.client.mvp.presenters.TablePresenter;
import ch.uzh.se.se7en.shared.model.Film;

public interface TableView extends IsWidget {
	public void setPresenter(TablePresenter presenter);
	public void setTable(List<Film> films);

}
