package models;

public class Fixtures
{
	public static User[] users =
		{
			new User ("gavin", "fennelly", 21, "m",  "programmer"),
			new User ("adam",  "daily", 19, "m",   "chief"),
			new User ("mark",  "duffy", 20, "m",   "manager"),
			new User ("peter","murphy", 11, "f", "stop assissant")
		};

	public static Movie[] movies =
		{
			new Movie("gavsmovie", "2011","www.gav.com"),
			new Movie("adamsmovie", "2012","www.adam.com"),
			new Movie("marksmovie", "2013","www.mark.com"),
			new Movie("petersmovie", "2014","www.peter.com"),
		};
	public static Rating[] ratings = 
		{
			new Rating(users[0].id,movies[0].id,3),
			new Rating(users[0].id,movies[1].id,0),
			new Rating(users[1].id,movies[2].id,5),
			new Rating(users[1].id,movies[3].id,2),
		};
}