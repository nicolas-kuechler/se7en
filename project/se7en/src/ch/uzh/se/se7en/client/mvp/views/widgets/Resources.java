package ch.uzh.se.se7en.client.mvp.views.widgets;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {

	@Source("SevenBlack.png")
	ImageResource sevenBlack();
	
	@Source("backgroundNewYorkSmall.png")
	ImageResource backgroundSkyline();
	
	@Source("backgroundTextSubtextWhite.png")
	ImageResource backgroundText();
	
	@Source("banana.gif")
	ImageResource adImage();
	
	@Source("download.gif")
	ImageResource mapLoading();

}
