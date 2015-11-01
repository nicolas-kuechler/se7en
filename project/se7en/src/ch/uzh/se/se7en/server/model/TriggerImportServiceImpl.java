package ch.uzh.se.se7en.server.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.googlecode.jcsv.annotations.MapToColumn;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import com.googlecode.jcsv.annotations.internal.ValueProcessorProvider;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.AnnotationEntryParser;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;

import ch.uzh.se.se7en.client.rpc.TriggerImportService;
import ch.uzh.se.se7en.shared.model.Film;

/**
 * Used to import film data from csv files in the Google Cloud Storage file
 * repository
 * 
 * @author Cyrill Halter, Roland Schläfli
 *
 */
@Singleton
public class TriggerImportServiceImpl extends RemoteServiceServlet implements TriggerImportService {
	@Inject
	Provider<EntityManager> em;

	/**
	 * This method is called to to import a file from the Google Cloud Storage
	 * file repository and convert it to a list of film objects
	 * 
	 * @author Cyrill Halter
	 * @pre A correctly formatted csv file has been deposited in the Cloud
	 *      Storage
	 * @post -
	 * @param String
	 *            nameOfFile The name of the csv file that should be imported
	 */
	@Override
	public boolean importFile(String nameOfFile) {
		GcsService gcsService = GcsServiceFactory.createGcsService();
		GcsFilename gcsFilename = new GcsFilename("se-team-se7en", nameOfFile);
		List<Film> importedFilms = new LinkedList<Film>();

		try {
			
			//open GCS channel for specified file name and create reader
			GcsInputChannel csvReadChannel = gcsService.openReadChannel(gcsFilename, 0);
			Reader csvFileReader = new InputStreamReader(Channels.newInputStream(csvReadChannel));
			
			//create csv reader on inputstream reader
			ValueProcessorProvider vpp = new ValueProcessorProvider();
			CSVReader<FilmHelper> filmReader = new CSVReaderBuilder<FilmHelper>(csvFileReader)
					.entryParser(new AnnotationEntryParser<FilmHelper>(FilmHelper.class, vpp)).build();
			

			//read csv to FilmHelper objects, convert them to Film objects and add them to the importedFilms List
			FilmHelper tempFilm;
			while((tempFilm = filmReader.readNext()) != null){
				importedFilms.add(new Film(tempFilm.getName(), tempFilm.getLength(), tempFilm.getYear(),
						new ArrayList<String>(Arrays.asList(tempFilm.getCountries().split("--"))),
						new ArrayList<String>(Arrays.asList(tempFilm.getLanguages().split("--"))),
						new ArrayList<String>(Arrays.asList(tempFilm.getGenres().split("--")))));
			}


		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// import the films into the db
		if (importFilmsToDB(importedFilms)) {
			return true;
		}
		;

		return false;
	}

	/**
	 * Imports the parsed films into the database
	 * 
	 * @author Roland Schläfli
	 * @pre -
	 * @post -
	 * @param List<Film>
	 *            films A list of film objects to import
	 * @return boolean Whether the import was successfully pushed to the
	 *         database
	 */
	@Transactional
	public boolean importFilmsToDB(List<Film> films) {
		// TODO: update for new DB structure
		
		// add each film to the transaction
		for (Film film : films) {
			em.get().persist(film);
		}

		// TODO: return a real success / error bool
		return true;
	}


}
