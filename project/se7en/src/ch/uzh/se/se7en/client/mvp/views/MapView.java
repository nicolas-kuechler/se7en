package ch.uzh.se.se7en.client.mvp.views;

import java.util.List;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

import ch.uzh.se.se7en.client.mvp.presenters.MapPresenter;
import ch.uzh.se.se7en.shared.model.Country;
import ch.uzh.se.se7en.shared.model.Genre;

public interface MapView extends IsWidget{
	public void setPresenter(MapPresenter presenter);
	public void setGeoChart(List<Country> countries);
	
	public void setGenreTable(List<Genre> genres);
	public void setPieChart(List<Genre> significantGenres);
	
	//public HasValue<Range> getYearRange(); //Bootstrap Range verwenden
	//public HasValue<Filter> getFilter(); //provide access to selected Country in geoChart and Filter criteria to get the genre data needed for table and piechart
}
