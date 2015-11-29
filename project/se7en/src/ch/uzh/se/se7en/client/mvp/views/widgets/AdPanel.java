package ch.uzh.se.se7en.client.mvp.views.widgets;

import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.constants.IconType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AdPanel extends Composite{

	private static AdPanelUiBinder uiBinder = GWT.create(AdPanelUiBinder.class);

	interface AdPanelUiBinder extends UiBinder<Widget, AdPanel> {
	}
	
	@UiField Image adImage;
	@UiField Panel adContainer;
	
	public AdPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("closeAd")
	public void onCloseBtnClicked(final ClickEvent event) {
		adContainer.setStyleName("closeAd");
	}
	

}
