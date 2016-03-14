package nike.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nike.model.Ratings;
import nike.model.Title;
import nike.model.User;

@Repository
@Transactional
public class TitleDAOImpl implements TitleDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Title addTitle(Title title) {
		em.persist(title);
		return title;
	}

	@Override
	public Title updateTitle(Title title) {
		return em.merge(title);
	}

	@Override
	public Title removeTitle(int id) {
		Title title = getTitleById(id);
		em.remove(title);
		return title;
	}

	@Override
	public List<Title> listTitles() {
    	return (List<Title>) em.createQuery("SELECT * FROM titles").getResultList();
	}

	@Override
	public Title getTitleById(int id) {
		return em.find(Title.class, id);
	}

	@Override
	public List<Title> getTitleBySearchTerm(String title) {
		return (List<Title>) em.createQuery("SELECT * FROM Title WHERE title like \"%:title%\"").setParameter("title", title).getResultList();
	}

	@Override
	public List<Title> getTitleByYear(int year) {
		return (List<Title>) em.createQuery("SELECT * FROM Title WHERE year = :year").setParameter("year", year).getResultList();
	}

	@Override
	public List<Title> getTitleByType(String type) {
		return (List<Title>) em.createQuery("SELECT * FROM Title WHERE type = :type").setParameter("type", type).getResultList();
	}

	@Override
	public List<Title> getTitleByGenre(String genre) {
		return (List<Title>) em.createQuery("SELECT * FROM Title WHERE genre = :genre").setParameter("genre", genre).getResultList();
	}
}
