package ch.uzh.se.se7en.client.mvp.views.widgets;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {

	@Source("SevenBlack.png")
	ImageResource sevenBlack();
	
	@Source("backgroundNewYork.jpg")
	ImageResource backgroundSkyline();
	
	@Source("backgroundTextSubtextWhite.png")
	ImageResource backgroundText();

}
