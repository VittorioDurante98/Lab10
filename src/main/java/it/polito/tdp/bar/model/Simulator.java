package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.bar.model.Event.EventType;


public class Simulator {
	
	private PriorityQueue<Event> queue;
	private List<Tavolo> tavoli4;
	private List<Tavolo> tavoli6;
	private List<Tavolo> tavoli8;
	private List<Tavolo> tavoli10;
	/*private Tavolo t4;
	private Tavolo t6;
	private Tavolo t8;
	private Tavolo t10;*/
	private Duration time;
	private LocalDateTime oraApertura = LocalDateTime.of(2014, 10, 4, 8, 0);
	private LocalTime oraChiusura = LocalTime.of(19, 00);
	private int cnt;
	
	private int clienti;
	private int insoddisfatti;
	private int bancone;
	//private Tavolo preso
	
	public Simulator() {
		this.queue = new PriorityQueue<>();
		this.tavoli4= new ArrayList<>();
		this.tavoli6= new ArrayList<>();
		this.tavoli8= new ArrayList<>();
		this.tavoli10= new ArrayList<>();
		/*this.t4 = new Tavolo(5,4);
		this.t6 = new Tavolo(4,6);
		this.t8 = new Tavolo(4,8);
		this.t10 = new Tavolo(2,10);
		this.tavoli.add(t10);
		this.tavoli.add(t4);
		this.tavoli.add(t6);
		this.tavoli.add(t8);*/
		this.clienti=0;
		this.insoddisfatti=0;
		this.cnt=0;
		this.bancone=0;
	}
	
	private void creaTavoli() {
		for (int i = 0; i < 5; i++) {
			this.tavoli4.add(new Tavolo(4));
		}
		for (int i = 0; i < 4; i++) {
			this.tavoli6.add(new Tavolo(6));
		}
		for (int i = 0; i < 4; i++) {
			this.tavoli8.add(new Tavolo(8));
		}
		for (int i = 0; i < 2; i++) {
			this.tavoli10.add(new Tavolo(10));
		}
	}
	

	public void run() {
		LocalDateTime oraArrivo = this.oraApertura;
		Event e;
		Random r = new Random();
		int max = 121;
		int min = 60;
		//System.out.print(oraArrivo+"\n");
		do {
			int numPersone= (int) ((Math.random()+0.1)*10);
			Duration durata = Duration.of(r.nextInt(max-min)+min, ChronoUnit.MINUTES);
			e= new Event(oraArrivo, numPersone, durata, Math.random(), EventType.ARRIVO);
			queue.add(e);
			time = Duration.of((int)((Math.random()+0.1)*10), ChronoUnit.MINUTES);
			oraArrivo=oraArrivo.plus(time);
			cnt ++;
			//System.out.println(e.toString());
		} while (cnt<2 );
		
		while (!this.queue.isEmpty()) {
			Event x = this.queue.poll();
			System.out.println(x);
			process(x);
		}
		System.out.println("Clienti: "+this.clienti+", insoddisfatti: "+this.insoddisfatti);
		
	}	
	
