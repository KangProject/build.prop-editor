package de.bwulfert.data;

public class Property {

	private String key, value;
	private boolean described;

	public boolean isDescribed() {
		return described;
	}

	public void setDescribed(boolean described) {
		this.described = described;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Property(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Property [key=" + key + ", value=" + value + ", described="
				+ described + "]";
	}

}
