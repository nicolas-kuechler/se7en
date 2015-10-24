package ch.uzh.se.se7en.client.mvp.views.widgets;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public class MultiSelect extends Composite implements HasValue<List<String>>{

	private static MultiSelectUiBinder uiBinder = GWT.create(MultiSelectUiBinder.class);

	interface MultiSelectUiBinder extends UiBinder<Widget, MultiSelect> {
	}

	public MultiSelect() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//TODO MultiSelect according to NK SevenDemo
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<String>> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(List<String> value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValue(List<String> value, boolean fireEvents) {
		// TODO Auto-generated method stub
		
	}

}
