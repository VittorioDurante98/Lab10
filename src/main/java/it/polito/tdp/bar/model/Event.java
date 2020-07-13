package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Event implements Comparable<Event>{
	
	public enum EventType {
		ARRIVO, USCITA
	}
	
	private LocalDateTime time;
	private int num_persone;
	private Duration durata;
	private double tolleranza;
	private EventType type;
	private Tavolo t;
	
	public Event(LocalDateTime time, int num_persone, Duration durata, double tolleranza, EventType type) {
		this.time = time;
		this.num_persone = num_persone;
		this.durata = durata;
		this.tolleranza = tolleranza;
		this.type= type;
	}
	

	public Tavolo getT() {
		return t;
	}


	public void setT(Tavolo t) {
		this.t = t;
	}


	public EventType getType() {
		return type;
	}


	public void setType(EventType type) {
		this.type = type;
	}


	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public int getNum_persone() {
		return num_persone;
	}
	public void setNum_persone(int num_persone) {
		this.num_persone = num_persone;
	}
	public Duration getDurata() {
		return durata;
	}
	public void setDurata(Duration durata) {
		this.durata = durata;
	}
	public double getTolleranza() {
		return tolleranza;
	}
	public void setTolleranza(double tolleranza) {
		this.tolleranza = tolleranza;
	}

	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.getTime());
	}

	@Override
	public String toString() {
		return  time + " num_persone: " + num_persone + ", durata: " + durata +" ("+tolleranza+") "+type;
	}
	

}
