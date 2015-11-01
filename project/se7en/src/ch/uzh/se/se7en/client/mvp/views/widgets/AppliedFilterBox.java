package ch.uzh.se.se7en.client.mvp.views.widgets;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

import ch.uzh.se.se7en.client.ClientLog;

public class AppliedFilterBox extends Composite implements HasValue<List<String>>{

	private static AppliedFilterBoxUiBinder uiBinder = GWT.create(AppliedFilterBoxUiBinder.class);

	interface AppliedFilterBoxUiBinder extends UiBinder<Widget, AppliedFilterBox> {
	}

	public AppliedFilterBox() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//TODO Box where all the applied filters are visible
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
		ClientLog.writeMsg(value.toString());
	}

	@Override
	public void setValue(List<String> value, boolean fireEvents) {
		// TODO Auto-generated method stub
		
	}

}
