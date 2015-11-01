package ch.uzh.se.se7en.client.mvp.views.widgets;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {

	@Source("Se7enLogo.png")
	ImageResource sevenWhite();
	
	@Source("skyline_3.jpg")
	ImageResource background();
	
	@Source("BackgroundText.png")
	ImageResource backgroundText();

}
