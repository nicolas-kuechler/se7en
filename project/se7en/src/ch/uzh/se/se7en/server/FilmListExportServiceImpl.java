package ch.uzh.se.se7en.server;

import java.io.BufferedWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.UUID;

import com.google.api.client.util.Base64;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.googlecode.jcsv.writer.CSVWriter;
import com.googlecode.jcsv.writer.internal.CSVWriterBuilder;

import ch.uzh.se.se7en.client.rpc.FilmListExportService;
import ch.uzh.se.se7en.client.rpc.FilmListService;
import ch.uzh.se.se7en.shared.model.Film;
import ch.uzh.se.se7en.shared.model.FilmFilter;


/**This class is used to export a list of filtered films as a csv file and delivering
 * a download link to said file to the user
 * 
 * @author vagrant
 *
 */
@Singleton
public class FilmListExportServiceImpl extends RemoteServiceServlet implements FilmListExportService {

	private final String BUCKET_NAME = "se-team-se7en";
	
	@Inject
	FilmListServiceImpl filmListService;
	
	/**
	 * exports a csv file for a set filter to Google Cloud Storage and
	 * returns a download link to said file
	 * 
	 * @author Cyrill Halter
	 * @pre a filter is set in the client data model
	 * @post -
	 * @param FilmFilter filter A filter object
	 * @return String downloadURL a URL pointing to the generated CSV file in GCS
	 */
	@Override
	public String getFilmListDownloadUrl(FilmFilter filter) {

		//create GCS service
		GcsService gcsService = GcsServiceFactory.createGcsService();
		
		//create unique file name
		String uniqueFilename = "filtered_table_" + UUID.randomUUID().toString() + ".csv";
		
		//create GCS file name
		GcsFilename filename = new GcsFilename(BUCKET_NAME, uniqueFilename);
		
		//create options for CSV file to be exported: set public read and mime-type
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
		Writer writer = new PrintWriter(Channels.newWriter(writeChannel, "UTF-8"));
		//write films to CSV
		CSVWriter<Film> csvWriter = new CSVWriterBuilder<Film>(writer).entryConverter(new FilmEntryConverter()).build();
		try {
			csvWriter.writeAll(filmListService.getFilmList(filter, 0, 80000)); //TODO RS CH check if possible to get all somehow without hardcoding it
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		//return URL of created file for download
		String downloadURL = "https://storage.googleapis.com/" + BUCKET_NAME + "/" + uniqueFilename;
		
		return downloadURL;

	}

	//TODO: add comment
	@Override
	public String getMapImageDownloadUrl(String imageURI) {
		//create GCS service
		GcsService gcsService = GcsServiceFactory.createGcsService();
		
		//create unique file name
		String uniqueFilename = "filtered_map_" + UUID.randomUUID().toString() + ".png";
		
		//create GCS file name
		GcsFilename filename = new GcsFilename(BUCKET_NAME, uniqueFilename);
		
		//create options for CSV file to be exported: set public read and mime-type
		GcsFileOptions.Builder builder = new GcsFileOptions.Builder();
		
		GcsFileOptions options = builder
				.mimeType("image/png")
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
		
		String [] parts = imageURI.split(",");
		byte[] imageBytes = Base64.decodeBase64(parts[1].getBytes());
		try {
			writeChannel.write(ByteBuffer.wrap(imageBytes));
			writeChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return URL of created file for download
		String downloadURL = "https://storage.googleapis.com/" + BUCKET_NAME + "/" + uniqueFilename;

		return downloadURL;

	}

}
