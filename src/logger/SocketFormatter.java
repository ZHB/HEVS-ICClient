package logger;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class SocketFormatter extends Formatter {
	public SocketFormatter() {
		super();
	}

	public String format(LogRecord record) {
		
		// Create a StringBuffer to contain the formatted record
		StringBuffer sb = new StringBuffer();
				
		// Get the date from the LogRecord and add it to the buffer
		Date date = new Date(record.getMillis());
		sb.append(date.getTime());
		sb.append(";");
		
		sb.append(record.getSourceClassName());
		sb.append(";");
		
		// Get the level name and add it to the buffer
		sb.append(record.getLevel().getName());
		sb.append(";");
		 
		sb.append(formatMessage(record));
		sb.append("\r\n");

		return sb.toString();
	}
}
