package util;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePropertyEditor extends PropertyEditorSupport {

	private String format;
	
	@Override
    public void setAsText(String text) throws IllegalArgumentException {  
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        try {  
            Date dateVal = sdf.parse(text);  
            this.setValue(dateVal);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
          
    }  
      
    public void setFormat(String format){  
        this.format = format;  
    }
    
    public String getFormat(){
    	return this.format;
    }
}
