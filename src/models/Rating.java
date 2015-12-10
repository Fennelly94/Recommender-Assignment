package models;

import utils.ToJsonString;

import com.google.common.base.Objects;

public class Rating 
{
	//	public static Long counter = 1l;
	//public Long id;
	public int rating;
	public Long movieId;
	public Long userId;

	public Rating(Long userId,Long movieId,int rating)
	{
		this.movieId = movieId;
		this.userId = userId;

		this.rating = rating;


	}

	// make sure they give an appropriate rating
	public void setRating(int rating)
	{
		if(rating >= 0 && rating <=5)
		{
			this.rating = rating;
		}
		else
		{
			System.out.println("This vaule is not in the rating system.Please rate between 0(very bad) and 5(Excellent)");
		}
	}

	//override-
	//Checking to make sure you actually are overriding a method when you think you are.

	//This way, if you make a common mistake of misspelling
	//a method name or not correctly matching the parameters,
	//you will be warned that your method does not actually override as you think it does


	public String toString()
	{
		return new ToJsonString(getClass(), this).toString();
	}

	//strengthen the hash associated with the Movie class, as we are storing it in a Map.
	//implements the hash function to use our objects attributes:

	@Override  
	public int hashCode()  
	{  
		return Objects.hashCode(this.rating);  
	}  


	//The default behaviour of this test is to 
	//trigger a call to 'equals()' on the User objects. 
	//We haven't implemented this method, so the default 
	//behaviour is to do an identity comparison:

	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof Rating)
		{
			final Rating other = (Rating) obj;
			return Objects.equal(rating,   other.rating) ;
		}
		else
		{
			return false;
		}
	}
}
