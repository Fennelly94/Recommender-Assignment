package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import models.Movie;
import models.User;
import edu.princeton.cs.introcs.In;

public class CSVLoader
{


	private File usersFile = new File("data.xml");


	public List<User> loadUsers(String fileName) throws Exception
	{
		//Returns an array of  user model objects created.
		List <User> users = new ArrayList<User>();

		In inUsers = new In(fileName);

		//each field is separated by a '|'
		String delims = "[|]";
		while (!inUsers.isEmpty()) 
		{
			// get user and rating from data source
			String userDetails = inUsers.readLine();

			// parse user details string
			String[] userTokens = userDetails.split(delims);
			users.add(new User(Long.parseLong(userTokens[0]),userTokens[1],userTokens[2],Integer.parseInt(userTokens[3]),userTokens[4],userTokens[5]));			
			// output user data to console.
			if (userTokens.length == 7) {
				System.out.println("id: "+userTokens[0]+",firstName:"+
						userTokens[1]+",lastName:" + userTokens[2]+",age:"+
						Integer.parseInt(userTokens[3])+",gender:"+userTokens[4]+",occupation:"+
						userTokens[5]);

			}else
			{
				throw new Exception("Invalid member length: "+userTokens.length);
			}
		}


		for(int i = 0;i<users.size();i++)
		{
			System.out.println(users.get(i));
		}
		return users;
	}


	@SuppressWarnings("unused")
	public List<Movie> loadMovies(String fileName) throws Exception
	{
		//Returns an array of model objects created.
		List <Movie> movies = new ArrayList<Movie>();

		In inUsers = new In(fileName);

		//each field is separated(delimited) by a '|'
		String delims = "[|]";
		while (!inUsers.isEmpty()) 
		{
			// get user and rating from data source
			String movieDetails = inUsers.readLine();

			// parse user details string
			String[] movieTokens = movieDetails.split(delims);
			movies.add(new Movie(Long.parseLong(movieTokens[0]),movieTokens[1],movieTokens[2],movieTokens[3]));			// output user data to console.
			if (movies!=null) 
			{
				System.out.println(movies.size());
			}
			else
			{
				throw new Exception("Invalid member length: "+movieTokens.length);
			}

		}
		for(int i = 0;i<movies.size();i++)
		{
			System.out.println(movies.get(i));
		}
		return movies;	
	}

	//returns users file

	public File getUsersFile() {
		return usersFile;
	}


	//Sets users file

	public void setUsersFile(File usersFile) {
		this.usersFile = usersFile;
	}
}

