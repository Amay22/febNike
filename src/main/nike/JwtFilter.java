package nike;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import nike.exception.UserUnathorizedException;

@WebFilter(urlPatterns="/delete/*")
public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI().toString();
        if(uri.contains("/delete/") || uri.contains("titles/new/") || uri.contains("titles/update/")){
	
	        final String authHeader = request.getHeader("Authorization");
	        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	            throw new ServletException("Missing or invalid Authorization header.");
	        }
	        
	        final String token = authHeader.substring(7); // The part after "Bearer "
	
	        try {
	            final Claims claims = Jwts.parser().setSigningKey("secretkey")
	                .parseClaimsJws(token).getBody();
	            System.out.println("inininnnininnininin" + claims.get("role"));
	            if(!claims.get("roles").equals("admin") ){
	            	throw new ServletException("Not an Admin Access Denied");
	            }
	            request.setAttribute("claims", claims);
	        }
	        catch (final SignatureException e) {
	            throw new ServletException("Invalid token.");
	        }
        }else{
        	HttpServletResponse response = (HttpServletResponse) res;
    		response.setHeader("Access-Control-Allow-Origin", "*");
    		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
    		response.setHeader("Access-Control-Max-Age", "3600");
    		response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type,  Access-Control-Allow-Headers, Authorization");
        }
        chain.doFilter(req, res);
    }

}