/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proquest.interview.phonebook;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.proquest.interview.util.DatabaseUtil;
/**
 *
 * @author jeff
 */
public class PhoneBookDAO {
    public static final String READ_PERSON = "SELECT * FROM PHONEBOOK WHERE NAME = ?";
    public static final String GET_ALL_PERSONS = "SELECT * FROM PHONEBOOK";
    public static final String INSERT_PERSON = "INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES(?, ?, ?) ";
    
    public Person findPersonByName(String queryName) throws Exception {
        try {  
            Person personFound;
            Connection PhoneBookDBConn = DatabaseUtil.getConnection();
            PreparedStatement personNameQuery = PhoneBookDBConn.prepareStatement(READ_PERSON);
            personNameQuery.setString(1, queryName);
            ResultSet personResultSet = personNameQuery.executeQuery();
            if(personResultSet.next()) {
                String tempName = personResultSet.getString(1);
                String tempPhoneNumber = personResultSet.getString(2);
                String tempAddress = personResultSet.getString(3);
                if(tempAddress == null) {
                    tempAddress = "";
                }
                personFound = new Person();
                personFound.name = tempName;
                personFound.phoneNumber = tempPhoneNumber;
                personFound.address = tempAddress; 
                return personFound;
            } else {
                return null;
            }
        } catch (SQLException sqle) {
          System.out.println(sqle.getMessage());
          throw new Exception("SQLException with PhoneBookDB in findPersonByName");
        } catch(ClassCastException cce) {
          System.out.println(cce.getMessage());
          throw new Exception("ClassCastException with PhoneBookDB in findPersonByName");
        }
    }
    public boolean insertPerson(Person newPerson)  throws Exception {
       
        try {
            Connection phoneBookDBConn = DatabaseUtil.getConnection();
            PreparedStatement insertPerson;
            insertPerson = phoneBookDBConn.prepareStatement(INSERT_PERSON);
            insertPerson.setString(1, newPerson.name);
            insertPerson.setString(2, newPerson.phoneNumber);
            insertPerson.setString(3, newPerson.address); 
            int linesUpdated = insertPerson.executeUpdate();
            phoneBookDBConn.commit();
            insertPerson.close();
            phoneBookDBConn.close();
            return(linesUpdated==1);
        } catch(SQLException sqle) {
            System.out.println(sqle.getMessage());
            throw new Exception("SQLException with PhoneBookDB in insertPerson");
        } catch(ClassCastException cce) {
            System.out.println(cce.getMessage());
            throw new Exception("ClassCastException with PhoneBookDB in insertPerson");
        } 
    }         
    public ArrayList<Person> getAllPersons() throws Exception {
        ArrayList<Person>personList= new ArrayList<Person>();
        try {
            Connection phoneBookDBConn = DatabaseUtil.getConnection();
            Statement getAllStatement = phoneBookDBConn.createStatement();
            ResultSet getAllResultSet = getAllStatement.executeQuery(GET_ALL_PERSONS);
            while(getAllResultSet.next()) {
                    Person newPerson = new Person();
                    newPerson.name = getAllResultSet.getString(1);
                    newPerson.phoneNumber = getAllResultSet.getString(2);
                    String tempAddress = getAllResultSet.getString(3);
                    if(tempAddress==null) {
                      newPerson.address="";
                    }
                    
                    personList.add(newPerson);
            }
            return personList;
        } catch(SQLException sqle) {
            System.out.println(sqle.getMessage());
            throw new Exception("SQLException in getAllPerons");
        } catch(ClassCastException cce) {
            System.out.println(cce.getMessage());
            throw new Exception("ClassCastException in getAllPerons");
        }
    }

}
 
