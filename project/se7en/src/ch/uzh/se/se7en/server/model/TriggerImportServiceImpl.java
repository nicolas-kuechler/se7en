package ch.uzh.se.se7en.server.model;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import ch.uzh.se.se7en.client.rpc.TriggerImportService;

public class TriggerImportServiceImpl extends RemoteServiceServlet implements TriggerImportService {

	@Override
	public boolean importFile(String nameOfFile) {
		// TODO Auto-generated method stub
		return true;
	}

}
