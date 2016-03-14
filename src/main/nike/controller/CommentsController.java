package nike.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nike.model.Comments;
import nike.exception.CommentBadRequestException;
import nike.exception.TitleNotFoundException;
import nike.exception.UserNotFoundException;
import nike.exception.UserUnathorizedException;
import nike.service.CommentsService;

@RestController
@RequestMapping("/comments")
@Api(tags="comments")
public class CommentsController {

	@Autowired
	@Qualifier("commentsServiceImpl")
	private CommentsService commentsService;

	@RequestMapping(value = "{titleId}/{userId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Create Comments", notes="Create and return Comments")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=400, message="Bad Request"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public Comments addComments(@PathVariable("titleId") int titleId,
			@PathVariable("userId") int userId, @RequestBody Comments c)
			throws UserNotFoundException, TitleNotFoundException, CommentBadRequestException {
		return this.commentsService.addComments(titleId, userId, c);
	}

	@RequestMapping(value = "{titleId}/{userId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Update Comment", notes="Update an existing Comment")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=400, message="Bad Request"),
						  @ApiResponse(code=404, message="Not Found"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public Comments updateComments(@PathVariable("titleId") int titleId,
			@PathVariable("userId") int userId, @RequestBody Comments c)
			throws UserUnathorizedException, CommentBadRequestException, TitleNotFoundException ,UserNotFoundException{
		return this.commentsService.updateComment(titleId, userId, c);
	}

	@RequestMapping(value = "{titleId}/{userId}/{commentId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Delete Comment", notes="Delete an existing Comment")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
		     			  @ApiResponse(code=404, message="Not Found"),
		     			  @ApiResponse(code=500, message="Internal Server Error")})
	public Comments deleteComments(@PathVariable("titleId") int titleId,
			@PathVariable("userId") int userId,
			@PathVariable("commentId") int commentId) 
					throws UserUnathorizedException, UserNotFoundException, CommentBadRequestException, TitleNotFoundException{
		return this.commentsService.removeComments(titleId, userId, commentId);
	}

	@RequestMapping(value = "/title/{titleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find Rating By Title Id", notes="Finds Rating of a particular Title using their Id")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=404, message="Not Found"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public List<Comments> getCommentsForTitle(
			@PathVariable("titleId") int titleId) throws TitleNotFoundException {
		return this.commentsService.getCommentsForTitle(titleId);
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find Rating By User Id", notes="Finds Rating of a particular User using their Id")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=404, message="Not Found"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public List<Comments> getCommentsForUser(@PathVariable("userId") int userId)
			throws UserNotFoundException, UserUnathorizedException {
		return this.commentsService.getCommentsForUser(userId);
	}
}
