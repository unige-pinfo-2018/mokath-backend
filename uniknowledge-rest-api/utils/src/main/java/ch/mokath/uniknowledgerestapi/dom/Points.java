package ch.mokath.uniknowledgerestapi.dom;

/**
 * @author zue
 */

public enum Points {

	QUESTION_CREATED(2),
	QUESTION_REMOVED(-QUESTION_CREATED.value),
	QUESTION_LIKED(1),
	QUESTION_UNLIKED(-QUESTION_LIKED.value),
	QUESTION_FOLLOWED(1),

	ANSWER_CREATED(3),
	ANSWER_REMOVED(-ANSWER_CREATED.value),
	ANSWER_LIKED(1),
	ANSWER_VALIDATED(2),
	ANSWER_UNVALIDATED(-ANSWER_VALIDATED.value);

	private long value;

	Points(long value) {
		this.value = value;
	}

	public long getPointValue() {
		return this.value;
	}
}
