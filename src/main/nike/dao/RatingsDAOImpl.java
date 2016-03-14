package nike.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nike.model.Comments;
import nike.model.Ratings;

@Repository
public class RatingsDAOImpl implements RatingsDAO {

	@PersistenceContext
	private EntityManager em;


	@Override
	public Ratings addRating(Ratings rating) {
		em.persist(rating);
		return rating;
	}

	@Override
	public List<Ratings> getRatingByUser(int userId) {
		return (List<Ratings>) em.createQuery("SELECT * FROM Rating WHERE user_Id = :userId").setParameter("userId", userId).getResultList();
	}

	@Override
	public List<Ratings> getRatingByTitle(int titleId) {
		return (List<Ratings>) em.createQuery("SELECT * FROM Rating WHERE title_id = :titleId").setParameter("titleId", titleId).getResultList();
	}

	@Override
	public double getAverageRatingForTitle(int titleId) {
		return (double) em.createQuery("SELECT AVG(rating) FROM Rating WHERE title_id = :titleId").setParameter("titleId", titleId).getFirstResult();
	}
	
	@Override
	public List<Ratings> getTopRatedTitle() {
		return (List<Ratings>) em.createQuery("SELECT * FROM Rating WHERE ORDER BY rating DESC LIMIT 10").getResultList();
	}
	
	@Override
	public void removeRatingForUser(int userId) {
		em.createQuery("DELETE FROM Rating WHERE user_Id = :userId").setParameter("userId", userId);
	}
	
	@Override
	public void removeRatingForTitle(int titleId) {		
		em.createQuery("DELETE FROM Rating WHERE title_Id = :titleId").setParameter("titleId", titleId);
	}
}
