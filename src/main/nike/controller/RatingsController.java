package nike.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nike.model.Comments;
import nike.model.Ratings;
import nike.model.Title;
import nike.exception.RatingBadRequestException;
import nike.exception.TitleNotFoundException;
import nike.exception.UserNotFoundException;
import nike.exception.UserUnathorizedException;
import nike.service.RatingsService;

@RestController
@RequestMapping("/ratings")
@Api(tags="ratings")
public class RatingsController {

	@Autowired
	@Qualifier("ratingsServiceImpl")
	private RatingsService ratingsServiceImpl;

	@RequestMapping(value = "/new/{titleId}/{userId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Create Rating", notes="Create and return Rating")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=400, message="Bad Request"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public Ratings addRating(@PathVariable("titleId") int titleId,@PathVariable("userId") int userId,
			@RequestBody Ratings rating) throws UserUnathorizedException, TitleNotFoundException, RatingBadRequestException {
		return this.ratingsServiceImpl.addRating(userId, titleId, rating);
	}

	@RequestMapping(value = "/{titleId}/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find Rating By User Id", notes="Finds Rating of a particular User using their Id")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=404, message="Not Found"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public Ratings getRatingForUser(@PathVariable("titleId") int titleId, @PathVariable("userId") int userId)
			throws UserUnathorizedException, UserNotFoundException , TitleNotFoundException{
		return this.ratingsServiceImpl.getRatingByUser(userId, titleId);
	}

	@RequestMapping(value = "/title/{titleId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find Rating for a Title", notes="Finds Rating of a particular Title using their Id")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=404, message="Not Found"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public List<Ratings> getRatingForTitle(@PathVariable("id") int titleId)
			throws TitleNotFoundException {
		return this.ratingsServiceImpl.getRatingByTitle(titleId);
	}

	@RequestMapping(value = "/average/{titleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Calcualtes Average Rating of a Title", notes="Returns Double value")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=404, message="Not Found"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public int getAvgRatingForTitle(@PathVariable("titleId") int titleId)
			throws TitleNotFoundException {
		return this.ratingsServiceImpl.getAverageRatingForTitle(titleId);
	}
	
	@RequestMapping(value = "/toppicks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Gets top 10 Titles", notes="Returns the top 10 Titles with highest rating")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=404, message="Not Found"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public List<Title> getTopRatedTitle(){
		return this.ratingsServiceImpl.getTopRatedTitle();
	}

}
