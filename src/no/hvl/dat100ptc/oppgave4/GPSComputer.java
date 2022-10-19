package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	// beregn total distances (i meter)
	public double totalDistance() {

//		Implementer metoden
//
//		public double totalDistance()
//		som beregner den totale distansen på ruten som GPS dataene i gpspoints-tabellen angir. 
//		Dvs. metoden må legge sammen avstanden (distanser) mellom de punktene som utgjør ruten.

		double distance = 0;
		for (int i = 0; i < gpspoints.length - 1; i++) {
			distance += GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
		}

		return distance;

	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

//		Implementer metoden
//
//		public double totalElevation()
//		som beregner det totale antall høydemeter på ruten. Husk kun å telle 
//		høydemeter mellom to punkter om en beveger seg oppover.

		double elevation = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {

			if (gpspoints[i].getElevation() < gpspoints[i + 1].getElevation()) {
				elevation += gpspoints[i + 1].getElevation() - gpspoints[i].getElevation();
			}

		}
		return elevation;

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {

		int sistepunkt = gpspoints.length - 1;

		int totalTime = gpspoints[sistepunkt].getTime() - gpspoints[0].getTime();

		return totalTime;
	}

	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {

		double[] hastighet = new double[gpspoints.length - 1];

		for (int i = 0; i < gpspoints.length - 1; i++) {
			hastighet[i] = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
		}
		return hastighet;

	}

	public double maxSpeed() {

		double maxspeed = 0;
		double midlertidig;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			midlertidig = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);

			if (midlertidig > maxspeed) {
				maxspeed = midlertidig;
			}
		}
		return maxspeed;

	}

	public double averageSpeed() {

		double average = 0;

		average = (totalDistance() / totalTime()) * 3.6;
		return average;

	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling, general
	 * 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0 bicycling,
	 * 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9 mph, racing or
	 * leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph, racing/not drafting
	 * or >19 mph drafting, very fast, racing general 12.0 bicycling, >20 mph,
	 * racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;
		double speedmph = speed * MS;

		double[] metArr = { 0, 6.0, 8.0, 10.0, 12.0, 16.0 };
		double[] mphArr = { 10, 12, 14, 16, 20 };

		for (int i = 1; i < mphArr.length; i++) {
			if (speedmph >= mphArr[i - 1] && speedmph < mphArr[i]) {
				met = metArr[i + 1];
			}
		}

		// Sjekker første og siste element i met arrayet
		if (speedmph < 10) {
			met = 4;
		} else if (speedmph > 20) {
			met = 16;
		}

		kcal = weight * met * secs / (60 * 60);
		return kcal;

	}

	public double totalKcal(double weight) {

		int time = totalTime();
		double speed = averageSpeed();

		double totalkcal;

		totalkcal = kcal(weight, time, speed);

		return totalkcal;

	}

	private static double WEIGHT = 80.0;

	public void displayStatistics() {

		System.out.println("==============================================");

		System.out.println("Total time     : " + GPSUtils.formatTime(totalTime()));
		System.out.println("Total distance :   " + Math.round((totalDistance() / 1000) * 100.0) / 100.0 + " km");
		System.out.println("Total elevation:   " + Math.round(totalElevation() * 100.0) / 100.0 + " m");
		System.out.println("Max speed      :   " + Math.round(maxSpeed() * 100.0) / 100.0 + " km/t");
		System.out.println("Average speed  :   " + Math.round(averageSpeed() * 100.0) / 100.0 + " km/t");
		System.out.println("Energy         :   " + Math.round(totalKcal(WEIGHT) * 100.0) / 100.0 + " kcal");

		System.out.println("==============================================");

	}

}
