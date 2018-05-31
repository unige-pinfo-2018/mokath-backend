package ch.mokath.uniknowledgerestapi.dom;

import org.junit.Assert;
import org.junit.Test;

/**
* @author ornela
* @author zue
*/
public class PointsTest {

	@Test
	public void pointPointValueAndInverseTest() {
        Points pointQuestionCreated = Points.QUESTION_CREATED;
        Points pointQuestionRemoved = Points.QUESTION_REMOVED;
        Assert.assertFalse(pointQuestionCreated.getPointValue() == 0);
        Assert.assertTrue(pointQuestionCreated.getPointValue() == -pointQuestionRemoved.getPointValue());
    }

}
