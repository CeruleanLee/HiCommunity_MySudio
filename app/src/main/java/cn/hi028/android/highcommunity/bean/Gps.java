package cn.hi028.android.highcommunity.bean;

import java.io.Serializable;

public class Gps implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double wgLat;
	private double wgLon;

	public Gps() {
		super();
	}  

	public Gps(double wgLat, double wgLon) {
		setWgLat(wgLat);
		setWgLon(wgLon);
	}

	public double getWgLat() {
		return wgLat;
	}

	public void setWgLat(double wgLat) {
		this.wgLat = wgLat;
	}

	public double getWgLon() {
		return wgLon;
	}

	public void setWgLon(double wgLon) {
		this.wgLon = wgLon;
	}

	@Override
	public String toString() {
		return wgLat + "," + wgLon;
	}
}
