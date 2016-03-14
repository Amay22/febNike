package nike.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import nike.dao.CommentsDAO;
import nike.dao.RatingsDAO;
import nike.dao.TitleDAO;
import nike.model.Comments;
import nike.model.Ratings;
import nike.model.Title;
import nike.exception.TitleBadRequestException;
import nike.exception.TitleNotFoundException;

@Service
@Transactional
public class TitleServiceImpl implements TitleService{

	@Autowired
	@Qualifier("titleDAOImpl")
	private TitleDAO titleDAO;
	
	@Autowired
	@Qualifier("commentsDAOImpl")
	private CommentsDAO commentsDAO;

	@Autowired
	@Qualifier("ratingsDAOImpl")
	private RatingsDAO ratingDAO;
	
	@Override
	public Title addTitle(Title title) throws TitleBadRequestException {
		Title existing_title = titleDAO.getTitleById(title.getId());
		if (title.getTitle() == null || existing_title != null) {
			throw new TitleBadRequestException();
		}
		return titleDAO.addTitle(title);
	}

	@Override
	public Title updateTitle(Title title) throws TitleNotFoundException, TitleBadRequestException {
		Title existing_title = titleDAO.getTitleById(title.getId());
		if (existing_title == null) {
			throw new TitleNotFoundException();			
		}
		if(title.equals(existing_title)){
			throw new TitleBadRequestException();		
		}
		return titleDAO.updateTitle(title);
	}

	@Override
	public Title removeTitle(int id) throws TitleNotFoundException {
		Title existing_title = titleDAO.getTitleById(id);
		if (existing_title == null) {
			throw new TitleNotFoundException();
		}else{
			List<Comments> comments= commentsDAO.getCommentsForTitle(id);
			List<Ratings> ratings= ratingDAO.getRatingByTitle(id);
			if(comments != null){
				commentsDAO.removeCommentsForTitle(id);		
			}
			if(ratings != null){
				ratingDAO.removeRatingForTitle(id);
			}
			return titleDAO.removeTitle(id);
		}
	}

	@Override
	public List<Title> listTitles() {
		return titleDAO.listTitles();
	}

	@Override
	public Title getTitleById(int id) throws TitleNotFoundException {
		Title title =  titleDAO.getTitleById(id);
		if(title == null) {
			throw new TitleNotFoundException();
		}
		return title;		
	}

	@Override
	public List<Title> getTitleBySearchTerm(String title) {
		return titleDAO.getTitleBySearchTerm(title);
	}

	@Override
	public List<Title> getTitleByYear(int year) {
		return titleDAO.getTitleByYear(year);
	}

	@Override
	public List<Title> getTitleByType(String type) {
		return titleDAO.getTitleByType(type);
	}

	@Override
	public List<Title> getTitleByGenre(String genre) {
		return titleDAO.getTitleByGenre(genre);
	}
}
