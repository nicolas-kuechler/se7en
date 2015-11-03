package ch.uzh.se.se7en.client.mvp.views.widgets;

import java.util.List;

import org.gwtbootstrap3.extras.select.client.ui.OptGroup;
import org.gwtbootstrap3.extras.select.client.ui.Option;
import org.gwtbootstrap3.extras.select.client.ui.Select;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;


public class MultiSelect extends Composite implements HasValue<List<String>>{

	private static MultiSelectUiBinder uiBinder = GWT
			.create(MultiSelectUiBinder.class);

	interface MultiSelectUiBinder extends UiBinder<Widget, MultiSelect> {
	}

	@UiField
	Select select;
	
	public MultiSelect() {
		initWidget(uiBinder.createAndBindUi(this));
		select.setEnabled(true);
		
	}
	
	public MultiSelect(List<String> options)
	{
		this();
		setValue(options);
	}
	
	@Override
	public List<String> getValue()
	{
		return select.getAllSelectedValues();
	}
	
	public void deselectAll() 
	{
		select.deselectAll();
	}
	
	public void setFocus(final boolean isFocused)
	{
		select.setFocus(isFocused);
	}
	
	 public void setWidth(final String width)
	 {
		 select.setWidth(width);
	 }

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(List<String> options) {
		OptGroup group = new OptGroup();
		Option option;
		for (int i = 0; i < options.size(); i++)
		{
			
			option = new Option();
			option.setText(options.get(i));
			select.add(option);
		}
	
		
	}

	@Override
	public void setValue(List<String> options, boolean fireEvents) {
		// TODO Auto-generated method stub
		
	}


	
	

}
