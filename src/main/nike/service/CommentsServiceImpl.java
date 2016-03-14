package nike.service;

import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import nike.dao.CommentsDAO;
import nike.dao.TitleDAO;
import nike.dao.UserDAO;
import nike.model.Comments;
import nike.model.Title;
import nike.model.User;
import nike.exception.CommentBadRequestException;
import nike.exception.TitleNotFoundException;
import nike.exception.UserNotFoundException;
import nike.exception.UserUnathorizedException;

@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {

	@Autowired
	@Qualifier("titleDAOImpl")
	private TitleDAO titleDAO;

	@Autowired
	@Qualifier("userDAOImpl")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("commentsDAOImpl")
	private CommentsDAO commentsDAO;

	@Override
	public Comments addComments(int titleId, int userId, Comments comment)
			throws UserNotFoundException, TitleNotFoundException, CommentBadRequestException {
		if (comment.getComments().isEmpty()) {
			throw new CommentBadRequestException();
		}
		Title title = titleDAO.getTitleById(titleId);
		User user = userDAO.findUserById(userId);
		if (title == null) {
			throw new TitleNotFoundException();			
		}
		if (user == null) {			
			throw new UserNotFoundException();
		}
		comment.setTitle(title);
		comment.setUser(user);
		return commentsDAO.addComments(titleId,userId, comment);
	}

	@Override
	public Comments removeComments(int titleId, int userId, int commentId)throws UserUnathorizedException, 
	CommentBadRequestException, TitleNotFoundException, UserNotFoundException {
		User user = userDAO.findUserById(userId);
		Title title = titleDAO.getTitleById(titleId);
		Comments comment = commentsDAO.getCommentByID(commentId);
		if(comment == null){
			throw new CommentBadRequestException();
		}
		if(user == null){
			throw new UserNotFoundException();
		}		
		if(title == null){
			throw new TitleNotFoundException();
		}
		if(comment.getUser().getId() != user.getId()){
			throw new UserUnathorizedException();
		}
		if(comment.getTitle().getId() != title.getId()){
			throw new CommentBadRequestException();
		}
		return commentsDAO.removeComments(commentId);
	}

	@Override
	public Comments updateComment(int titleId, int userId, Comments comment) throws UserUnathorizedException, 
		CommentBadRequestException, TitleNotFoundException, UserNotFoundException {
		User user = userDAO.findUserById(userId);
		Title title = titleDAO.getTitleById(titleId);
		if(comment == null){
			throw new CommentBadRequestException();
		}
		if(user == null){
			throw new UserNotFoundException();
		}		
		if(title == null){
			throw new TitleNotFoundException();
		}
		if(comment.getUser().getId() != user.getId()){
			throw new UserUnathorizedException();
		}
		if(comment.getTitle().getId() != title.getId()){
			throw new CommentBadRequestException();
		}
		return commentsDAO.updateComment(comment);
	}

	@Override
	public List<Comments> getCommentsForTitle(int titleId) throws TitleNotFoundException {
		Title title = titleDAO.getTitleById(titleId);
		if(title == null){
			throw new TitleNotFoundException();
		}				
		return commentsDAO.getCommentsForTitle(titleId);
	}

	@Override
	public List<Comments> getCommentsForUser(int userId) throws UserNotFoundException {
		User user = userDAO.findUserById(userId);
		if(user == null){
			throw new UserNotFoundException();
		}
		return commentsDAO.getCommentsForUser(userId);
	}
}
