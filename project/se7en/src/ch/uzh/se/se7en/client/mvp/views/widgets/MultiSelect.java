package ch.uzh.se.se7en.client.mvp.views.widgets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gwtbootstrap3.extras.select.client.ui.Option;
import org.gwtbootstrap3.extras.select.client.ui.Select;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


/**
 * This class defines a widget that adds functionality to the
 * gwtbootstrap3.extras.select widget.
 * 
 * @author Cyrill Halter
 *
 */
public class MultiSelect extends Composite {

	private static MultiSelectUiBinder uiBinder = GWT.create(MultiSelectUiBinder.class);

	interface MultiSelectUiBinder extends UiBinder<Widget, MultiSelect> {
	}

	@UiField
	Select select;

	public MultiSelect() {
		initWidget(uiBinder.createAndBindUi(this));
		select.setEnabled(true);

	}
	
	// TODO CH Verify and add comment
	public Set<Integer> getAllSelectedIds()
	{
		List<String> selected =  select.getAllSelectedValues();
		Set<Integer> ids = new HashSet<Integer>();
		for(String s : selected)
		{
			ids.add(Integer.parseInt(s));
		}
		return ids;
	}
	
	//TODO CH Write Comment
	public void select(Set<Integer> ids)
	{
		//TODO CH Write metho to select values in the multiselects
	}


	/**
	 * This method deselects all options in the multiselect widget
	 * 
	 * @author Cyrill Halter
	 * @pre -
	 * @post select.getAllSelected() == null;
	 * @param -
	 * @return -
	 */
	public void deselectAll() {
		select.deselectAll();
	}

	/**
	 * This method sets the width of the multiselect widget
	 * 
	 * @author Cyrill Halter
	 * @pre -
	 * @post -
	 * @param final
	 *            String width The width to set to
	 * @return -
	 */
	public void setWidth(final String width) {
		select.setWidth(width);
	}

	/**
	 * This method fills the multiselect widget with options to select from
	 * 
	 * @author Cyrill Halter
	 * @pre -
	 * @post -
	 * @param List<SelectOption>
	 *            currentOptions The options that should be available in the
	 *            multiselect widget
	 * @return -
	 */
	public void setOptions(HashMap<Integer,String> currentOptions) {
		Option option;
		Set<Integer> ids = currentOptions.keySet();
		for (Integer id : ids) {
			option = new Option();
			option.setText(currentOptions.get(id));
			option.setValue(id.toString());
			select.add(option);
		}
		select.refresh();
	}

}
