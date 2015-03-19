package gui;

import java.util.List;

import classes.Person;

public interface GetPersonListener {
	public void updatePersons(List<Person> persons);
	public void personAdded(boolean added);
}
