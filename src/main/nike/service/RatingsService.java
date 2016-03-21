package nike.service;

import java.util.List;

import org.springframework.stereotype.Service;

import nike.model.Ratings;
import nike.model.Title;
import nike.exception.RatingBadRequestException;
import nike.exception.TitleNotFoundException;
import nike.exception.UserNotFoundException;
import nike.exception.UserUnathorizedException;

@Service
public interface RatingsService {
	
	public Ratings addRating(int userId,int titleId,Ratings r) throws UserUnathorizedException, TitleNotFoundException, RatingBadRequestException;

	public Ratings getRatingByUser(int userId, int titleId) throws UserNotFoundException, TitleNotFoundException;

	public List<Ratings> getRatingByTitle(int titleId)throws TitleNotFoundException;

	public int getAverageRatingForTitle(int titleId) throws TitleNotFoundException;
	
	public List<Title> getTopRatedTitle();
}
