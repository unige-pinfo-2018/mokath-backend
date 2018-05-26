package ch.mokath.uniknowledgerestapi.dom;

/**
 * @author zue
 */

public enum Points {

	QUESTION_CREATED(0.2),
	QUESTION_REMOVED(-QUESTION_CREATED.value),
	QUESTION_LIKED(0.1),
	QUESTION_FOLLOWED(0.1),

	ANSWER_CREATED(0.3),
	ANSWER_REMOVED(-ANSWER_CREATED.value),
	ANSWER_LIKED(0.1),
	ANSWER_VALIDATED(0.2);

	private double value;

	Points(double value) {
		this.value = value;
	}

	public double getPointValue() {
		return this.value;
	}
}
