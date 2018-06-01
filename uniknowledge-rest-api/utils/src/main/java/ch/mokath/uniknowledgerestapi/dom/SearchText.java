package ch.mokath.uniknowledgerestapi.dom;

import java.io.Serializable;

public class SearchText implements Serializable {
	
	private String text;
	
	public SearchText() {
		
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

}
