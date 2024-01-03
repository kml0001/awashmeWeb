package cu.edu.cujae.backend.core.util;

import java.sql.Date;

public class date_string_converter {


	
	public static Date dateToString(String date) {
		Date r = null;
		if(date != null) {
			
			try {
				r = Date.valueOf(date);
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
		return r;
	}
	
	
}
