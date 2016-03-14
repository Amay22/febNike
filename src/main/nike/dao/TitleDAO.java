package nike.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import nike.model.Title;

@Repository
public interface TitleDAO {
	
	public Title addTitle(Title t);

	public Title updateTitle(Title t);

	public Title removeTitle(int id);
	
	public List<Title> listTitles();
	
	public Title getTitleById(int id);
	
	public List<Title> getTitleBySearchTerm(String title);
	
	public List<Title> getTitleByYear(int year);
	
	public List<Title> getTitleByType(String type);
	
	public List<Title> getTitleByGenre(String genre);
	
}
