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
/**
 * Used to import film data from csv files in the Google Cloud Storage file repository
 * @author Cyrill Halter
 *
 */
public class TriggerImportServiceImpl extends RemoteServiceServlet implements TriggerImportService {
	
	/**
	 * This method is called to to import a file from the Google Cloud Storage file repository and
	 * convert it to a list of film objects
	 *@author Cyrill Halter
	 *@pre	A correctly formatted csv file has been deposited in the Cloud Storage
	 *@post -
	 *@param String nameOfFile The name of the csv file that should be imported
	 */
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
