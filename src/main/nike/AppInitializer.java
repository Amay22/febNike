package nike;


import javax.servlet.Filter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {AppConfig.class, SwaggerConfig.class, JPAConfig.class, JwtFilter.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/api/*"};
	}
	
	@Override
    protected Filter[] getServletFilters() {
        Filter [] singletonCorsFilter = { new CORSFilter() };
        return singletonCorsFilter;
    }
}
