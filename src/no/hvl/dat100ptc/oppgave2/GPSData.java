package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int n) {
		this.gpspoints = new GPSPoint[n];
		antall = 0;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	protected boolean insertGPS(GPSPoint gpspoint) {

//		protected boolean insertGPS(GPSPoint gpspoint) som setter inn GPS punktet gpspoint i gpspoints-tabellen på 
//		posisjonen angitt ved objektvariablen antall. Videre skal metoden inkrementere antall slik neste punkt kommmer 
//		inn på neste posisjon. Metoden skal kun sette inn gpspoint om der er plass i tabellen dvs. hvis antall er strengt 
//		mindre enn gpspoints.length. Metoden skal returnere true om punktet blev satt inn og false ellers.

		boolean inserted = false;

		if (antall < gpspoints.length) {
			gpspoints[antall] = gpspoint;
			antall++;
			inserted = true;
		}

		return inserted;
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {

		GPSPoint gpspoint = GPSDataConverter.convert(time, latitude, longitude, elevation);
		boolean inserted = insertGPS(gpspoint); 
		return inserted; 
	}

	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");

		for (GPSPoint i : gpspoints) {
			System.out.println(i.toString());
		}

		System.out.println("====== Konvertert GPS Data - SLUTT ======");

	}
}
