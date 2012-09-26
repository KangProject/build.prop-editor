package de.bwulfert.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Descriptor implements Serializable {

	private static final long serialVersionUID = 1L;

	private String keyName;

	private String description;
	
	private String keyboardLayout;
	
	private ArrayList<String> values;
	
	private String defaultValue;
	
	public static Descriptor unknown_descriptor = new Descriptor("unknown", "No proper description found. If you've got any information about the current key, contact me." ,"unknown", new ArrayList<String>(0), "unknown");
	
	public Descriptor(String keyName, String description,
			String keyboardLayout, ArrayList<String> values, String defaultValue) {
		super();
		this.keyName = keyName;
		this.description = description;
		this.keyboardLayout = keyboardLayout;
		this.values = values;
		this.defaultValue = defaultValue;
	}

	public Descriptor(){}
	
	public static final String KeyName = "KeyName";
	public static final String Description = "Description";
	public static final String KeyboardLayout = "KeyboardLayout";
	public static final String DefaultValue = "DefaultValue";
	public static final String Values = "Values";

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeyboardLayout() {
		return keyboardLayout;
	}

	public void setKeyboardLayout(String keyboardLayout) {
		this.keyboardLayout = keyboardLayout;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String toString() {
		return "PropertyDescription [keyName=" + keyName + ", description="
				+ description + ", keyboardLayout=" + keyboardLayout
				+ ", values=" + values + "]";
	}
	
	
}
