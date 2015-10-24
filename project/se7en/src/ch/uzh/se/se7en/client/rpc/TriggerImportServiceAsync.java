package ch.uzh.se.se7en.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TriggerImportServiceAsync {

	void importFile(String nameOfFile, AsyncCallback<Boolean> callback);

}
