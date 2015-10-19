package ch.uzh.se.se7en.client.mvp.views.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MultiSelect extends Composite {

	private static MultiSelectUiBinder uiBinder = GWT.create(MultiSelectUiBinder.class);

	interface MultiSelectUiBinder extends UiBinder<Widget, MultiSelect> {
	}

	public MultiSelect() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//TODO MultiSelect according to NK SevenDemo
	}

}
