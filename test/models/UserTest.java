package models;

import static models.Fixtures.users;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import controllers.MovieRecommenderAPI;

public class UserTest
{
	User gavin = new User ("gavin", "fennelly", 21, "m",  "programmer");
	MovieRecommenderAPI movieRecommender;
	@Test
	public void testCreate()
	{
		assertEquals ("gavin",                gavin.firstName);
		assertEquals ("fennelly",             gavin.lastName);
		assertEquals (21,                    gavin.age);   
		assertEquals ("m",                   gavin.gender); 
		assertEquals ("programmer",            gavin.occupation);
	}

	@Test
	public void testIds()
	{
		//test id size
		Set<Long> ids = new HashSet<>();
		for (User user : users)
		{
			ids.add(user.id);
		}
		assertEquals (users.length, ids.size());
		//test that each objects id witch each other to ensure they are the same
		for(int i = 0; i<users.length;i++)
		{
			assertEquals(users[i].id,users[i].id);
		}
	}

	@Test
	public void testToString()
	{
		assertEquals(gavin.toString(),gavin.toString());
	}


	@Test
	public void testEquals()
	{
		User gavin2 = new User ("gavin", "fennelly", 21, "m",  "programmer"); 
		User homer  = new User ("homer", "simpson", 47, "m",  "nuclear power employee"); 

		assertEquals(gavin, gavin);
		assertEquals(gavin, gavin2);
		assertNotEquals(gavin, homer);
	} 
	
	
}