package ch.uzh.se.se7en.client.mvp.views.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

//TODO DB Remove class and place advertisement better
public class FooterBar extends Composite {

	private static FooterBarUiBinder uiBinder = GWT.create(FooterBarUiBinder.class);

	interface FooterBarUiBinder extends UiBinder<Widget, FooterBar> {
	}

	public FooterBar() {
		initWidget(uiBinder.createAndBindUi(this));
	}



}
