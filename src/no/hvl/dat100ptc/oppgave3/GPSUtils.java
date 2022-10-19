package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max;

		max = da[0];

		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}

		return max;
	}

	public static double findMin(double[] da) {

		// finner minste tall i en tabell med flyttall. Det kan antas at der er minst et
		// element i tabellen.
		// Hint: se på implementasjonen av metoden findMax som allerede finnes i
		// klassen.

		double min;
		min = da[0];
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

//		tar en tabell med GPS punkter som parameter og returnerer en tabell av desimaltall inneholdende 
//		breddegradene for GPS-punktene.
//
//		Hint: metoden skal først opprette en tabell av desimaltall med samme lengde som gpspoints-tabellen
//		og så kopiere de enkelte breddegrader over i den nye tabellen. Husk at getLatitude-metoden på et 
//		GPSPoint-objekt kan brukes til å lese ut breddegrad i et objekt.

		double[] latitudes = new double[gpspoints.length];

		for (int i = 0; i < latitudes.length; i++) {
			latitudes[i] = gpspoints[i].getLatitude();
		}

		return latitudes;

	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] longitudes = new double[gpspoints.length];

		for (int i = 0; i < longitudes.length; i++) {
			longitudes[i] = gpspoints[i].getLongitude();
		}

		return longitudes;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;

		latitude1 = toRadians(gpspoint1.getLatitude());
		latitude2 = toRadians(gpspoint2.getLatitude());
		longitude1 = toRadians(gpspoint1.getLongitude());
		longitude2 = toRadians(gpspoint2.getLongitude());

		double a = (sin((latitude2 - latitude1) / 2) * sin((latitude2 - latitude1) / 2) + cos(latitude1)
				* cos(latitude2) * (sin((longitude2 - longitude1) / 2) * (sin((longitude2 - longitude1) / 2))));
		double c = 2 * atan2(sqrt(a), sqrt(1 - a));
		d = R * c;
		return d;

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;

//		Implementer metoden
//
//		public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {
//		som beregninger gjennomsnittshastighet i km/t om man beveger seg fra punktet gitt ved gpspoint1 til punktet gpspoint2.
//
//		Hint: Bruk metoden distance fra d) samt get-metode(r) på GPSPoint-objekt.

		int t1 = gpspoint1.getTime();
		int t2 = gpspoint2.getTime();
		secs = t2 - t1;

		double distance = distance(gpspoint1, gpspoint2);

		double ms = distance / secs;
		speed = ms * 3.6;

		return speed;

	}

	public static String formatTime(int secs) {

//		Implementer metoden
//
//		public static String formatTime(int secs)
//		som returnerer en streng på formatet hh:mm:ss der tiden i sekunder fra midnatt er gitt 
//		av parameteren secs. I strengen på formatet hh:mm:ss er hh er antall timer, mm er antall 
//		minutter og ss er antall sekunder. Videre skal metoden legge inn mellomrom foran tiden slik
//		den total lengden på strengen blir 10. Hint: se på format-metoden i String-klassen.`

		String timestr;
		String TIMESEP = ":";

		int hh, mm, ss;

		hh = secs / (60 * 60);
		mm = secs / 60 - (hh * 60);
		ss = secs - (mm * 60) - (hh * 60 * 60);

		String strhh, strmm, strss;

		strhh = String.format("%02d", hh);
		strmm = String.format("%02d", mm);
		strss = String.format("%02d", ss);
		;

		timestr = "  " + strhh + TIMESEP + strmm + TIMESEP + strss;

		return timestr;

	}

	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

//		Implementer metoden
//
//		public static String formatDouble(double d)
//		som runder av et flyttall til to desimaler, setter resultat inn i en streng og 
//		fyller på med mellomrom foran i strengen slik at lengden på strengen blir 10.

		String str;

		double roundOff = Math.round(d * 100.0) / 100.0;

		String ro = Double.toString(roundOff);
		int lengde = 10 - ro.length();
		str = "";

		for (int i = 0; i < lengde; i++) {
			str += " ";

		}
		str += ro;

		return str;

	}
}
