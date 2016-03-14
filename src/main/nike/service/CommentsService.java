package nike.service;

import java.util.List;

import org.springframework.stereotype.Service;

import nike.model.Comments;
import nike.exception.CommentBadRequestException;
import nike.exception.TitleNotFoundException;
import nike.exception.UserNotFoundException;
import nike.exception.UserUnathorizedException;

@Service
public interface CommentsService {

	public Comments addComments(int titleId, int userId, Comments c)throws UserNotFoundException, TitleNotFoundException, CommentBadRequestException;

	public Comments removeComments(int titleId, int userId,int commentId)throws UserUnathorizedException,CommentBadRequestException,TitleNotFoundException, UserNotFoundException;

	public Comments updateComment(int titleId, int userId,Comments c)throws UserUnathorizedException,CommentBadRequestException,TitleNotFoundException, UserNotFoundException;

	public List<Comments> getCommentsForTitle(int titleId)throws TitleNotFoundException;

	public List<Comments> getCommentsForUser(int userId) throws UserNotFoundException, UserUnathorizedException;

}
