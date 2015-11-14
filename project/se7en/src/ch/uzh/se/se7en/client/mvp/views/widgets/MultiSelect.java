package ch.uzh.se.se7en.client.mvp.views.widgets;

import java.util.ArrayList;
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

import ch.uzh.se.se7en.shared.model.SelectOption;

//TODO Cyrill: Missing method to set the current selectedOptions. Necessary for the filter parsing from the url.
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

	private List<SelectOption> currentOptions;

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
	
// TODO CH Remove because not used anymore
//	/**
//	 * This method delivers a list of SelectOption objects according to what was
//	 * selected by the user in the multiselect widget
//	 * 
//	 * @author Cyrill Halter
//	 * @pre -
//	 * @post -
//	 * @param -
//	 * @return List<SelectOption> selectedOption A list of selected Options as
//	 *         SelectOption objects
//	 */
//	public List<SelectOption> getSelectedOptions() {
//		List<String> ids = select.getAllSelectedValues();
//		List<SelectOption> selectedOptions = new ArrayList<SelectOption>();
//		for (String id : ids) {
//			int i = 0;
//			SelectOption tempOption = currentOptions.get(i);
//			while (Integer.parseInt(id) != tempOption.getId()) {
//				tempOption = currentOptions.get(++i);
//			}
//			selectedOptions.add(tempOption);
//		}
//		return selectedOptions;
//	}

//	/**
//	 * This method delivers a list of strings according to what was selected by
//	 * the user in the multiselect widget. This has to be done for integration
//	 * with the existing code.
//	 * 
//	 * @author Cyrill Halter
//	 * @pre -
//	 * @post -
//	 * @param -
//	 * @return List<Strings> selectedOption A list of selected Options as
//	 *         strings
//	 */ //TODO CH Decide if Necessary?
//	public List<String> getValue() {
//		List<String> ids = select.getAllSelectedValues();
//		List<String> selectedOptions = new ArrayList<String>();
//		for (String id : ids) {
//			int i = 0;
//			SelectOption tempOption = currentOptions.get(i);
//			while (Integer.parseInt(id) != tempOption.getId()) {
//				tempOption = currentOptions.get(++i);
//			}
//			selectedOptions.add(tempOption.getName());
//		}
//		return selectedOptions;
//	}

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
	public void setOptions(List<SelectOption> currentOptions) {
		Option option;
		this.currentOptions = currentOptions;

		for (int i = 0; i < currentOptions.size(); i++) {
			option = new Option();
			option.setText(currentOptions.get(i).getName());
			option.setValue(Integer.toString(currentOptions.get(i).getId()));
			select.add(option);
		}
		select.refresh();
	}

}
