package nike.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import nike.model.Ratings;

@Repository
public interface RatingsDAO {

	public Ratings addRating(Ratings r);

	public List<Ratings> getRatingByUser(int userId);

	public List<Ratings> getRatingByTitle(int titleId);

	public double getAverageRatingForTitle(int titleId);

	public void removeRatingForUser(int userId);

	public void removeRatingForTitle(int titleId);
	
	public List<Ratings> getTopRatedTitle();
}
