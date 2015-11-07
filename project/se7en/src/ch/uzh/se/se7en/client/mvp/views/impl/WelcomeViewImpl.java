package ch.uzh.se.se7en.client.mvp.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import ch.uzh.se.se7en.client.mvp.presenters.WelcomePresenter;
import ch.uzh.se.se7en.client.mvp.views.WelcomeView;

public class WelcomeViewImpl extends Composite implements WelcomeView {

	private static WelcomeViewImplUiBinder uiBinder = GWT.create(WelcomeViewImplUiBinder.class);

	interface WelcomeViewImplUiBinder extends UiBinder<Widget, WelcomeViewImpl> {
	}

	private WelcomePresenter welcomePresenter;

	/**
	 * Iniialize the welcome view
	 * 
	 * @author Dominik Bünzli
	 * @pre container != null
	 * @post -
	 * @param -
	 */
	@Inject
	public WelcomeViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Set the presenter for the welcome view
	 * 
	 * @author Dominik Bünzli
	 * @pre container != null
	 * @post -
	 * @param presenter
	 */
	@Override
	public void setPresenter(WelcomePresenter presenter) {
		this.welcomePresenter = presenter;
	}

}
