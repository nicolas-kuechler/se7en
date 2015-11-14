package ch.uzh.se.se7en.client.mvp.views.widgets;

import java.util.List;

import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import ch.uzh.se.se7en.client.ClientLog;

public class AppliedFilterBox extends Composite{

	private static AppliedFilterBoxUiBinder uiBinder = GWT.create(AppliedFilterBoxUiBinder.class);

	interface AppliedFilterBoxUiBinder extends UiBinder<Widget, AppliedFilterBox> {}
	
	@UiField
	Panel rootPanel;
	
	public AppliedFilterBox() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
	}
//TODO CH Comment
	public void setValue(List<String> values) {
		rootPanel.clear();
		for(String value : values){
			Label filterLabel = new Label();
			filterLabel.setText(value);
			filterLabel.setMarginBottom(2);
			filterLabel.setMarginLeft(2);
			filterLabel.setMarginTop(2);
			filterLabel.setMarginRight(2);
			rootPanel.add(filterLabel);
		}
		ClientLog.writeErr(values.toString());
	}

}
