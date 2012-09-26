package de.bwulfert.data;

import java.util.List;

public class EnrichedProperty {

	@Override
	public String toString() {
		return "EnrichedProperty [key=" + key + ", value=" + value
				+ ", description=" + description + ", keyboardLayout="
				+ keyboardLayout + ", values=" + values + ", defaultValue="
				+ defaultValue + "]";
	}

	private String key, value, description, keyboardLayout;
	private List<String> values;
	private String defaultValue;

	public EnrichedProperty(String key, String value, Descriptor descriptor) {
		this.key = key;
		this.value = value;
		this.description = descriptor.getDescription();
		this.keyboardLayout = descriptor.getKeyboardLayout();
		this.values = descriptor.getValues();
		this.defaultValue = descriptor.getDefaultValue();
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCurrentValue() {
		return value;
	}

	public void setCurrentValue(String value) {
		this.value = value;
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

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

}