package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import java.lang.String.*;
import static java.lang.Integer.*;
import static java.lang.Double.*;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSDataConverter {

	// konverter tidsinformasjon i gps data punkt til antall sekunder fra midnatt
	// dvs. ignorer information om dato og omregn tidspunkt til sekunder
	// Eksempel - tidsinformasjon (som String): 2017-08-13T08:52:26.000Z
	// skal omregnes til sekunder (som int): 8 * 60 * 60 + 52 * 60 + 26

	private static int TIME_STARTINDEX = 11; // posisjon for start av tidspunkt i timestr

	public static int toSeconds(String timestr) {

		int secs;
		int hr, min, sec;

		hr = parseInt(timestr.substring(11, 13));
		min = parseInt(timestr.substring(14, 16));
		sec = parseInt(timestr.substring(17, 19));

		secs = hr * 60 * 60 + min * 60 + sec;
		return secs;

	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {


		GPSPoint gpspoint;
		int time = toSeconds(timeStr);
		double latitude = parseDouble(latitudeStr);
		double longitude = parseDouble(longitudeStr);
		double elevation = parseDouble(elevationStr);

		
		gpspoint = new GPSPoint(time, latitude, longitude, elevation); 
		return gpspoint; 
	}

}
