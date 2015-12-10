package models;

import java.util.ArrayList;
import java.util.List;

import utils.ToJsonString;

import com.google.common.base.Objects;
/**
 * 
 * @author Gavin Fennelly 
 * This class is the Where the user model  is created and where all functions relating to the user/member are performed.
 * 
 */



public class User 
{

	//static counter, incremented by 1 as each object is created.
	public static Long counter = 1l; 
	public Long id;
	public String firstName;
	public String lastName;
	public int age;
	public String gender;
	public String occupation;


	public List<Rating> ratings = new ArrayList<>();



	//Constructs all the objects created in the user class
	//only used when parsing in users from CSVLoader (From the users5.dat file)

	public User(Long id,String firstName, String lastName,int age,String gender,String occupation)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;

		counter++; 
	}



	//This constructor is used on the command line interface
	//Doesn't have an ID in main, ID is assigned on creation
	public User(String firstName, String lastName, int age, String gender,String occupation) 
	{
		this.id = counter++;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;
	}

	//override-
	//Checking to make sure you actually are overriding a method when you think you are.

	//This way, if you make a common mistake of misspelling
	//a method name or not correctly matching the parameters,
	//you will be warned that your method does not actually override as you think it does



	@Override
	public String toString()
	{
		return new ToJsonString(getClass(), this).toString();
	}

	//strengthen the hash associated with the Movie class, as we are storing it in a Map.
	//implements the hash function to use our objects attributes:

	@Override  
	public int hashCode()  
	{  
		return Objects.hashCode(this.firstName, this.lastName,this.age,this.gender,this.occupation);  
	}  

	//The default behaviour of this test is to 
	//trigger a call to 'equals()' on the User objects. 
	//We haven't implemented this method, so the default 
	//behaviour is to do an identity comparison:

	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof User)
		{
			final User other = (User) obj;
			return Objects.equal(firstName,   other.firstName) 
					&&  Objects.equal(lastName,    other.lastName)
					&& Objects.equal(age,			other.age)
					&& Objects.equal(gender, other.gender)
					&&  Objects.equal(occupation,       other.occupation)
					&&	Objects.equal(ratings,			other.ratings);
		}
		else
		{
			return false;
		}
	}
}
