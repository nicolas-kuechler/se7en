package ch.uzh.se.se7en.client.mvp.views.impl;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

import ch.uzh.se.se7en.client.mvp.presenters.FilterPresenter;
import ch.uzh.se.se7en.client.mvp.views.FilterView;
import org.gwtbootstrap3.extras.slider.client.ui.Range;

public class FilterViewImpl extends Composite implements FilterView{

	private static FilterViewImplUiBinder uiBinder = GWT.create(FilterViewImplUiBinder.class);

	interface FilterViewImplUiBinder extends UiBinder<Widget, FilterViewImpl> {
	}

	public FilterViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(FilterPresenter presenter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HasValue<String> getNameBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasValue<Range> getLengthSlider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasValue<Range> getYearSlider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasValue<List<String>> getCountrySelect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasValue<List<String>> getLanguageSelect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasValue<List<String>> getGenreSelect() {
		// TODO Auto-generated method stub
		return null;
	}

}
