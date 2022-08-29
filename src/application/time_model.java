package application;

import java.sql.Timestamp;

public class time_model {

	private int id;
	private String psu_id;
	private Timestamp entry_time;
	private Timestamp departure_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public time_model(int id, String psu_id, Timestamp entry_time, Timestamp departure_time) {
		this.id = id;
		this.psu_id = psu_id;
		this.entry_time = entry_time;
		this.departure_time = departure_time;
	}

	public String getPsu_id() {
		return psu_id;
	}

	public void setPsu_id(String psu_id) {
		this.psu_id = psu_id;
	}

	public Timestamp getEntry_time() {
		return entry_time;
	}

	public void setEntry_time(Timestamp entry_time) {
		this.entry_time = entry_time;
	}

	public Timestamp getDeparture_time() {
		return departure_time;
	}

	public void setDeparture_time(Timestamp departure_time) {
		this.departure_time = departure_time;
	}

	@Override
	public String toString() {
		return "time_model [id=" + id + ", psu_id=" + psu_id + ", entry_time=" + entry_time + ", departure_time="
				+ departure_time + "]";
	}
}
