//package database;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Hashtable;
//import java.util.List;
//import java.util.Map;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//
//import no.xkal.business.IAlarm;
//import no.xkal.business.IEvent;
//import no.xkal.business.IGroup;
//import no.xkal.business.IGroupInvitation;
//import no.xkal.business.IIsInvitedTo;
//import no.xkal.business.IPerson;
//import no.xkal.business.IRoom;
//import no.xkal.entities.Alarm;
//import no.xkal.entities.Event;
//import no.xkal.entities.Group;
//import no.xkal.entities.GroupInvitation;
//import no.xkal.entities.IsInvitedTo;
//import no.xkal.entities.Person;
//import no.xkal.entities.Room;
//
//public class ConnectionEJB {
//	
//	private static boolean DEBUG = false;
//
//	
//	////
//	// JPA
//	
//	@SuppressWarnings("null")
//	public static Map<String, String> getUserInfo(String username) {
//		
//		IPerson personBean = null;
//		Map<String, String> info = new HashMap<String, String>();
//		
//		Person user = new Person();
//		Person p = new Person();		
//		p.setUsername(username);
//		
//		try{
//			user = personBean.findPerson(p);			
//				
//			info.put("username", user.getUsername());
//			info.put("password", user.getPassword());
//			info.put("firstname", user.getFornavn());
//			info.put("lastname", user.getEtternavn());
//			info.put("email", user.getEmail());
//			info.put("phone", user.getPhone());
//			info.put("isAdmin", String.valueOf(user.getIsAdmin()));
//			
//			return info;
//
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return null;
//		}
//		
//	}
//	
//	public static ArrayList<HashMap<String, String>> getUsers(){
//		
//		IPerson personBean = null;		
//		ArrayList<HashMap<String, String>> allUsers = new ArrayList<HashMap<String, String>>();
//		List<Person> persons = new ArrayList<Person>();
//			
//			try {
//				personBean = personLookup();
//				persons = personBean.getPersons();
//								
//				for (Person p : persons) {					
//					HashMap<String, String> users = new HashMap<String, String>();
//					users.put("username", p.getUsername());
//					users.put("firstName", p.getFornavn());
//					users.put("lastName", p.getEtternavn());
//					allUsers.add(users);					
//				}
//				
//			} catch (Exception e) {
//				if (DEBUG)
//					e.printStackTrace();
//				return null;
//			}
//			return allUsers;	
//		}
//
//	public static boolean createUser(String username, String firstName, String lastName, String password, String email, String phone, boolean isAdmin) {
//		
//        IPerson personBean = null;
//        
//		try {
//			personBean = personLookup();		
//         
//	        Person p = new Person();
//	        p.setUsername(username);
//	        p.setFornavn(firstName);
//	        p.setEtternavn(lastName);
//	        p.setPassword(password);
//	        p.setEmail(email);
//	        
//	        if(!phone.isEmpty()) p.setPhone(phone);
//	        
//	        byte b = 0;
//	        if (isAdmin) b = 1;               
//	        p.setIsAdmin(b);        
//	 
//	        personBean.savePerson(p);
//	        
//	        
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//		
//		return true;
//	}
//	
//	public static boolean updateUser(String username, String firstName, String lastName, String email, String phone, boolean isAdmin) { 
//		
//		IPerson personBean = null;
//        
//		try {
//			personBean = personLookup();		
//         
//			Person person = new Person();
//	        Person p = new Person();
//	        p.setUsername(username);
//	        
//	        person = personBean.findPerson(p);
//	        
//	        person.setUsername(username);
//	        person.setFornavn(firstName);
//	        person.setEtternavn(lastName);
//	        //person.setPassword(password);
//	        person.setEmail(email);
//	        
//	        if(!phone.isEmpty()) person.setPhone(phone);
//	        
//	        byte b = 0;
//	        if (isAdmin) b = 1;               
//	        person.setIsAdmin(b);        
//	 
//	        personBean.updatePerson(person);
//	        
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//		
//		return true;
//	}
//	
//	public static boolean changePassword(String username, String password){
//		
//
//		IPerson personBean = null;
//        
//		try {
//			personBean = personLookup();		
//         
//	        Person p = new Person();
//	        p.setUsername(username);
//	        p.setPassword(password);
//	 
//	        personBean.updatePerson(p);
//	        
//	        return true;
//	        
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}		
//		
//	}
//	
//	public static ArrayList<HashMap<String, String>> getEvents(String username){
//		
//		IEvent eventBean = null;
//		ArrayList<HashMap<String, String>> allEvents = new ArrayList<HashMap<String, String>>();
//		
//		try {
//			eventBean = eventLookup();
//			
//			Person p = new Person();
//			p.setUsername(username);
//			
//			List<Event> events = eventBean.getEvents(p);
//			
//			for (Event e : events) {
//				HashMap<String, String> event = new HashMap<String, String>();
//				event.put("eventId", String.valueOf(e.getEventId()));
//				event.put("eventName", e.getEventName());
//				event.put("location", e.getLocation());
//				event.put("start", String.valueOf(e.getStart()));
//				event.put("end", String.valueOf(e.getEnd()));
//				event.put("priority", String.valueOf(e.getPriority()));
//				event.put("lastChanged", String.valueOf(e.getLastChanged()));
//				event.put("frequency", String.valueOf(e.getFrequency()));
//				event.put("info", e.getInfo());
//				event.put("freqDate", String.valueOf(e.getFreqToDate()));
//				event.put("alarmId", String.valueOf(e.getIsInvitedTos().get(0).getAlarm().getAlarmId()));
//				event.put("lastSeen", String.valueOf(e.getIsInvitedTos().get(0).getLastSeen()));
//				event.put("appliance", String.valueOf(e.getIsInvitedTos().get(0).getIsGoing()));
//				event.put("isHidden", String.valueOf(e.getIsInvitedTos().get(0).getIsGoing()));
//				allEvents.add(event);				
//			}		
//		
//		} catch (Exception e) {
//			if (DEBUG)
//				e.printStackTrace();
//		}
//
//		return allEvents;		
//	}
//	
//	public static ArrayList<HashMap<String, String>> getEventsForGroup(int groupId){
//		
//		IEvent eventBean = null;
//		ArrayList<HashMap<String, String>> allEvents = new ArrayList<HashMap<String, String>>();
//		
//		try {
//			eventBean = eventLookup();
//			
//			Group g = new Group();
//			g.setGroupId(groupId);
//			
//			List<Event> events = eventBean.getEventsForGroup(g);
//			
//			for (Event e : events) {
//				HashMap<String, String> event = new HashMap<String, String>();
//				event.put("eventId", String.valueOf(e.getEventId()));
//				event.put("eventName", e.getEventName());
//				event.put("location", e.getLocation());
//				event.put("start", String.valueOf(e.getStart()));
//				event.put("end", String.valueOf(e.getEnd()));
//				event.put("priority", String.valueOf(e.getPriority()));
//				event.put("lastChanged", String.valueOf(e.getLastChanged()));
//				event.put("frequency", String.valueOf(e.getFrequency()));
//				event.put("info", e.getInfo());
//				event.put("freqDate", String.valueOf(e.getFreqToDate()));
//				event.put("alarmId", String.valueOf(e.getIsInvitedTos().get(0).getAlarm().getAlarmId()));
//				event.put("lastSeen", String.valueOf(e.getIsInvitedTos().get(0).getLastSeen()));
//				event.put("appliance", String.valueOf(e.getIsInvitedTos().get(0).getIsGoing()));
//				event.put("isHidden", String.valueOf(e.getIsInvitedTos().get(0).getIsGoing()));
//				allEvents.add(event);				
//			}		
//		
//		} catch (Exception e) {
//			if (DEBUG)
//				e.printStackTrace();
//		}
//
//		return allEvents;	
//		
//	}
//	
//	public static String getEventCreator(int eventId){
//		
//		IPerson personBean = null;
//		Person person = new Person();
//		Event event = new Event();
//		
//		event.setEventId(eventId);
//		
//		try {
//			personBean = personLookup();
//			
//			person = personBean.getEventCreator(event);
//			
//		} catch (Exception e) {
//			if (DEBUG)
//				e.printStackTrace();
//		}
//
//		return person.getUsername();
//		
//	}
//	
//	// denne er jeg usikker på
//	@SuppressWarnings("unused")
//	public static boolean setEventCreator(int eventId, String username){
//
//        //IEvent eventBean = null;
//        IPerson personBean = null;
//        List<Event> events = null;
//        
//		try {
//			personBean = personLookup();
//			
//			Person p = new Person();
//			p.setUsername(username);
//			
//			Event e = new Event();
//			e.setEventId(eventId);
//			
//			events = p.getEvents();
//			events.add(e);
//			
//			p.setEvents(events);
//	        
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}		
//
//		return true;
//		
//	}
//	
//	// sjekk return type på denne
//	public static int createEvent(String eventName, String location, String start, String end, int priority, int frequency, String info) {
//		
//        IEvent eventBean = null;
//        
//		try {
//			eventBean = eventLookup();		
//         
//	        Event e = new Event();
//	        e.setEventName(eventName);
//	        if(!location.isEmpty()) e.setLocation(location);
//	        e.setStart(Date.valueOf(start));
//	        e.setEnd(Date.valueOf(end));
//	        e.setPriority(Byte.valueOf(Integer.toString(priority)));
//	        if(frequency != 0) e.setFrequency(frequency);
//	        e.setInfo(info);
//	        
//	        eventBean.saveEvent(e);	        
//	        
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return 0;
//		}
//		
//		return 1;
//	}
//	
//	public static boolean updateEvent(String eventId, String eventName, String location, String start, String end, int priority,  String lastChanged, int frequency, String info) {
//
//        IEvent eventBean = null;
//        
//		try {
//			eventBean = eventLookup();		
//         
//			Event event = new Event();
//	        Event e = new Event();
//	        
//	        e.setEventId(Integer.valueOf(eventId));
//	        
//	        event = eventBean.findEvent(e);
//	        
//	        event.setEventName(eventName);
//	        if(!location.isEmpty()) event.setLocation(location);
//	        event.setStart(Date.valueOf(start));
//	        event.setEnd(Date.valueOf(end));
//	        event.setPriority(Byte.valueOf(Integer.toString(priority)));
//	        event.setLastChanged(Date.valueOf(lastChanged));
//	        if(frequency != 0) event.setFrequency(frequency);
//	        event.setInfo(info);
//	        
//	        eventBean.updateEvent(event);
//	        
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//		
//		return true;		
//	}
//	
//	public static boolean onClickedEvent(int eventId, String username) {
//		
//		IIsInvitedTo isInvitedToBean = null;
//		IsInvitedTo isinvitedto = new IsInvitedTo();
//		
//		try {
//			isInvitedToBean = isInvitedToLookup();
//			
//			isinvitedto.getId().setEventId(eventId);
//			isinvitedto.getPerson().setUsername(username);
//			isinvitedto.setLastSeen(Calendar.getInstance().getTime());
//			
//			isInvitedToBean.refreshIsInvitedTo(isinvitedto);
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//	
//		return true;
//	}
//	
//	public static ArrayList<HashMap<String, String>> getAppliances(int eventId){
//						
//		ArrayList<HashMap<String, String>> allAppliances = new ArrayList<HashMap<String, String>>();
//		IIsInvitedTo isInvitedToBean = null;
//		IsInvitedTo isinvitedto = new IsInvitedTo();
//
//		List<IsInvitedTo> isinvitedtos = new ArrayList<IsInvitedTo>(); 
//		
//		try {
//			isInvitedToBean = isInvitedToLookup();
//			
//			isinvitedto.getId().setEventId(eventId);
//			
//			isinvitedtos = isInvitedToBean.getAllIsGoing(isinvitedto);
//			
//			for(IsInvitedTo i : isinvitedtos) {
//				HashMap<String, String> lastSeen = new HashMap<String, String>();
//				lastSeen.put("username", i.getPerson().getUsername());
//				lastSeen.put("appliance", String.valueOf(i.getIsGoing()));
//				allAppliances.add(lastSeen);
//			}
//			
//			
//						
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return null;
//		}
//	
//		return allAppliances;		
//	}
//	
//	//sjekker om alle har sett eventen, og evt om lastSeen er senere enn lastChanged-attributten fra event
//	public static ArrayList<HashMap<String, String>> getLastSeen(int eventId){
//		
//		ArrayList<HashMap<String, String>> allLastSeen = new ArrayList<HashMap<String, String>>();
//		IIsInvitedTo isInvitedToBean = null;
//		IsInvitedTo isinvitedto = new IsInvitedTo();
//
//		List<IsInvitedTo> isinvitedtos = new ArrayList<IsInvitedTo>(); 
//		
//		try {
//			isInvitedToBean = isInvitedToLookup();
//			
//			isinvitedto.getId().setEventId(eventId);
//			
//			isinvitedtos = isInvitedToBean.getAllLastSeen(isinvitedto);
//			
//			for(IsInvitedTo i : isinvitedtos) {
//				HashMap<String, String> lastSeen = new HashMap<String, String>();
//				lastSeen.put("username", i.getPerson().getUsername());
//				lastSeen.put("lastSeen", String.valueOf(i.getLastSeen()));
//				allLastSeen.add(lastSeen);
//			}
//			
//			
//						
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return null;
//		}
//	
//		return allLastSeen;
//		
//	}
//
//	public static boolean setAppliance(int eventId, String username, String appliance) {
//		
//		IIsInvitedTo isInvitedToBean = null;
//		IsInvitedTo isinvitedto = new IsInvitedTo();
//		
//		try {
//			isInvitedToBean = isInvitedToLookup();
//			
//			isinvitedto.getId().setEventId(eventId);
//			isinvitedto.getId().setUsername(username);
//			isinvitedto.setIsGoing(Byte.valueOf(appliance));
//			isinvitedto.setLastSeen(Calendar.getInstance().getTime());
//			
//			isInvitedToBean.refreshIsInvitedTo(isinvitedto);
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//	
//		return true;
//	}
//			
//	public static boolean setGroupAppliance(int eventId, int groupId, String appliance) {		
//
//		IGroupInvitation groupInvitationBean = null;
//		GroupInvitation invitation = new GroupInvitation();
//		
//		try {
//			groupInvitationBean = groupInvitationLookup();
//			
//			invitation.getId().setEventId(eventId);
//			invitation.getId().setGroupId(groupId);
//			invitation.setIsGoing(Byte.valueOf(appliance));
//			invitation.setLastSeen(Calendar.getInstance().getTime());
//			
//			groupInvitationBean.updateInvitation(invitation);
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//	
//		return true;	
//	}
//	
//	public static boolean hideEvent(int eventId, String username, boolean isHidden){
//		
//		IIsInvitedTo isInvitedToBean = null;
//		IsInvitedTo isinvitedto = new IsInvitedTo();
//		
//		try {
//			isInvitedToBean = isInvitedToLookup();
//			
//			isinvitedto.getId().setEventId(eventId);
//			isinvitedto.getId().setUsername(username);
//			isinvitedto.setIsHidden(Byte.valueOf(Boolean.toString(isHidden)));
//			
//			isInvitedToBean.refreshIsInvitedTo(isinvitedto);
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//	
//		return true;
//	}
//	
//	public static boolean hideEventGroup(int eventId, int groupId, boolean isHidden){
//		
//		IGroupInvitation groupInvitationBean = null;
//		GroupInvitation invitation = new GroupInvitation();
//		
//		try {
//			groupInvitationBean = groupInvitationLookup();
//			
//			invitation.getId().setEventId(eventId);
//			invitation.getId().setGroupId(groupId);
//			invitation.setIsHidden(Byte.valueOf(Boolean.toString(isHidden)));
//			
//			groupInvitationBean.updateInvitation(invitation);
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//	
//		return true;	
//		
//	}
//
//	public static boolean deleteEvent(int eventId) {
//		
//		IEvent eventBean = null;
//		Event event = new Event();
//		
//		try {
//			eventBean = eventLookup();			
//			event.setEventId(eventId);			
//			eventBean.removeEvent(event);
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//	
//		return true;
//	}
//	
//	public static boolean addMembersToEvent(int eventId, String username) {
//		
//		IIsInvitedTo isInvitedToBean = null;
//		IsInvitedTo isinvitedto = new IsInvitedTo();
//		Person person = new Person();
//		
//		try {
//			isInvitedToBean = isInvitedToLookup();
//			
//			isinvitedto.getId().setEventId(eventId);
//			//isinvitedto.setAlarm(null);
//			
//			person.setUsername(username);
//			isinvitedto.setPerson(person);
//			
//			
//			isInvitedToBean.saveIsInvitedTo(isinvitedto);
//			
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//	
//		return true;
//	}
//	
//	public static boolean removeMembersFromEvent(int eventId, String username) {
//		
//		IIsInvitedTo isInvitedToBean = null;
//		IsInvitedTo isinvitedto = new IsInvitedTo();
//		Person person = new Person();
//		
//		try {
//			isInvitedToBean = isInvitedToLookup();
//			
//			isinvitedto.getId().setEventId(eventId);
//			
//			person.setUsername(username);
//			isinvitedto.setPerson(person);
//			
//			
//			isInvitedToBean.removeIsInvitedTo(isinvitedto);
//			
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//	
//		return true;
//	}
//
//	public static boolean addGroupsToEvent(int eventId, int groupId) {
//
//		IGroupInvitation groupInvitationBean = null;
//		GroupInvitation invitation = new GroupInvitation();
//		
//		
//		try {
//			groupInvitationBean = groupInvitationLookup();
//			
//			invitation.getId().setEventId(eventId);
//			invitation.getId().setGroupId(groupId);
//			
//			groupInvitationBean.addToEvent(invitation);
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//	
//		return true;
//	}
//	
//	public static boolean removeGroupsFromEvent(int eventId, int groupId) {
//		
//		IGroupInvitation groupInvitationBean = null;
//		GroupInvitation invitation = new GroupInvitation();
//		
//		
//		try {
//			groupInvitationBean = groupInvitationLookup();
//			
//			invitation.getId().setEventId(eventId);
//			invitation.getId().setGroupId(groupId);
//			
//			groupInvitationBean.removeFromEvent(invitation);
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//	
//		return true;
//	}
//	
//	public static ArrayList<String> getGroupMembers(int groupId){
//		
//		ArrayList<String> members = new ArrayList<String>();
//		IGroup groupBean = null;
//		Group group = new Group();
//		List<Person> persons = new ArrayList<Person>();
//		
//		try {
//			groupBean = groupLookup();
//			
//			group.setGroupId(groupId);
//			
//			persons = groupBean.getMembers(group);
//			
//			for(Person p : persons) {
//				members.add(p.getUsername());
//			}
//			
//		} catch (Exception e) {
//			if (DEBUG)
//				e.printStackTrace();
//			return null;
//		}
//		
//		return members;	
//		
//		
//	}
//	
//	public static ArrayList<HashMap<String, String>> getGroups(String username) {
//		
//		ArrayList<HashMap<String, String>> allGroups = new ArrayList<HashMap<String, String>>();
//		IPerson personBean = null;
//		Person person = new Person();
//		List<Group> groups = new ArrayList<Group>(); 
//		
//		try {
//			personBean = personLookup();
//			
//			person.setUsername(username);
//			
//			groups = personBean.getAllGroups(person);
//			
//			for (Group g : groups) {
//				HashMap<String, String> group = new HashMap<String, String>();
//				group.put("groupId", String.valueOf(g.getGroupId()));
//				group.put("groupName", String.valueOf(g.getGroupName()));
//				group.put("parent", String.valueOf(g.getGroup()));
//				allGroups.add(group);
//			}
//					
//					
//		} catch (Exception e) {
//			if (DEBUG)
//				e.printStackTrace();
//			return null;
//		}
//
//		return allGroups;
//		
//	}
//	
//
//	public static boolean addParent(int groupId, int parent){
//		
//		IGroup groupBean = null;
//		Group group = new Group();
//		Group parentGroup = new Group();
//		
//		group.setGroupId(groupId);
//		parentGroup.setGroupId(parent);
//		
//		try {
//			groupBean = groupLookup();
//			
//			groupBean.updateParentGroup(group, parentGroup);
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//	
//		return true;
//		
//	}
//	
//	public static boolean removeParent(int groupId, int parent){
//		
//		IGroup groupBean = null;
//		Group group = new Group();
//		Group parentGroup = new Group();
//		
//		group.setGroupId(groupId);
//		parentGroup.setGroupId(parent);
//		
//		try {
//			groupBean = groupLookup();
//			
//			groupBean.removeParentGroup(group);
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//	
//		return true;
//		
//	}
//	
//	public static int createGroup(String groupName, int parent) {
//		
//		IGroup groupBean = null;
//		Group group = new Group();
//		Group parentGroup = new Group();
//		int groupId;
//		
//		try {
//			groupBean = groupLookup();
//			
//			group.setGroupName(groupName);			
//			parentGroup.setGroupId(parent);			
//			if(parent != 0) group.setGroup(parentGroup);
//			
//			groupId = groupBean.saveGroup(group);			
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return -1;
//		}
//		
//		return groupId;
//		
//	}
//	
//	public static boolean addMembersToGroup(int groupId, String username) {
//
//		IPerson personBean = null;
//		
//		Group group = new Group();
//		//List<Group> groups = new ArrayList<Group>();
//		Person person = new Person();
//		
//		try {
//			personBean = personLookup();
//			
//			person.setUsername(username);
//			group.setGroupId(groupId);
//						
//			personBean.addToGroup(group, person);
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//	
//		return true;
//		
//	}
//	
//	public static boolean removeMembersFromGroup(int groupId, String username) {
//		
//		IPerson personBean = null;
//		
//		Group group = new Group();
//		//List<Group> groups = new ArrayList<Group>();
//		Person person = new Person();
//		
//		try {
//			personBean = personLookup();
//			
//			person.setUsername(username);
//			group.setGroupId(groupId);
//						
//			personBean.removeFromGroup(group, person);
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//	
//		return true;
//	}
//	
//	public static boolean deleteGroup(int groupId) {
//		
//		IGroup groupBean = null;
//		Group group = new Group();
//		
//		try {
//			groupBean = groupLookup();
//			
//			group.setGroupId(groupId);
//			groupBean.removeGroup(group);
//			
//			
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//	
//		return true;
//				
//	}
//		
//	public static boolean reserveRoom(int eventId, int roomNr) {
//		
//		IRoom roomBean = null;
//		Event event = new Event();
//		List<Event> events = new ArrayList<Event>();
//		Room room = new Room();
//		
//		try {
//			roomBean = roomLookup();
//			
//			event.setEventId(eventId);
//			room.setRoomNr(roomNr);
//			events.add(event);			
//			room.setEvents(events);
//			
//			roomBean.saveRoom(room);			
//		
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return false;
//		}
//		
//		return true;
//		
//	}
//	
//	public static ArrayList<HashMap<String, String>> getAllRooms(){
//		
//		IRoom roomBean = null;
//		List<Room> rooms = new ArrayList<Room>();
//		//ArrayList<String> result = new ArrayList<String>();
//		ArrayList<HashMap<String, String>> allRooms = new ArrayList<HashMap<String, String>>();		
//		
//		try {
//			roomBean = roomLookup();
//			rooms = roomBean.getAllRooms();
//			
//			for (Room r : rooms) {
//				HashMap<String, String> room = new HashMap<String, String>();
//				room.put("roomNr", String.valueOf(r.getRoomNr()));
//				room.put("capacity", String.valueOf(r.getCapacity()));
//				allRooms.add(room);
//			}
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return null;
//		}
//		
//		return allRooms;
//		
//	}
//
//	public static ArrayList<String> getAvailableRooms(String start, String end) {
//		
//		IRoom roomBean = null;
//		List<Room> rooms = new ArrayList<Room>();
//		ArrayList<String> result = new ArrayList<String>();
//		Event event = new Event();
//		
//		event.setStart(Date.valueOf(start));
//		event.setEnd(Date.valueOf(end));
//		
//		
//		try {
//			roomBean = roomLookup();
//			rooms = roomBean.getAvailableRooms(event);
//			
//			for (Room room : rooms) {
//				result.add(String.valueOf(room.getRoomNr()));
//			}
//			
//		} catch (Exception e) {
//			if (DEBUG) e.printStackTrace();
//			return null;
//		}
//		
//		return result;
//		
//	}
//	
//	public static boolean createAlarm(int minBeforeEvent) {
//		
//		IAlarm alarmBean = null;
//		Alarm alarm = new Alarm();
//		
//		try {
//			alarmBean = alarmLookup();
//			
//			alarm.setMinBeforeEvent(Short.parseShort(Integer.toString(minBeforeEvent)));
//			
//			alarmBean.saveAlarm(alarm);
//			
//		 } catch (Exception e) {
//		      if (DEBUG) e.printStackTrace();
//		      return false;
//	    }
//		
//		return true;		
//	}
//	
//	public static boolean setAlarm(int eventId, String username, int alarmId) {
//		
//		IIsInvitedTo isInvitedToBean = null;
//		IsInvitedTo i = new IsInvitedTo();
//		Event event = new Event();
//		Person person = new Person();
//		Alarm alarm = new Alarm();
//		
//		event.setEventId(eventId);
//		person.setUsername(username);
//		alarm.setAlarmId(alarmId);
//		
//		try {
//			isInvitedToBean = isInvitedToLookup();			
//			
//			i.setEvent(event);
//			i.setPerson(person);
//			i.setAlarm(alarm);
//			
//			isInvitedToBean.saveIsInvitedTo(i);			
//	          
//	    } catch (Exception e) {
//		      if (DEBUG) e.printStackTrace();
//		      return false;
//	    }
//		
//		return true;
//		
//	}
//	
//	
//	
//	////
//	// lookup methods
//	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	private static IPerson personLookup() throws NamingException {
//		final Hashtable properties = new Hashtable();        
//        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");        
//        final Context context = new InitialContext(properties);        
//        return (IPerson) context.lookup("ejb:/XKalServer/PersonBean!" + IPerson.class.getName());    	
//    }
//
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	private static IGroup groupLookup() throws NamingException {
//        final Hashtable properties = new Hashtable();        
//        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");        
//        final Context context = new InitialContext(properties);        
//        return (IGroup) context.lookup("ejb:/XKalServer/GroupBean!" + IGroup.class.getName());    	
//    }
//
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	private static IGroupInvitation groupInvitationLookup() throws NamingException {
//        final Hashtable properties = new Hashtable();        
//        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");        
//        final Context context = new InitialContext(properties);        
//        return (IGroupInvitation) context.lookup("ejb:/XKalServer/GroupInvitationBean!" + IGroupInvitation.class.getName());    	
//    }
//
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	private static IEvent eventLookup() throws NamingException {
//        final Hashtable properties = new Hashtable();        
//        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");        
//        final Context context = new InitialContext(properties);        
//        return (IEvent) context.lookup("ejb:/XKalServer/EventBean!" + IEvent.class.getName());    	
//    }
//
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	private static IAlarm alarmLookup() throws NamingException {
//        final Hashtable properties = new Hashtable();        
//        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");        
//        final Context context = new InitialContext(properties);        
//        return (IAlarm) context.lookup("ejb:/XKalServer/AlarmBean!" + IAlarm.class.getName());    	
//    }
//
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	private static IIsInvitedTo isInvitedToLookup() throws NamingException {
//        final Hashtable properties = new Hashtable();        
//        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");        
//        final Context context = new InitialContext(properties);        
//        return (IIsInvitedTo) context.lookup("ejb:/XKalServer/IsInvitedToBean!" + IIsInvitedTo.class.getName());    	
//    }
//
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	private static IRoom roomLookup() throws NamingException {
//        final Hashtable properties = new Hashtable();        
//        properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");        
//        final Context context = new InitialContext(properties);        
//        return (IRoom) context.lookup("ejb:/XKalServer/RoomBean!" + IRoom.class.getName());    	
//    }
//}
