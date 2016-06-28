package bookingTable;

import android.app.Application;

public class GlobalVariableNumSeats extends Application{
	private int numSeats;
	
	public void setNumSeats(int nNumSeats) {
		numSeats = nNumSeats;
	}
	
	public int getNumSeats() {
		return numSeats;
	}
}
