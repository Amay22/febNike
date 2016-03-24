package nike.controller;

import nike.model.User;
import nike.exception.UserAlreadyExistsException;
import nike.exception.UserNotFoundException;
import nike.exception.UserUnathorizedException;
import nike.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Date;
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

@RestController
@RequestMapping("/users")
@Api(tags="users")
public class UserController {
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@RequestMapping(method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find All Users", notes="Returns a list of the users in the system.")
	@ApiResponses(value={@ApiResponse(code=200, message="Success"), 
						 @ApiResponse(code=500, message="Internal Server Error")})
	public List<User> findAll() {
		return userService.findAllUsers();
	}
	
	@SuppressWarnings("unused")
    private static class UserLogin {
        public String email;
        public String password;
    }

    @SuppressWarnings("unused")
    private static class LoginResponse {
        public String token;
        public String role;
        public int id;

        public LoginResponse(final String token, final String role, final int id){
            this.token = token;
            this.role = role;
            this.id = id;
        }
    }
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse userlogin(@RequestBody final UserLogin login)
        throws UserUnathorizedException, UserNotFoundException {
    	User user = userService.login(login.email,login.password);
        if (user == null) {
            throw new UserUnathorizedException();
        }
        String role = "user";
        if(login.email.equals(login.email)){
        	role = "admin";
        }
        return new LoginResponse(Jwts.builder().setSubject(login.email)
            .claim("role", role).setIssuedAt(new Date())
            .signWith(SignatureAlgorithm.HS256, "secretkey").compact(), role , user.getId());
    }
	
	@RequestMapping(value="/id/{id}",method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find User By Id", notes="Returns a user by it's id exists.")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=404, message="Not Found"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public User findUserId(@PathVariable("id") int id) throws UserNotFoundException {		
		return userService.findUserById(id);
	}
	
	@RequestMapping(value="/email/{email}/{ext}",method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Find User By Email", notes="Returns a user by it's Email exists.")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=404, message="Not Found"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public User findUserEmail(@PathVariable("email") String email, @PathVariable("ext") String ext) throws UserNotFoundException {
		return userService.findUserByEmail(email + "." + ext);
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Create User", notes="Create and return user")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=400, message="Bad Request"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public User create (@RequestBody User user) throws UserAlreadyExistsException {
		return userService.createUser(user);
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Update User", notes="Update an existing user")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
						  @ApiResponse(code=400, message="Bad Request"),
						  @ApiResponse(code=404, message="Not Found"),
						  @ApiResponse(code=500, message="Internal Server Error")})
	public User update (@PathVariable("id") int id, @RequestBody User user) throws UserNotFoundException {
		return userService.updateUser(id, user);
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Delete User", notes="Delete an existing user")
	@ApiResponses(value={ @ApiResponse(code=200, message="Success"),
		     			  @ApiResponse(code=404, message="Not Found"),
		     			  @ApiResponse(code=500, message="Internal Server Error")})
	public void delete (@PathVariable("id") int id) throws UserNotFoundException {
		userService.deleteUser(id);
	}
}