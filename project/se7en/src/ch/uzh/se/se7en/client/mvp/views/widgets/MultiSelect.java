package ch.uzh.se.se7en.client.mvp.views.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
	
	/**
	 * This method returns the ids of all selected options
	 * 
	 * @author Cyrill Halter
	 * @pre -
	 * @post -
	 * @param -
	 * @return Set<Integer> ids The set of all selected ids
	 */
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
	
	/**
	 * Selects a set of entries in the multiselect widget
	 * 
	 * @author Cyrill Halter
	 * @pre -
	 * @post getAllSelectedIds().contains(ids)
	 * @param Set<Integer> ids The set of all selected ids
	 * @return -
	 */
	public void select(Set<Integer> ids)
	{
		String [] idStrings = new String[ids.size()];
		int i = 0;
		for (Integer id : ids){
			idStrings[i] = id.toString();
			i++;
		}
		select.setValues(idStrings);
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
	 * This method fills the multiselect widget with sorted options to select from
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
		Set<Map.Entry<Integer, String>> currentOptionsSet = currentOptions.entrySet();
		List<Map.Entry<Integer, String>> currentOptionsList = 
				new ArrayList<Map.Entry<Integer, String>>(currentOptionsSet);
		Collections.sort(currentOptionsList, new Comparator<Map.Entry<Integer, String>>(){
		    public int compare(Map.Entry<Integer, String> entry1, Map.Entry<Integer, String> entry2) {
		    	return entry1.getValue().compareTo(entry2.getValue()); 
		    }
		});
		
		for (Map.Entry<Integer, String> entry : currentOptionsList) {
			option = new Option();
			option.setText(entry.getValue());
			option.setValue(entry.getKey().toString());
			select.add(option);
		}
		select.refresh();
	}
	

}
