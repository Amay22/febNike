package nike.service;

import java.util.List;

import org.springframework.stereotype.Service;

import nike.model.Title;
import nike.exception.TitleBadRequestException;
import nike.exception.TitleNotFoundException;

@Service
public interface TitleService {

	public Title addTitle(Title t) throws TitleBadRequestException;

	public Title updateTitle(Title t) throws TitleNotFoundException, TitleBadRequestException;

	public Title removeTitle(int id) throws TitleNotFoundException;

	public List<Title> listTitles();

	public Title getTitleById(int id) throws TitleNotFoundException;

	public List<Title> getTitleBySearchTerm(String title);

	public List<Title> getTitleByYear(int year);

	public List<Title> getTitleByType(String type);

	public List<Title> getTitleByGenre(String genre);
}
