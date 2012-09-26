package de.bwulfert.data;

import java.util.ArrayList;

import android.content.res.XmlResourceParser;

public class DescriptorHandler {

	private DescriptorParser parser;

	public DescriptorHandler() {
		parser = new DescriptorParser();
	}
		
	public ArrayList<Descriptor> getDescriptors(XmlResourceParser xmlrp){
		return parser.parse(xmlrp);
	}
	
}
