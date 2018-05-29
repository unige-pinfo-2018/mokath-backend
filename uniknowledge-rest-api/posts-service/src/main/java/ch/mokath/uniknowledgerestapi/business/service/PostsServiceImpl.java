/**
 *
 */
package ch.mokath.uniknowledgerestapi.business.service;

/**z*/
import ch.mokath.uniknowledgerestapi.utils.CustomException;
/*z*/

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.core.Response;

import ch.mokath.uniknowledgerestapi.dom.Answer;
import ch.mokath.uniknowledgerestapi.dom.Points;
import ch.mokath.uniknowledgerestapi.dom.Question;
import ch.mokath.uniknowledgerestapi.dom.User;
import ch.mokath.uniknowledgerestapi.utils.DBHelper;
import ch.mokath.uniknowledgerestapi.utils.CustomException;

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
	public Question getQuestion(final String qid) throws CustomException{
        try{
            Long qidl = Long.valueOf(qid);
            Question question=em.find(Question.class,qidl);
            if(question == null) throw new CustomException("question not found");
            else return question;
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong question ID");
		}
	}

	@Override
	public List<Question> getQuestions(){
		List<Question> questions = DBHelper.getAllEntitiesDescOrder(Question.class, em, "popularity");
        return questions;
	}

	@Override
	public Set<User> getQuestionFollowers(final String qid) throws CustomException{
        try{
            Long qidl = Long.valueOf(qid);
            Question question=em.find(Question.class,qidl);
            if(question == null) throw new CustomException("question not found");
            else{
                Set<User> followers = question.getFollowers();
                followers.toString(); //This is needed otherwise org.hibernate.LazyInitializationException
                return followers;
            }
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong question ID");
		}
	}

	@Override
	public Set<User> getQuestionUpvoters(final String qid) throws CustomException{
        try{
            Long qidl = Long.valueOf(qid);
            Question question=em.find(Question.class,qidl);
            if(question == null) throw new CustomException("question not found");
            else{
                Set<User> upvoters = question.getUpvoters();
                upvoters.toString(); //This is needed otherwise org.hibernate.LazyInitializationException
                return upvoters;
            }
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong question ID");
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
            throw new CustomException("question not found");
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong question ID");
		}
	}

	@Override
	public void upvoteQuestion(final String qid, User u) throws CustomException {
        try{
            Long qidl = Long.valueOf(qid);
            Question question=em.find(Question.class,qidl);
            if(question == null) throw new CustomException("question not found");
            else{
                User user = em.merge(u);
                if(user.getLikedQuestions().contains(question))
                    throw new CustomException("already upvoted question",Response.Status.UNAUTHORIZED);
                else if (user.equals(question.getAuthor())) //Do not upvote or add points to own questions
                    throw new CustomException("author can't upvote his own question",Response.Status.UNAUTHORIZED);
                else{
                    user.addLikedQuestion(question);
                    User question_author = em.merge(question.getAuthor());
                    question_author.addPoints(Points.QUESTION_LIKED);
                    question.addPopularity();
                }
            }
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong question ID");
		}
	}

	@Override
	public void followQuestion(final String qid, User u) throws CustomException {
        try{
            Long qidl = Long.valueOf(qid);
            Question question=em.find(Question.class,qidl);
            if(question == null) throw new CustomException("question not found");
            else{
                User user = em.merge(u);
                if(user.getFollowedQuestions().contains(question))
                    throw new CustomException("already following question",Response.Status.UNAUTHORIZED);
                else if (user.equals(question.getAuthor())) //Do not upvote or add points to own questions
                    throw new CustomException("author can't upvote his own question",Response.Status.UNAUTHORIZED);
                else{
                    user.addFollowedQuestion(question);
                    if (!user.equals(question.getAuthor())){ //Do not add points to follow own questions
                        User question_author = em.merge(question.getAuthor());
                        question_author.addPoints(Points.QUESTION_FOLLOWED);
                    }
                }
            }
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong question ID");
		}
	}

	@Override
	public void deleteQuestion(final String qid, User u) throws CustomException {
        try{
            Long qidl = Long.valueOf(qid);
            Question question=em.find(Question.class,qidl);
            if (u.equals(question.getAuthor())) {
                User author = question.getAuthor();
                // remove all followers/upvoters
                for (User usrf : question.getFollowers()) {
                    usrf.removeFollowedQuestion(question);
                }
                for (User usrl : question.getUpvoters()) {
                    usrl.removeLikedQuestion(question);
                    author.addPoints(Points.QUESTION_UNLIKED);
                }
                em.remove(question);
                author.addPoints(Points.QUESTION_REMOVED);
            }else
                throw new CustomException("unable to delete the question with ID = "+ qid +" (not the author)",Response.Status.UNAUTHORIZED);
 		}catch(NullPointerException ne){
            throw new CustomException("question not found");
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong question ID");
		}
	}

	@Override
	public Question editQuestion(final String qid, Question uq, User u) throws CustomException {
        try{
            Long qidl = Long.valueOf(qid);
            Question question=em.find(Question.class,qidl);
            if(question == null) throw new CustomException("question not found");
            else{
                User user = em.merge(u);
                if (user.equals(question.getAuthor())) {
                    question.setText(uq.getText());
                    question.setTitle(uq.getTitle());
                    question.setDomains(uq.getDomains());
                    em.merge(question);
                    return question;
                }else throw new CustomException("not the author of the question",Response.Status.UNAUTHORIZED);
            }
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong question ID");
		}
	}

	@Override
	public List<Question> getMyQuestions(final User u) {
        Map<String, Object> wherePM = new HashMap<String, Object>();
        wherePM.put("author",u.getId());
        List<Question> questions = DBHelper.getEntitiesFromFields(wherePM,Question.class,em);
        return questions;
	}

	@Override
	public Set<Question> getMyFollowedQuestions(final User u) {
        User user = em.find(User.class,u.getId());
        Set<Question> questions = user.getFollowedQuestions();
        questions.toString(); //This is needed otherwise org.hibernate.LazyInitializationException
        return questions;
	}

	@Override
	public Set<Question> getMyUpvotedQuestions(final User u) {
        User user = em.find(User.class,u.getId());
        Set<Question> questions = user.getLikedQuestions();
        questions.toString(); //This is needed otherwise org.hibernate.LazyInitializationException
        return questions;
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
                question.addNbAnswers();
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
	public Answer getAnswer(final String aid) throws CustomException {
        try{
            Long aidl = Long.valueOf(aid);
            Answer answer=em.find(Answer.class,aidl);
            if(answer == null) throw new CustomException("answer not found");
            else return answer;
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong answer ID");
		}
	}

	@Override
	public Set<User> getAnswerUpvoters(final String aid) throws CustomException {
        try{
            Long aidl = Long.valueOf(aid);
            Answer answer=em.find(Answer.class,aidl);
            if(answer == null) throw new CustomException("answer not found");
            else{
                Set<User> upvoters = answer.getUpvoters();
                upvoters.toString(); //This is needed otherwise org.hibernate.LazyInitializationException
                return upvoters;
            }
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong answer ID");
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
	public Set<Answer> getMyUpvotedAnswers(final User u) {
        User user = em.find(User.class,u.getId());
        Set<Answer> answers = user.getLikedAnswers();
        answers.toString(); //This is needed otherwise org.hibernate.LazyInitializationException
        return answers;
	}

	@Override
	public void validateAnswer(final String aid, User u) throws CustomException {
        try{
            Long aidl = Long.valueOf(aid);
            Answer answer=em.find(Answer.class,aidl);
            if(answer == null) throw new CustomException("answer not found");
            else{
                if(answer.isValidated()) throw new CustomException("answer is already validated",Response.Status.OK);
                else {
                    User user = em.merge(u);
                    if (user.equals(answer.getQuestion().getAuthor())) {
                        answer.validate();
                        User answer_author = em.merge(answer.getAuthor());
                        answer_author.addPoints(Points.ANSWER_VALIDATED);
                    } else throw new CustomException("not the author of the question",Response.Status.UNAUTHORIZED);
                }
            }
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong question ID");
		}
	}

	@Override
	public void upvoteAnswer(final String aid, User u) throws CustomException {
        try{
            Long aidl = Long.valueOf(aid);
            Answer answer=em.find(Answer.class,aidl);
            if(answer == null) throw new CustomException("answer not found");
            else{
                User user = em.merge(u);
                if(user.getLikedAnswers().contains(answer))
                    throw new CustomException("already upvoted answer",Response.Status.UNAUTHORIZED);
                else if (user.equals(answer.getAuthor())) //Do not upvote or add points to own answers
                     throw new CustomException("author can't upvote his own answer",Response.Status.UNAUTHORIZED);
                else {
                    user.addLikedAnswer(answer);
                    User answer_author = em.merge(answer.getAuthor());
                    answer_author.addPoints(Points.ANSWER_LIKED);
                }
            }
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong question ID");
		}
	}

	@Override
	public Answer editAnswer(final String aid, Answer ua, User u) throws CustomException {
        try{
            Long aidl = Long.valueOf(aid);
            Answer answer=em.find(Answer.class,aidl);
            if(answer == null) throw new CustomException("answer not found");

            User user = em.merge(u);
            if(user.equals(answer.getAuthor())) {
                answer.setText(ua.getText());
                if(answer.isValidated()){
                    answer.unvalidate();
                    user.addPoints(Points.ANSWER_UNVALIDATED);
                }
                em.merge(answer);
                return answer;
            }else throw new CustomException("bad answer (not the author)",Response.Status.UNAUTHORIZED);
            
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong answer ID");
        }
	}

	@Override
	public void deleteAnswer(final String aid,User user) throws CustomException {
        try{
            Long aidl = Long.valueOf(aid);
            Answer answer = em.find(Answer.class,aidl);
            if (user.equals(answer.getAuthor())) {
                // remove all upvoters
                for (User usr : answer.getUpvoters()) {
                    usr.removeLikedAnswer(answer);
                }
                em.flush();
                em.clear(); //need to clear and reload otherwise Set not equals=>no delete from em
                answer = em.find(Answer.class,aidl);
                Question question = em.find(Question.class, answer.getId());
                User author = answer.getAuthor();
                em.remove(answer);
                author.addPoints(Points.ANSWER_REMOVED);
                question.removeNbAnswers();
            } else {
                throw new CustomException("unable to delete answer with ID = "+ aid +" (not the author)",Response.Status.UNAUTHORIZED);
            }
 		}catch(NullPointerException ne){
            throw new CustomException("answer not found");
        }catch(NumberFormatException nfe){
            throw new CustomException("wrong question ID");
        }
	}
}