	//RIVEDERE IL CASO TAVOLO DA 4
	public void process(Event e) {
		this.creaTavoli();
		
		switch (e.getType()) {
		case ARRIVO:
			
			if (e.getNum_persone()==1) {
				if (e.getTolleranza()>=0.5) {
					clienti++;
					this.bancone++;
				}else
					this.insoddisfatti++;
			}
			if (e.getNum_persone()==2) {
				if (tavoli4.isEmpty()) {
					if (e.getTolleranza()>=0.5) {
						clienti+=2;
						this.bancone++;
					}else
						this.insoddisfatti++;
				}else {
					clienti+=2;
					e.setT(tavoli4.get(tavoli4.size()-1));
					tavoli4.remove(tavoli4.size()-1);
					Event u= new Event(e.getTime().plus(e.getDurata()), e.getNum_persone(), e.getDurata(), e.getTolleranza(), EventType.USCITA);
					u.setT(new Tavolo(4));
					queue.add(u);
				}
			}
			if (e.getNum_persone()==3) {
				if (tavoli4.isEmpty()) {
					if (tavoli6.isEmpty()) {
						if (e.getTolleranza()>=0.5) {
							clienti++;
							this.bancone++;
						}else
							this.insoddisfatti++;
					}else {
						clienti+=3;
						e.setT(tavoli6.get(tavoli6.size()-1));
						tavoli6.remove(tavoli6.size()-1);
						Event u= new Event(e.getTime().plus(e.getDurata()), e.getNum_persone(), e.getDurata(), e.getTolleranza(), EventType.USCITA);
						u.setT(new Tavolo(6));
						queue.add(u);
					}
				}else {
					clienti+=3;
					e.setT(tavoli4.get(tavoli4.size()-1));
					tavoli4.remove(tavoli4.size()-1);
					Event u= new Event(e.getTime().plus(e.getDurata()), e.getNum_persone(), e.getDurata(), e.getTolleranza(), EventType.USCITA);
					u.setT(new Tavolo(4));
					queue.add(u);
				}
			}
			if (e.getNum_persone()==4) {
				if (tavoli4.isEmpty()) {
					if (tavoli6.isEmpty()) {
						if (tavoli8.isEmpty()) {
							if (e.getTolleranza()>=0.5) {
								clienti+=4;
								this.bancone++;
							}else
								this.insoddisfatti++;
						}else {
							clienti+=4;
							e.setT(tavoli8.get(tavoli8.size()-1));
							tavoli8.remove(tavoli8.size()-1);
							Event u= new Event(e.getTime().plus(e.getDurata()), e.getNum_persone(), e.getDurata(), e.getTolleranza(), EventType.USCITA);
							u.setT(new Tavolo(8));
							queue.add(u);
						}
					}else {
						clienti+=4;
						e.setT(tavoli6.get(tavoli6.size()-1));
						tavoli6.remove(tavoli6.size()-1);
						Event u= new Event(e.getTime().plus(e.getDurata()), e.getNum_persone(), e.getDurata(), e.getTolleranza(), EventType.USCITA);
						u.setT(new Tavolo(6));
						queue.add(u);
					}
				}else {
					clienti+=4;
					e.setT(tavoli4.get(tavoli4.size()-1));
					tavoli4.remove(tavoli4.size()-1);
					Event u= new Event(e.getTime().plus(e.getDurata()), e.getNum_persone(), e.getDurata(), e.getTolleranza(), EventType.USCITA);
					u.setT(new Tavolo(4));
					queue.add(u);
				}	
			}
			if (e.getNum_persone()<=6) {
				if (tavoli6.isEmpty()) {
					if (tavoli8.isEmpty()) {
						if (tavoli10.isEmpty()) {
							if (e.getTolleranza()>=0.5) {
								clienti+=e.getNum_persone();
								this.bancone++;
							}else
								this.insoddisfatti++;
						}else {
							clienti+=e.getNum_persone();
							e.setT(tavoli10.get(tavoli10.size()-1));
							tavoli10.remove(tavoli10.size()-1);
							Event u= new Event(e.getTime().plus(e.getDurata()), e.getNum_persone(), e.getDurata(), e.getTolleranza(), EventType.USCITA);
							u.setT(new Tavolo(10));
							queue.add(u);
						}
					}else {
						clienti+=e.getNum_persone();
						e.setT(tavoli8.get(tavoli8.size()-1));
						tavoli8.remove(tavoli8.size()-1);
						Event u= new Event(e.getTime().plus(e.getDurata()), e.getNum_persone(), e.getDurata(), e.getTolleranza(), EventType.USCITA);
						u.setT(new Tavolo(8));
						queue.add(u);
					}
				}else {
					clienti+=e.getNum_persone();
					e.setT(tavoli6.get(tavoli6.size()-1));
					tavoli6.remove(tavoli6.size()-1);
					Event u= new Event(e.getTime().plus(e.getDurata()), e.getNum_persone(), e.getDurata(), e.getTolleranza(), EventType.USCITA);
					u.setT(new Tavolo(6));
					queue.add(u);
				}
			}
			if (e.getNum_persone()<=8) {
				if (tavoli8.isEmpty()) {
					if (tavoli10.isEmpty()) {
						if (e.getTolleranza()>=0.5) {
							clienti+=e.getNum_persone();
							this.bancone++;
						}else
							this.insoddisfatti++;
					}else {
						clienti+=e.getNum_persone();
						e.setT(tavoli10.get(tavoli10.size()-1));
						tavoli10.remove(tavoli10.size()-1);
						Event u= new Event(e.getTime().plus(e.getDurata()), e.getNum_persone(), e.getDurata(), e.getTolleranza(), EventType.USCITA);
						u.setT(new Tavolo(10));
						queue.add(u);
					}
				}else {
					clienti+=e.getNum_persone();
					e.setT(tavoli8.get(tavoli8.size()-1));
					tavoli8.remove(tavoli8.size()-1);
					Event u= new Event(e.getTime().plus(e.getDurata()), e.getNum_persone(), e.getDurata(), e.getTolleranza(), EventType.USCITA);
					u.setT(new Tavolo(8));
					queue.add(u);
				}
			}
			else {
				if (tavoli10.isEmpty()) {
					if (e.getTolleranza()>=0.5) {
						clienti+=e.getNum_persone();
						this.bancone++;
					}else
						this.insoddisfatti++;
				}else {
					clienti+=e.getNum_persone();
					e.setT(tavoli10.get(tavoli10.size()-1));
					tavoli10.remove(tavoli10.size()-1);
					Event u= new Event(e.getTime().plus(e.getDurata()), e.getNum_persone(), e.getDurata(), e.getTolleranza(), EventType.USCITA);
					u.setT(new Tavolo(10));
					queue.add(u);
				}
			}
			
			break;

		case USCITA:
			switch (e.getT().getNumeroPosti()) {
			case 4:
				tavoli4.add(new Tavolo(4));
				break;
			case 6:
				tavoli6.add(new Tavolo(6));
				break;
			case 8:
				tavoli8.add(new Tavolo(8));
				break;
			case 10:
				tavoli10.add(new Tavolo(10));
				break;
			}
			
			break;
	}
	
	}
}
