package models;
import static models.Fixtures.movies;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import utils.Serializer;
import utils.XMLSerializer;
import controllers.Data;
import controllers.MovieRecommenderAPI;
public class MovieTest 
{

	Movie movie = new Movie ("GavsFilm", "2018","www.films.co.uk");
	MovieRecommenderAPI movieRecommender;
	@Test
	public void testCreate()
	{
		assertEquals ("GavsFilm",                movie.title);
		assertEquals ("2018",             		   movie.year);
		assertEquals ("www.films.co.uk",              movie.url);   
	}

	@Test
	public void testIds()
	{
		//test id size
		Set<Long> ids = new HashSet<>();
		for (Movie movie:movies)
		{
			ids.add(movie.id);
		}
		assertEquals (movies.length, ids.size());
		//test that each objects id witch each other to ensure they are the same
		for(int i = 0; i<movies.length;i++)
		{
			assertEquals(movies[i].id,movies[i].id);
		}
	}

	@Test
	public void testToString()
	{
		assertEquals(movie.toString(),movie.toString());
	}


	@Test
	public void testEquals()
	{
		Movie movie2 = new Movie ("gavsFilm", "2018","www.gavsfilm.co.uk"); 
		Movie movie3  = new Movie ("pokemon", "2015","www.gavsfilm.co.uk"); 

		assertEquals(movie, movie);
		assertEquals(movie, movie2);
		assertNotEquals(movie, movie3);
	} 

	@Test
	public void getMovieById() throws Exception
	{
		File usersFile = new File("testdatastore.xml");
		Serializer serializer = new XMLSerializer(usersFile);
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(serializer);
		Data data = new Data();

		List<Movie> movies = data.importMovies("data/items5.dat");
		for(Movie movie : movies)
		{
			movieRecommender.addMovie(movie);
		}
		movieRecommender.store();

		//loads movieRecommender2 with the new data and tests their equality
		MovieRecommenderAPI movieRecommender2 =  new MovieRecommenderAPI(serializer);
		movieRecommender2.load();

		/*the for loop will go through each user in movieRecommender
		 * and will get the id's of each user. It will then test the movieRecommender
		 * id's against the id's of movieRecommender2 assuring that the getUser(id) function
		 * is working correctly.
		 */
		for (Movie movie : movieRecommender.getMovies())
		{
			assertEquals(movie.id,movieRecommender2.getMovie(movie.id).id);
		}
	}
}



