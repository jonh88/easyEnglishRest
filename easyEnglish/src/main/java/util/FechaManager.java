package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FechaManager {

	private Date c;
	
	public FechaManager(){
		c = new Date();		
	}
		
	public int getNow(){					
		SimpleDateFormat formateador = new SimpleDateFormat("yyyyMMdd");
		String f = formateador.format(this.c);
		return Integer.parseInt(f);
	}
	

}
