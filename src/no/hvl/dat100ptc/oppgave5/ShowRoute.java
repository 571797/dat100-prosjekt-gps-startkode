package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;

	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);

		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon));

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {

		double ystep;

		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		ystep = MAPYSIZE / (Math.abs(maxlat - minlat));

		return ystep;
	}

	public void showRouteMap(int ybase) {
		// brukes til å finne posisjonen til gps punktet
		int sumx = 0;
		int sumy = 0;

		// Henter longitudes og latitudes data
		double[] lonArr = GPSUtils.getLongitudes(gpspoints);
		double[] latArr = GPSUtils.getLatitudes(gpspoints);

		for (int i = 0; i < gpspoints.length - 1; i++) {

			// Finner differansen mellom to etterfulgene gps punkt
			double lon = lonArr[i + 1] - lonArr[i];
			double lat = latArr[i + 1] - latArr[i];

			// Ganger differansen med xstep og ystep metodene for å få et punkt innenfor
			// skjermen
			double x = xstep() * lon;
			int x1 = (int) Math.round(x);

			double y = ystep() * lat;
			int y1 = (int) Math.round(y);

			// plusser på differansen med radius 2 på stedet hvor gpspunktet er lokalisert
			sumx += x1;
			sumy += y1;

			// Tegner sirkler med radius 2 på stedet hvor gpspunktet er lokalisert
			setColor(0, 250, 0);
			fillCircle(MARGIN + sumx, (-sumy / 2) + 120, 2);

			// Tegner en linje fra sirkelen som nettopp ble tegnet til den forrige sirkelen
			// som ble tegnet
			drawLine(MARGIN + sumx, (-sumy / 2) + 120, MARGIN + sumx - x1, ((-sumy + y1) / 2) + 120);

		}
	}

	private static double WEIGHT = 80.0;

	public void showStatistics() {

		setColor(0, 0, 0);
		setFont("Courier", 12);

		drawString("Total time     : " + GPSUtils.formatTime(gpscomputer.totalTime()), 20, 20);
		drawString("Total elevation:   " + Math.round(gpscomputer.totalElevation() * 100.0) / 100.0 + " m", 20, 40);
		drawString("Max speed      :   " + Math.round(gpscomputer.maxSpeed() * 100.0) / 100.0 + " km/t", 20, 60);
		drawString("Average speed  :   " + Math.round(gpscomputer.averageSpeed() * 100.0) / 100.0 + " km/t", 20, 80);
		drawString("Energy         :   " + Math.round(gpscomputer.totalKcal(WEIGHT) * 100.0) / 100.0 + " kcal", 20,
				100);

	}

}
