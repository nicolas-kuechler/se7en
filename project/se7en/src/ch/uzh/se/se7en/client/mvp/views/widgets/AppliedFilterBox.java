package ch.uzh.se.se7en.client.mvp.views.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AppliedFilterBox extends Composite {

	private static AppliedFilterBoxUiBinder uiBinder = GWT.create(AppliedFilterBoxUiBinder.class);

	interface AppliedFilterBoxUiBinder extends UiBinder<Widget, AppliedFilterBox> {
	}

	public AppliedFilterBox() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//TODO Box where all the applied filters are visible
	}

}
