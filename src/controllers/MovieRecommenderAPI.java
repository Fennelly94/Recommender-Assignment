package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

import models.Movie;
import models.Rating;
import models.User;
import utils.Serializer;

public class MovieRecommenderAPI 
{
	private Serializer serializer;

	//movies and users are members of this API class.

	//map of userID , User
	private Map<Long,User>   userIndex = new HashMap<>();

	private Map<String,Long> userName = new HashMap<>();

	//map of movieID , Movie 
	private Map<Long, Movie> movieIndex = new HashMap<>();

	//Each user holds a list of ratings objects 
	private Map<String, Rating> ratingIndex = new HashMap<>();

	public MovieRecommenderAPI()
	{}

	public MovieRecommenderAPI(Serializer serializer)
	{
		this.serializer = serializer;
	}

	//Persistence is data outlives the process that created it

	//we are using XML databases to make data persist

	//To introduce persistence capability into the api,
	//we have to implement these two methods

	@SuppressWarnings("unchecked")
	public void load() throws Exception
	{
		serializer.read();
		Movie.counter = (Long) serializer.pop();
		movieIndex = (Map<Long, Movie>) serializer.pop();
		userName = (Map<String,Long>)  serializer.pop();

		User.counter = (Long) serializer.pop();
		userIndex = (Map<Long, User>)	serializer.pop();
	}

	public void store() throws Exception
	{

		//Since counter is now serialized when application is re launched it will 
		//not start at zero but from where it last was
		serializer.push(userIndex);
		serializer.push(User.counter);
		serializer.push(userName);
		serializer.push(movieIndex);
		serializer.push(Movie.counter);
		serializer.write(); 
	}

	//Returns a collection of the users in the map
	public Collection<User> getUsers ()
	{
		return userIndex.values();
	}

	public  void deleteUsers() 
	{
		userIndex.clear();
	}

	/**
	 * @param user
	 * takes in a user and adds that user to the arrayList of users
	 */
	public User createUser(User user)
	{
		userIndex.put(user.id, user);

		userName.put(user.firstName +" " + user.lastName, user.id);
		return user;
	}


	//method overloaded so had to create two methods
	public User createUser(String firstName, String lastName, int age, String gender, String occupation)
	{
		User user = new User (firstName, lastName, age, gender, occupation);
		userIndex.put(user.id, user);

		userName.put(user.firstName +" " + user.lastName, user.id);

		return user;


	}

	// Searches for a user
	public User getUser(Long id) 
	{
		System.out.println(userIndex.get(id));
		return userIndex.get(id);
	}

	//takes in name converts to id converts to user object
	public User getUserByFullName(String fullname)
	{
		return getUser(userName.get(fullname));
	}

	//Searches for a user then deletes them 
	public void deleteUser(Long id) 
	{
		System.out.println("User id: " + userIndex.get(id).id + " First Name: "+ userIndex.get(id).firstName + " has been deleted");
		userIndex.remove(id);
	}

	public Movie addMovie(String title, String year, String url)
	{
		Movie movie = new Movie (title, year, url);
		movieIndex.put(movie.id, movie);
		return movie;
	}
	/**
	 * @param movie
	 * method to take in a movie and add it to the movie array
	 * */
	public Movie addMovie(Movie movie)
	{
		movieIndex.put(movie.id, movie);
		return movie;
	}

	//Returns a collection of the users in the map
	public Collection<Movie> getMovies ()
	{
		return movieIndex.values();
	}

	public void deleteMovies()
	{
		movieIndex.clear();
	}

	public Movie getMovie(Long movieID)
	{
		return movieIndex.get(movieID);
	}

	//Each user holds a list of ratings objects 
	public Rating addRating(Long userId,Long movieId,int rating)

	{

		Rating userRating = new Rating(userId,movieId,rating);
		ratingIndex.put(userId + "," + movieId, userRating);
		User user = userIndex.get(userId);
		user.ratings.add(userRating);

		Movie movie = getMovie(movieId);
		movie.ratings.add(userRating);

		return userRating;

	}

	public Rating addRating(Rating rating)
	{
		ratingIndex.put(rating.userId + "," + rating.movieId, rating);

		User user = userIndex.get(rating);
		user.ratings.add(rating);
		Movie movie = getMovie(rating.movieId);
		movie.ratings.add(rating);


		return rating;

	}


	public Collection<Rating> getUserRatings(Long userID)
	{
		User user = userIndex.get(userID);
		return user.ratings;
	}

	public List <Movie> getTopTenMovies()
	{

		//Gets all the movies into an unsorted list, gets values from movie hash map on right movieIndex.values
		//Returns a collection class that contains all the movies and stores them in an array list so we can sort
		//them, (Arraylist supports sorting)

		List <Movie> allmovies = new ArrayList <Movie> (movieIndex.values());

		//Collections.sort works because of compareTo in the Movie class
		//The movie class implements comparable interface , reason it functions properly
		//sorts in accending order, static method in collections class.
		//sorting is done on the spot a new list was not made

		Collections.sort (allmovies);

		//ordered by average rating
		//sorted by default in accending order thats why need reverse

		Collections.reverse(allmovies);

		//To stop all movies being returned, subList was used. Intakes 2 arguments 0 up to 10
		//At most take first 10 elements, if over ten take no more if under 10 just take the amount there is

		return allmovies.subList(0,Math.min (allmovies.size(),10));


	}


	//userid is passed into the method

	public List <Movie> getUserRecommendations (Long userid) 
	{

		//Gets all the movies into an unsorted list, gets values from movie hash map on right movieIndex.values
		//Returns a collection class that contains all the movies and stores them in an array list so we can sort
		//them ArrayLists support sorting

		List <Movie> allmovies = new ArrayList <Movie> (movieIndex.values());

		//Collections.sort works because of compareTo in the Movie class
		//The movie class implements comparable interface , reason it functions properly
		//sorts in accending order, static method in collections class.
		//sorting is done on the spot a new list was not made

		Collections.sort (allmovies);

		//ordered by average rating
		//sorted by default in accending order thats why reverse again is needed again

		Collections.reverse(allmovies);

		//seperate list with 5 movies, has user rated movie or not
		//if he/she rated it drop it, starts at top then stops when find  5 that were
		//not rated


		//Seperate list with 5 movies is created. 

		List<Movie> newList = new ArrayList <Movie> ();

		//start of loop

		//nested loop checking each movie

		//Start of loop

		for (Movie movie : allmovies)
		{

			//Movie at default is assumed its not rated by user

			boolean ratedByUser = false;

			//nested loop checking each movie in arrayList

			for (Rating rating : movie.ratings)

			{  
				//userId filed in rating class, userid is paramater aboove

				//userId in rating class (Parameter above) 

				//If one rating is found by user boolean is flipped to true.

				if (rating.userId == userid)
				{
					ratedByUser = true;


					break;

				}
			}

			//passed test got added to new arraylist that was created called newList

			if (!ratedByUser)
			{
				newList.add(movie);
				if (newList.size() ==5)
				{
					break;

				}
			}

		}


		return newList;


	}


}



