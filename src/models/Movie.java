package models;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

import utils.ToJsonString;


/**
 * @author Gavin Fennelly
 * This class is the Movie models section where all functions relating to the film are executed.
 */

//Without implementing comparable, compareTo would just be a random name for a method

public class Movie implements Comparable <Movie>
{
	public static Long counter = 1l;

	public Long id;

	public String title;
	public String year;
	public String url;

	public List<Rating> ratings = new ArrayList<Rating>();


	//This constructor is used on the command line interface
	//Doesn't have an ID in main, ID is assigned on creation
	//constructs all the Objects in the movie class
	public Movie(String title, String year, String url)
	{
		this.id = counter++;
		this.title = title;
		this.year = year;
		this.url = url;
	}

	//Constructs all the objects created in the user class
	//only used when parsing in Movies from CSVLoader (From the items5.dat file)
	public Movie(Long id, String title, String year, String url) 
	{
		this.id = id;
		this.title = title;
		this.year = year;
		this.url = url;

		counter++;
	}


	// returns 0 for not rated  film

	//This method gets the average rating of a movie

	public double getAverageRating()
	{
		//returns 0 for the not rated movies
		if (ratings.isEmpty()) {
			return 0;
		}
		else
		{

			//otherwise computes average if there is ratings in arraylist

			double total = 0;

			for (Rating rating : ratings)
			{
				total += rating.rating;
			}
			return total / ratings.size();

		}
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
		return Objects.hashCode(this.id, this.title, this.year, this.url);
	}


	//The default behaviour of this test is to 
	//trigger a call to 'equals()' on the User objects. 
	//We haven't implemented this method, so the default 
	//behaviour is to do an identity comparison:
	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof Movie)
		{
			final Movie other = (Movie) obj;
			return Objects.equal(title, other.title)
					&& Objects.equal(year, other.year)
					&& Objects.equal(url, other.url);
		}
		else
		{
			return false;
		}
	}

	// Compares this movie with another movie, 
	//if left ones average is less than right ones average returns -1
	// if rights ones average is less than left ones average returns 1
	//and if they are equal it returns 0
	//all these values are calculated within the comparable java method.

	public int compareTo(Movie other) {
		return Double.compare(this.getAverageRating(), other.getAverageRating());

	}
}