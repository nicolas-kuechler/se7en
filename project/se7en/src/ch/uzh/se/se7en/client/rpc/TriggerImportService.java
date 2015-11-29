package ch.uzh.se.se7en.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("triggerImportService")
public interface TriggerImportService extends RemoteService {
	public String importFile(String nameOfFile, String password);
}
