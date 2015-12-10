package controllers;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import models.Movie;
import models.User;
import utils.Serializer;
import utils.XMLSerializer;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
/**
 * @author Gavin Fennelly
 * 
 * this class starts the recommender programme
 *
 */

public class Main
{
	public MovieRecommenderAPI movieRecommender;
	public CSVLoader csvloader = new CSVLoader();
	public Main() throws Exception
	{

		//Stores the users file  to the file datastore.xml
		//Loads the file datastore in main method
		File datastore = new File("datastore.xml");
		Serializer serializer = new XMLSerializer(datastore);

		//To implement persistance we need .load and .store

		movieRecommender = new MovieRecommenderAPI(serializer);
		if (datastore.isFile())
		{
			movieRecommender.load();
		}
		else
		{
			List<User> users  = csvloader.loadUsers("data/users5.dat");
			for(User user:users)
			{
				movieRecommender.createUser(user);
			}

			List<Movie> movies = csvloader.loadMovies("data/items5.dat");
			for(Movie movie : movies)
			{
				movieRecommender.addMovie(movie);
			}
			movieRecommender.store();

		}
	}

	/**
	 * Launch the application.
	 */
	//Cliche Command-Line Shell-
	//Cliche is a small java library enabling simple creation
	//of interactive command line user interfaces
	//This runs the main command line interface of my project
	public static void main(String[] args) throws Exception
	{
		Main main = new Main();
		Shell shell = ShellFactory.createConsoleShell("Enter your text here..>", "Welcome to the movie recommender system,"
				+ " please type ?list-all to see all commands we have available in our system today :)", main);
		shell.commandLoop();
		main.movieRecommender.store();
	}



	//returns all users and details of users
	//generated users from tests and saved to datastore
	@Command(description="Get all users details")
	public void getUsers ()
	{
		Collection<User> users = movieRecommender.getUsers();
		System.out.println(users);
	}
	//returns a user by id
	@Command(description="Get a user by id")
	public void getUser(@Param(name = "id")Long id)
	{
		movieRecommender.getUser(id);
	}

	@Command(description="Add a new User")
	public void addUser (@Param(name="first name") String firstName, @Param(name="last name") String lastName,
			@Param(name="age") int age, @Param(name="gender") String gender, @Param(name="occupation") String occupation)
	{
		movieRecommender.createUser(firstName, lastName, age, gender, occupation);
	}

	@Command(description="Delete a User")
	public void removeUser (@Param(name="id") Long id)
	{
		{
			//Prints back all the details of the user then confirms they are deleted from the system
			Optional<User> user = Optional.ofNullable(movieRecommender.getUser(id));
			if (user.isPresent())
			{
				movieRecommender.deleteUser(id);
			}
		}
	}


	@Command(description = "List all movies")
	public void getMovies()
	{
		Collection<Movie> movies = movieRecommender.getMovies();
		System.out.println(movies);
	}
	@Command(description="Add a Movie")
	public void addMovie (@Param(name="title") String title, @Param(name="year") String year, @Param(name="url") String url)
	{
		movieRecommender.addMovie(title, year, url);
	}

	@Command(description="Add a rating")
	public void addRating(@Param(name="Full-Name") String fullname, @Param(name="movieId")Long movieId, @Param(name="rating") int rating)
	{


		User user = movieRecommender.getUserByFullName (fullname);

		movieRecommender.addRating(user.id, movieId, rating);

		//
	}

	@Command(description="User Recommendations")
	public void getUserRecommendations (@Param(name="fullname") String fullname )

	{
		User user = movieRecommender.getUserByFullName (fullname);

		movieRecommender.getUserRecommendations(user.id);
	}

	@Command(description="Top Ten Movies")
	public void topTenMovies ()
	{

		System.out.println(movieRecommender.getTopTenMovies());
	}
}
