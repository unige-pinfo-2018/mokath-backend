/**
 *
 */
package ch.mokath.uniknowledgerestapi.business.service;

/**z*/
import ch.mokath.uniknowledgerestapi.utils.CustomException;
/*z*/

import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.NoSuchElementException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.mokath.uniknowledgerestapi.dom.Answer;
import ch.mokath.uniknowledgerestapi.dom.Points;
import ch.mokath.uniknowledgerestapi.dom.Question;
import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.utils.DBHelper;
import ch.mokath.uniknowledgerestapi.utils.CustomException;


import java.util.HashSet;

/**
 * @author matteo113
 * @author zue
 */
@Stateless
public class PostsServiceImpl implements PostsService {

	@PersistenceContext
	private EntityManager em;
	private DBHelper DBHelper = new DBHelper();

	/**********************************************************************
	 * QUESTIONS *
	 **********************************************************************/

	@Override
	public void createQuestion(Question question, User user) throws CustomException {
        try{
            User author = em.merge(user);
            author.addQuestion(question);
            question.setAuthor(author);
            question.setCreated(new Date());
            author.addPoints(Points.QUESTION_CREATED);
            em.persist(question);
		} catch (NullPointerException ne) {
            throw new CustomException("empty question");
        }
	}

	@Override
	public Set<Answer> getQuestionAnswers(final String qid) throws CustomException {
        try{
            Long qidl = Long.valueOf(qid);
            Question question=em.find(Question.class,qidl);
            Set<Answer> answers=question.getAnswers();
            answers.toString(); //This is needed otherwise org.hibernate.LazyInitializationException
            return answers;
		}catch(NullPointerException ne){
            throw new CustomException("empty question");
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong question ID");
		}
	}

	@Override
	public void upvoteQuestion(Question q, User u) throws NoSuchElementException {
		User user = em.merge(u);
		Question question = em.merge(q);

		if (!user.equals(question.getAuthor())){ //Do not add points to own questions
            User question_author = em.merge(question.getAuthor());
            question_author.addPoints(Points.QUESTION_LIKED);
        }
		user.addLikedQuestion(question);
	}

	@Override
	public void followQuestion(Question q, User u) throws NoSuchElementException {
		User user = em.merge(u);
		Question question = em.merge(q);

        if (!user.equals(question.getAuthor())){ //Do not add points to follow own questions
            User question_author = em.merge(question.getAuthor());
            question_author.addPoints(Points.QUESTION_FOLLOWED);
        }
		user.addFollowedQuestion(question);
	}

	@Override
/**z	public void deleteQuestion(Question q, User u) {
		User user = em.merge(u);
		Question question = em.merge(q);

		// TODO externalize check
		if (user.equals(question.getAuthor())) {
			
//			question.getAnswers().clear();
//			question.getUpvotes().clear();
			
			em.merge(question);

			user.removeQuestion(question);
            question.getFollowers();
			
			em.remove(question);
        }
	}*/
	public void deleteQuestion(Question q, User u) throws CustomException {
		User user = em.merge(u);
		Question question = em.merge(q);

		// TODO externalize check
		if (user.equals(question.getAuthor())) {
			
//			question.getAnswers().clear();
//			question.getUpvotes().clear();
			
			em.merge(question);

			user.removeQuestion(question);
            question.getFollowers();
			
			em.remove(question);
            user.addPoints(Points.QUESTION_REMOVED);
        }
	}

	@Override
	public void editQuestion(Question oq, Question uq, User u) {
		User user = em.merge(u);
		Question question = em.merge(oq);

		if (user.equals(question.getAuthor())) {
			question.setText(uq.getText());
			question.setTitle(uq.getTitle());
			question.setDomains(uq.getDomains());
		}
		em.merge(question);
	}

	/**********************************************************************
	 * ANSWERS *
	 **********************************************************************/

	@Override
	public void createAnswer(final String qid, Answer a, User u) throws CustomException {
        try{
            Long qidl = Long.valueOf(qid);
			Map<String, Object> wherePredicatesMap = new HashMap<String, Object>();
			wherePredicatesMap.put("id",qidl);
			Optional<Question> wrappedQuestion = DBHelper.getEntityFromFields(wherePredicatesMap, Question.class, em);

			if (wrappedQuestion.isPresent()) {
                User user = em.merge(u);
                Question question = em.merge(wrappedQuestion.get());

                user.addAnswer(a);
                question.addAnswer(a);
                a.setQuestion(question);
                a.setAuthor(user);
                a.setCreated(new Date());

                em.persist(a);
                user.addPoints(Points.ANSWER_CREATED);
           } else {
                throw new CustomException("question not found");
            }
		}catch(NullPointerException ne){
            throw new CustomException("empty answer");
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong question ID");
		}
	}
	
	@Override
	public Answer getAnswer(final String aid) throws CustomException{
        try{
            Long aidl = Long.valueOf(aid);
            Answer answer=em.find(Answer.class,aidl);
            if(answer == null) throw new CustomException("empty answer");
            else return answer;
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong question ID");
		}
	}

	@Override
	public List<Answer> getMyAnswers(final User u) {
        Map<String, Object> wherePM = new HashMap<String, Object>();
        wherePM.put("author",u.getId());
        List<Answer> answers = DBHelper.getEntitiesFromFields(wherePM,Answer.class,em);
        return answers;
	}

	@Override
	public void validateAnswer(final String aid, User u) {
/*	public void validateAnswer(Answer a, User u) {
		User user = em.merge(u);
		Answer answer = em.merge(a);

		if (user.equals(answer.getAuthor())) {
			answer.validate();
            User answer_author = em.merge(answer.getAuthor());
            answer_author.addPoints(Points.ANSWER_VALIDATED);
		}
*/
	}

	@Override
	public void upvoteAnswer(final String aid, User u) {
/*	public void upvoteAnswer(Answer a, User u) {
		User user = em.merge(u);
		Answer answer = em.merge(a);

		user.addLikedAnswer(answer);
 		if (!user.equals(answer.getAuthor())){ //Do not add points to like own answers
           User answer_author = em.merge(answer.getAuthor());
            answer_author.addPoints(Points.ANSWER_LIKED);
        }
*/
	}

	@Override
	public void editAnswer(Answer oa, Answer ua, User u) {
		User user = em.merge(u);
		Answer answer = em.merge(oa);

		// TODO externalise check
		if (user.equals(answer.getAuthor())) {
			answer.setText(ua.getText());
		}
		em.merge(answer);
	}

	@Override
	public void deleteAnswer(final String aid,User user) throws CustomException {
        try{
            Long aidl = Long.valueOf(aid);
            Answer answer = em.find(Answer.class,aidl);
            if (user.equals(answer.getAuthor())) {
                // remove all upvotes
                for (User usr : answer.getUpvotes()) {
                    usr.removeLikedAnswer(answer);
                }
//            answer.removeAuthor();
//            answer.removeQuestion();
                em.flush();
                em.clear(); //need to clear and reload otherwise Set not equals=>no delete from em
                answer = em.find(Answer.class,aidl);
                User author = answer.getAuthor();
                em.remove(answer);
                author.addPoints(Points.ANSWER_REMOVED);
            } else {
                throw new CustomException("User is not the author. Unable to delete answer !");
            }
 		}catch(NullPointerException ne){
            throw new CustomException("empty answer");
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong question ID");
        }
	}
}
