package application;

import java.sql.Timestamp;

public class user_model {

	private String psu_id;
	private boolean user_status;
	private String user_role;
	private Timestamp entry_time;

	@Override
	public String toString() {
		return "user_model [psu_id=" + psu_id + ", user_status=" + user_status + ", user_role=" + user_role + "]";
	}



	public user_model(String psu_id, boolean user_status, String user_role, Timestamp entry_time) {
		super();
		this.psu_id = psu_id;
		this.user_status = user_status;
		this.user_role = user_role;
		this.entry_time = entry_time;
	}



	public String getPsu_id() {
		return psu_id;
	}

	public void setPsu_id(String psu_id) {
		this.psu_id = psu_id;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public boolean isUser_status() {
		return user_status;
	}

	public void setUser_status(boolean user_status) {
		this.user_status = user_status;
	}

	public Timestamp getEntry_time() {
		return entry_time;
	}

	public void setEntry_time(Timestamp entry_time) {
		this.entry_time = entry_time;
	}

}
