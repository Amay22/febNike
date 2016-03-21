package nike.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import nike.dao.RatingsDAO;
import nike.dao.TitleDAO;
import nike.dao.UserDAO;
import nike.model.Ratings;
import nike.model.Title;
import nike.model.User;
import nike.exception.RatingBadRequestException;
import nike.exception.TitleNotFoundException;
import nike.exception.UserNotFoundException;
import nike.exception.UserUnathorizedException;

@Service
@Transactional
public class RatingsServiceImpl implements RatingsService {
	
	@Autowired
	@Qualifier("titleDAOImpl")
	private TitleDAO titleDAO;

	@Autowired
	@Qualifier("userDAOImpl")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("ratingsDAOImpl")
	private RatingsDAO ratingDAO;


	@Override
	public Ratings addRating(int userId, int titleId, Ratings rating)
			throws UserUnathorizedException , TitleNotFoundException, RatingBadRequestException{
		if(rating.getRating() < 0 || rating.getRating() > 10){
			throw new RatingBadRequestException();
		}
		Title title = titleDAO.getTitleById(titleId);
		User user = userDAO.findUserById(userId);
		if (title == null) {
			throw new TitleNotFoundException();
		}		
		if (user == null) {
			throw new UserUnathorizedException();
		}
		rating.setTitle(title);
		rating.setUser(user);
		return ratingDAO.addRating(rating);
	}

	@Override
	public Ratings getRatingByUser(int userId, int titleId) throws UserNotFoundException , TitleNotFoundException{
		User user = userDAO.findUserById(userId);
		Title title = titleDAO.getTitleById(titleId);
		if (title == null) {
			throw new TitleNotFoundException();
		}
		if (user == null) {
			throw new UserNotFoundException();
		}
		return ratingDAO.getRatingByUser(userId , titleId);
	}

	@Override
	public List<Ratings> getRatingByTitle(int titleId) throws TitleNotFoundException {
		Title title = titleDAO.getTitleById(titleId);
		if (title == null) {
			throw new TitleNotFoundException();
		}
		return ratingDAO.getRatingByTitle(titleId);
	}

	@Override
	public int getAverageRatingForTitle(int titleId) throws TitleNotFoundException {
		Title title = titleDAO.getTitleById(titleId);
		if (title == null) {
			throw new TitleNotFoundException();			
		} 
		return ratingDAO.getAverageRatingForTitle(titleId);
	}

	@Override
	public List<Title> getTopRatedTitle() {
		return ratingDAO.getTopRatedTitle();
	}
}
