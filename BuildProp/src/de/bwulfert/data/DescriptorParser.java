package de.bwulfert.data;

import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.content.res.XmlResourceParser;
import android.util.Log;

public class DescriptorParser {

	private ArrayList<Descriptor> descriptorList = new ArrayList<Descriptor>();

	private Descriptor descriptor;

	private final String ELEM_PROPERTY_DESCRIPTION = "PropertyDescription";
	private final String ELEM_KEYNAME = "KeyName";
	private final String ELEM_DESCRIPTION = "Description";
	private final String ELEM_KEYBOARDLAYOUT = "KeyboardLayout";
	private final String ELEM_VALUES = "Values";
	private final String ELEM_VALUES_ENTRY_VALUE = "Value";
	private final String ELEM_VALUES_ENTRY_DEFAULT_VALUE = "DEFAULT";

	String currentTag = "";
	String currentText = "";


	public ArrayList<Descriptor> parse(XmlResourceParser xmlrp) {
	
		try {

			XmlPullParser xpp = xmlrp;

			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {

				if (eventType == XmlPullParser.START_TAG) {

					currentTag = xpp.getName();

					if (currentTag.equals(ELEM_PROPERTY_DESCRIPTION)) {
						descriptor = new Descriptor();
					} else if (currentTag.equals(ELEM_VALUES)) {
						descriptor.setValues(new ArrayList<String>());
					}

				} else if (eventType == XmlPullParser.END_TAG) {

					if (currentTag.equals(ELEM_KEYNAME)) {
						descriptorList.add(descriptor);
					}

				} else if (eventType == XmlPullParser.TEXT) {

					currentText = xpp.getText();

					if (currentTag.equals(ELEM_KEYNAME)) {
						descriptor.setKeyName(currentText);
					} else if (currentTag.equals(ELEM_DESCRIPTION)) {
						descriptor.setDescription(currentText);
					} else if (currentTag.equals(ELEM_KEYBOARDLAYOUT)) {
						descriptor.setKeyboardLayout(currentText);
					} else if (currentTag.equals(ELEM_VALUES_ENTRY_VALUE) && descriptor.getValues() != null) {
						descriptor.getValues().add(currentText);
					} else if (currentTag.equals(ELEM_VALUES_ENTRY_DEFAULT_VALUE)) {
						descriptor.setDefaultValue(currentText);
					}
				}

				eventType = xpp.next();
			}

		} catch (Throwable t) {
			Log.e("propbuild", "ERROR: ", t);
		}

		for (Descriptor d : descriptorList) {
			Log.i("propbuild", d.toString());
		}

		return descriptorList;

	}

}
