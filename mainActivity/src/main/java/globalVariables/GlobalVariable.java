package globalVariables;

import android.app.Application;

public class GlobalVariable extends Application{
	public static int numSeats;
	public static String customerUserName="";
	public static int tableNumber=0;
	public static double totalDue=0;

	public static void setNumSeats(int nNumSeats) {
		numSeats = nNumSeats;
	}

	public static int getNumSeats() {
		return numSeats;
	}
	public static void setCustomerUserName(String name) {
		customerUserName = name;
	}
	public static String getCustomerUserName() {
		return customerUserName;
	}
	public static void setTableNumber(int number) {
		tableNumber = number;
	}
	public static int getTableNumber() {
		return tableNumber;
	}
	public static void setTotalDue(double total) {
		totalDue = total;
	}
	public static double getTotalDue() {
		return totalDue;
	}
}