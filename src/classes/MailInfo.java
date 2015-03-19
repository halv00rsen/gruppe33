package classes;
public class MailInfo{
		
		public final String header, from, date, status;
		public final int eventId;
		
		public MailInfo(String header, String from, String date, String status, int eventId){
			this.header = header;
			this.from = from;
			this.date = date;
			this.status = status;
			this.eventId = eventId;
		}
	}