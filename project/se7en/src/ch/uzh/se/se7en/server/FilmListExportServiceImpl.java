package ch.uzh.se.se7en.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.channels.Channels;
import java.util.UUID;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Singleton;
import com.googlecode.jcsv.writer.CSVWriter;
import com.googlecode.jcsv.writer.internal.CSVWriterBuilder;

import ch.uzh.se.se7en.client.rpc.FilmListExportService;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;

@Singleton
public class FilmListExportServiceImpl extends RemoteServiceServlet implements FilmListExportService {

	private final String BUCKET_NAME = "se-team-se7en";
	@Override
	public String getFilmListDownloadUrl(FilmFilter filter) {
		//create FilmListService
		FilmListServiceImpl filmListService = new FilmListServiceImpl();
		
		//create GCS service
		GcsService gcsService = GcsServiceFactory.createGcsService();
		
		//create unique file name
		String uniqueFilename = "filtered_table_" + UUID.randomUUID().toString() + ".csv";
		
		//create GCS file name
		GcsFilename filename = new GcsFilename(BUCKET_NAME, uniqueFilename);
		
		//create options for CSV file to be exported
		GcsFileOptions.Builder builder = new GcsFileOptions.Builder();
		
		GcsFileOptions options = builder
				.mimeType("text/csv")
				.acl("public-read")
				.build();
		
		//open channel for writing to file
		GcsOutputChannel writeChannel = null;
		try {
			writeChannel = gcsService.createOrReplace(filename, options);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		BufferedWriter writer = new BufferedWriter(Channels.newWriter(writeChannel, "UTF-8"));
		
		//write films to CSV
		CSVWriter<Film> csvWriter = new CSVWriterBuilder<Film>(writer).entryConverter(new FilmEntryConverter()).build();
		try {
			csvWriter.writeAll(filmListService.getFilmList(filter));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		String downloadURL = "https://storage.googleapis.com/" + BUCKET_NAME + uniqueFilename;
		
		return downloadURL;
	}

}
