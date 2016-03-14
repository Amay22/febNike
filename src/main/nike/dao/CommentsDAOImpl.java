package nike.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nike.model.Comments;
import nike.model.User;

@Repository
@Transactional
public class CommentsDAOImpl implements CommentsDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Comments addComments(int titleId, int userId, Comments comment) {
		em.persist(comment);
		return comment;
	}

	@Override
	public Comments removeComments(int id) {
		Comments delete_comment = getCommentByID(id);
		em.remove(delete_comment);
		return delete_comment;
	}

	@Override
	public Comments getCommentByID(int id) {
		return em.find(Comments.class, id);
	}

	@Override
	public Comments updateComment(Comments comment) {
		return em.merge(comment);
	}

	@Override
	public List<Comments> getCommentsForTitle(int titleId) {
		return (List<Comments>) em.createQuery("SELECT * FROM Comments WHERE title_Id = :titleId").setParameter("titleId", titleId).getResultList();
	}

	@Override
	public List<Comments> getCommentsForUser(int userId) {
		return (List<Comments>) em.createQuery("SELECT * FROM Comments WHERE user_Id = :userId").setParameter("userId", userId).getResultList();		
	}

	@Override
	public void removeCommentsForUser(int userId) {
		em.createQuery("DELETE FROM Comments WHERE user_Id = :userId").setParameter("userId", userId);
	}

	@Override
	public void removeCommentsForTitle(int titleId) {
		em.createQuery("DELETE FROM Comments WHERE title_Id = :titleId").setParameter("titleId", titleId);
	}
}
