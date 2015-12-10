package controllers;

import static org.junit.Assert.assertEquals;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class MovieRecommenderAPITest
{
	private MovieRecommenderAPI movierecommender;

	@Before
	public void setup()
	{
		movierecommender = new MovieRecommenderAPI();
	}

	@After
	public void tearDown()
	{
		movierecommender = null;
	}

	@Test
	public void testUser()
	{
		assertEquals (0, movierecommender.getUsers().size());
	} 

	// verifies that we are starting with an empty model
	@Test
	public void testUserEmpty()

	{

		User gavin = new User ("gavin", "fennelly", "21",  "m","programmer");

		assertEquals (0, movierecommender.getUsers().size());
		movierecommender.createUser("gavin", "fennelly", "21", "m","programmer");
		assertEquals (1, movierecommender.getUsers().size());
	} 

	@Test
	public void testUser()
	{
		User gavin = new User ("gavin", "fennelly", "21",  "m", "programmer");

		assertEquals (0, movierecommender.getUsers().size());
		movierecommender.createUser("gavin", "fennelly", "21", "m","programmer");
		assertEquals (1, movierecommender.getUsers().size());

		assertEquals (gavin, movierecommender.getUser("gavin"));
	}  

	@Test
	public void testUsers()
	{
		for (User user : users)
		{
			movierecommender.createUser(user.firstName, user.lastName, user.age, user.gender, user.occupation);
		}
		assertEquals (getUsers.length, movierecommender.getUsers().size());
	}
}