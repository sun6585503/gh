package com.sunkun.gh.element;

import org.jsoup.nodes.Document;

/**
 * @author Administrator
 *
 */
public class PageElement {

	private Document doc;
	
	private String referer;

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}
	
	
}
