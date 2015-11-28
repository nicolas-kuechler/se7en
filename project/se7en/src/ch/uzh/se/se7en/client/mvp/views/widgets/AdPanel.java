package ch.uzh.se.se7en.client.mvp.views.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class AdPanel extends Composite{

	private static AdPanelUiBinder uiBinder = GWT.create(AdPanelUiBinder.class);

	interface AdPanelUiBinder extends UiBinder<Widget, AdPanel> {
	}

	public AdPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}


}
