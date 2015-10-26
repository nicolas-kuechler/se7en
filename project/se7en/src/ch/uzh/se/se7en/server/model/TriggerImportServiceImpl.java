package ch.uzh.se.se7en.server.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.channels.Channels;
import java.util.List;

import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.jcsv.annotations.internal.ValueProcessorProvider;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.AnnotationEntryParser;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

import ch.uzh.se.se7en.client.rpc.TriggerImportService;

import ch.uzh.se.se7en.shared.model.Film;

public class TriggerImportServiceImpl extends RemoteServiceServlet implements TriggerImportService {

	@Override
	public boolean importFile(String nameOfFile) {
		GcsService gcsService = GcsServiceFactory.createGcsService();
		GcsFilename gcsFilename = new GcsFilename("se-team-se7en", nameOfFile);
		List<Film> importedFilms = null;
		
		try{
			
			GcsInputChannel csvReadChannel = gcsService.openReadChannel(gcsFilename, 0);
			Reader csvFileReader = new InputStreamReader(Channels.newInputStream(csvReadChannel));
			
			ValueProcessorProvider vpp = new ValueProcessorProvider();
			CSVReader<Film> filmReader = new CSVReaderBuilder<Film>(csvFileReader).
					entryParser(new AnnotationEntryParser<Film>(Film.class, vpp)).build();
			importedFilms = filmReader.readAll();
			
		}catch (IOException e){
			e.printStackTrace();
			return false;
		}
		
		//insert method for adding films to DB here
		return true;
	}

}
