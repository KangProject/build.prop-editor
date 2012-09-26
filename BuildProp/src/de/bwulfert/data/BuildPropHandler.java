package de.bwulfert.data;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import de.bwulfert.system.Constants;

public class BuildPropHandler {


	private BuildPropWriter writer; 
	private LinkedHashMap<String, String> currentPropMap = null;
	
	public BuildPropHandler() {
		writer = new BuildPropWriter();
		currentPropMap = new BuildPropParser().parse();
	}
	
	public void deleteProperty(String key){
		currentPropMap.remove(key);
	}
		
	public void modifyProperty(String propKey, String value){
		currentPropMap.put(propKey, value);
	}
	
	public void write(){
		writer.writeMapAsFile(currentPropMap);
	}
	
	public List<Property> getProperties(){
		
		ArrayList<Property> propertyList = new ArrayList<Property>();
		
		for (String key : currentPropMap.keySet()) {
			if (!key.startsWith("#") && !key.contains(Constants.comment)) {
				propertyList.add(new Property(key, currentPropMap.get(key)));
			}
		}
		
		return (List<Property>)propertyList;
	}
	
	public boolean isPropertyAllreadyIncluded(Property property){
		return currentPropMap != null && currentPropMap.containsKey(property.getKey());
	}
	
	public void addProperty(Property property){
		currentPropMap.put(property.getKey(), property.getValue());
	}
	
}
