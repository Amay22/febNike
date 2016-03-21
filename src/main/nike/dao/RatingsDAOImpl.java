package nike.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nike.model.Comments;
import nike.model.Ratings;
import nike.model.Title;

@Repository
public class RatingsDAOImpl implements RatingsDAO {

	@PersistenceContext
	private EntityManager em;


	@Override
	public Ratings addRating(Ratings rating) {
		if(getRatingByUser(rating.getUser().getId(), rating.getTitle().getId()) != null){
			em.createQuery("DELETE FROM Ratings WHERE user_Id = :userId and title_Id = :titleId")
			.setParameter("userId", rating.getUser().getId())
			.setParameter("titleId",  rating.getTitle().getId()).executeUpdate();
		}
		em.persist(rating);
		return rating;
	}

	@Override
	public Ratings getRatingByUser(int userId, int titleId) {
		List<Ratings> rating  = (List<Ratings>) em.createQuery("FROM Ratings WHERE user_Id = :userId and title_Id = :titleId").setParameter("userId", userId).setParameter("titleId", titleId).getResultList();
		return  rating.size() > 0 ? rating.get(0) : null;
	}

	@Override
	public List<Ratings> getRatingByTitle(int titleId) {
		return (List<Ratings>) em.createQuery("FROM Ratings WHERE title_id = :titleId").setParameter("titleId", titleId).getResultList();
	}

	@Override
	public int getAverageRatingForTitle(int titleId) {
		return (int)((double)em.createQuery("SELECT  AVG(rating) FROM Ratings WHERE title_id = :titleId").setParameter("titleId", titleId).getSingleResult());
	}
	
	@Override
	public List<Title> getTopRatedTitle() {
		List<Ratings> topRatings =  em.createQuery("FROM Ratings ORDER BY rating DESC").getResultList();
		HashSet<Title> titles = new HashSet<>();
		topRatings.forEach((rating) -> titles.add(rating.getTitle()));
		return new ArrayList<Title>(titles);
	}
	
	@Override
	public void removeRatingForUser(int userId) {
		em.createQuery("DELETE FROM  Ratings WHERE user_Id = :userId").setParameter("userId", userId).executeUpdate();
	}
	
	@Override
	public void removeRatingForTitle(int titleId) {		
		em.createQuery("DELETE FROM  Ratings WHERE title_Id = :titleId").setParameter("titleId", titleId).executeUpdate();
	}
}
