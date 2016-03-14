package nike.service;

import java.util.List;

import org.springframework.stereotype.Service;

import nike.model.Ratings;
import nike.exception.RatingBadRequestException;
import nike.exception.TitleNotFoundException;
import nike.exception.UserNotFoundException;
import nike.exception.UserUnathorizedException;

@Service
public interface RatingsService {
	
	public Ratings addRating(int userId,int titleId,Ratings r) throws UserUnathorizedException, TitleNotFoundException, RatingBadRequestException;

	public List<Ratings> getRatingByUser(int userId) throws UserNotFoundException;

	public List<Ratings> getRatingByTitle(int titleId)throws TitleNotFoundException;

	public Double getAverageRatingForTitle(int titleId) throws TitleNotFoundException;
	
	public List<Ratings> getTopRatedTitle();
}
