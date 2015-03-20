package classes;
public class MailInfo{
		
		public final String header, from, date, status, to;
		public final int eventId;
		
		public MailInfo(String header, String from, String to, String date, String status, int eventId){
			this.header = header;
			this.from = from;
			this.date = date;
			this.to = to;
			this.status = status;
			this.eventId = eventId;
		}
	}