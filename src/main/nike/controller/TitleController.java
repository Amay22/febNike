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
import nike.model.Title;
import nike.exception.TitleBadRequestException;
import nike.exception.TitleNotFoundException;
import nike.service.TitleService;


@RestController
@RequestMapping("/titles")
@Api(tags="titles")
public class TitleController {

	@Autowired
	@Qualifier("titleServiceImpl")
	private TitleService titleService;

	@RequestMapping(method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find All Titles", notes="Returns a list of the titles in the system.")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"), 
			 			  @ApiResponse(code=500, message="Internal Server Error")})
	public List<Title> getAllTitles() {
		return titleService.listTitles();
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find Title By Id", notes="Returns a Title by it's id if it exists.")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=404, message="Not Found"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public Title getTitleById(@PathVariable("id") int id) throws TitleNotFoundException{
		return titleService.getTitleById(id);
	}

	@RequestMapping(value="/new", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Create Title", notes="Create and return Title")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=400, message="Bad Request"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public Title addTitle(@RequestBody Title title) throws TitleBadRequestException{
		return titleService.addTitle(title);	
	}
	
	@RequestMapping(value="/update", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Update Title", notes="Update an existing Title")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=400, message="Bad Request"),
						  @ApiResponse(code=404, message="Not Found"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public Title updateTitle(@RequestBody Title title) throws TitleNotFoundException, TitleBadRequestException{
		return titleService.updateTitle(title);
	}
	
	@RequestMapping(value="/delete/{id}",method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Delete Title", notes="Delete an existing Title")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
		     			  @ApiResponse(code=404, message="Not Found"),
		     			  @ApiResponse(code=500, message="Internal Server Error")})
	public Title deleteTitleById(@PathVariable("id") int id) throws TitleNotFoundException{
		return titleService.removeTitle(id);
	}
	
	@RequestMapping(value="/title/{title}",method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Search Title", notes="Search Title by likeness of name")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
		     			  @ApiResponse(code=404, message="Not Found"),
		     			  @ApiResponse(code=500, message="Internal Server Error")})
	public List<Title> getTitleByTitle(@PathVariable("title") String title) {
		return titleService.getTitleBySearchTerm(title);
	}
	
	@RequestMapping(value="/year/{year}",method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find Title by Year", notes="Get All Title in a particular Year")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
		     			  @ApiResponse(code=404, message="Not Found"),
		     			  @ApiResponse(code=500, message="Internal Server Error")})
	public List<Title> getTitleByYear(@PathVariable("year") int year) {
		return titleService.getTitleByYear(year);
	}
	
	@RequestMapping(value="/genre/{genre}",method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find Title by Genre", notes="Get All Title in a particular Genre")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
		     			  @ApiResponse(code=404, message="Not Found"),
		     			  @ApiResponse(code=500, message="Internal Server Error")})
	public List<Title> getTitleByGenre(@PathVariable("genre") String genre) {
		return titleService.getTitleByGenre(genre);
	}
	
	@RequestMapping(value="/type/{type}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find Title by Type", notes="Get All Title in a particular Type")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
		     			  @ApiResponse(code=404, message="Not Found"),
		     			  @ApiResponse(code=500, message="Internal Server Error")})
	public List<Title> getTitleByType(@PathVariable("type") String type) {
		return titleService.getTitleByType(type);
	}
}
