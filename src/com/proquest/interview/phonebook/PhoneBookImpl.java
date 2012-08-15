package com.proquest.interview.phonebook;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.PrintStream;
import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	public List<Person> people;
	public PhoneBookDAO phoneDAO;
        public PhoneBookImpl() {
            people = new ArrayList<Person>();
            phoneDAO = new PhoneBookDAO();
            try {
                people=phoneDAO.getAllPersons();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
	@Override
	public void addPerson(Person newPerson) { 
            try {
                if(!phoneDAO.insertPerson(newPerson)) {
                    System.out.println("Duplicate phonebook entry");
                }
                people.add(newPerson);
            } catch(Exception e) {
              System.out.println(e.getMessage());
            }
	}
	
	@Override
	public Person findPerson(String firstName, String LastName) {
            try {
                Person foundPerson = phoneDAO.findPersonByName(firstName+" "+LastName);
                return foundPerson;
            }   catch(Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
		
	}
        
        public static void printOutPhoneBook(PhoneBookImpl phoneBook, PrintStream out) {
            for(Person tempPerson : phoneBook.people) {
                out.println(tempPerson.name+"||"+tempPerson.phoneNumber+"||"+tempPerson.address);
            }
        }
        
	public static void main(String[] args) {
		DatabaseUtil.initDB();  //You should not remove this line, it creates the in-memory database
                PhoneBookImpl myPhoneBook = new PhoneBookImpl();
                Person newPerson = new Person();
                newPerson.name = "John Smith";
                newPerson.phoneNumber = "(248)123-4567";
                newPerson.address = "1234 Sand Hill Dr, Royal Oak, MI";
                myPhoneBook.addPerson(newPerson);
                newPerson = new Person();
                newPerson.name = "Cynthia Smith";
                newPerson.phoneNumber = "(248)128-8758";
                newPerson.address = "875 Main St, Ann Arbor, MI";
                myPhoneBook.addPerson(newPerson);
                PhoneBookImpl.printOutPhoneBook(myPhoneBook, System.out);
		/* TODO: create person objects and put them in the PhoneBook and database
		 * John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
		 * Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI
		*/ 
              
		// TODO: print the phone book out to System.out
                System.out.println("************");
                Person cynthia=myPhoneBook.findPerson("Cynthia","Smith");
                System.out.println(cynthia.name+"||"+cynthia.phoneNumber+"||"+cynthia.address);
		// TODO: find Cynthia Smith and print out just her entry
		// TODO: insert the new person objects into the database
	}
}
