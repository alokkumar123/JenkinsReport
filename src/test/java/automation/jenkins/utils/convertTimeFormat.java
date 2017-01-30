package automation.jenkins.utils;

import java.util.Date;

public class convertTimeFormat {

    Date expiry;
    String realTime;

    public void initialiseTime(String time) {
        long epoch = Long.parseLong(time);
        expiry = new Date(epoch);
        realTime = expiry.toString();
    }

    public String returnYear() {
        return realTime.substring(realTime.length() - 4, realTime.length());
    }

    @SuppressWarnings("deprecation")
	public String returnDate() {
        return Integer.toString(expiry.getDate());
    }

    @SuppressWarnings("deprecation")
	public String returnMonth() {
        return Integer.toString(expiry.getMonth());
    }

}
