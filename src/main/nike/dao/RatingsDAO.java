package nike.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import nike.model.Ratings;
import nike.model.Title;

@Repository
public interface RatingsDAO {

	public Ratings addRating(Ratings r);

	public Ratings getRatingByUser(int userId, int titleId);

	public List<Ratings> getRatingByTitle(int titleId);

	public int getAverageRatingForTitle(int titleId);

	public void removeRatingForUser(int userId);

	public void removeRatingForTitle(int titleId);
	
	public List<Title> getTopRatedTitle();
}
