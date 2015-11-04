package ch.uzh.se.se7en.client.mvp.views.widgets;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.extras.select.client.ui.Option;
import org.gwtbootstrap3.extras.select.client.ui.Select;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import ch.uzh.se.se7en.shared.model.SelectOption;


public class MultiSelect extends Composite{

	private static MultiSelectUiBinder uiBinder = GWT
			.create(MultiSelectUiBinder.class);

	interface MultiSelectUiBinder extends UiBinder<Widget, MultiSelect> {
	}

	@UiField
	Select select;
	
	private List<SelectOption> currentOptions;
	
	public MultiSelect() {
		initWidget(uiBinder.createAndBindUi(this));
		select.setEnabled(true);
		
	}
	
	public List<SelectOption> getOptions()
	{
		List<String> ids = select.getAllSelectedValues();
		List<SelectOption> selectedOptions = new ArrayList<SelectOption>();
		for(String id : ids){
			int i = 0;
			SelectOption tempOption = currentOptions.get(i);
			while(Integer.parseInt(id) != tempOption.getId()){
				tempOption = currentOptions.get(++i);
			}
			selectedOptions.add(tempOption);
		}
		return selectedOptions;
	}
	
	public List<String> getValue(){
		List<String> ids = select.getAllSelectedValues();
		List<String> selectedOptions = new ArrayList<String>();
		for(String id : ids){
			int i = 0;
			SelectOption tempOption = currentOptions.get(i);
			while(Integer.parseInt(id) != tempOption.getId()){
				tempOption = currentOptions.get(++i);
			}
			selectedOptions.add(tempOption.getName());
		}
		return selectedOptions;
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


	public void setOptions(List<SelectOption> currentOptions) {
		Option option;
		this.currentOptions = currentOptions;
		
		for (int i = 0; i < currentOptions.size(); i++)
		{
			option = new Option();
			option.setText(currentOptions.get(i).getName());
			option.setValue(Integer.toString(currentOptions.get(i).getId()));
			select.add(option);
		}
		
		select.refresh();
	
		
	}



	
	

}
