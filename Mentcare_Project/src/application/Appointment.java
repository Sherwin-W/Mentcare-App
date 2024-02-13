package application;

public class Appointment {
	private String date;
	private String office;
	private String doctor;
	private String summary;
	
	public String getDate() {
		return date;
	}
	public String getOffice() {
		return office;
	}
	public String getDoctor() {
		return doctor;
	}
	public String getSummary() {
		return summary;
	}
	public String get(int i) {
		if (i == 0) {return date;}
		else if (i == 1) { return office; } 
		else if (i == 2) {return doctor; }
		else if (i == 3) { return summary; }
		else {return null; }
	}
	public Appointment(String d,String of,String doc,String sum) {
		date = d;
		office = of;
		doctor = doc;
		summary = sum;
	}
}
