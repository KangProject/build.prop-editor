package de.bwulfert.logical;

import java.util.ArrayList;
import java.util.List;

import android.content.res.XmlResourceParser;
import de.bwulfert.data.BuildPropHandler;
import de.bwulfert.data.Descriptor;
import de.bwulfert.data.DescriptorHandler;
import de.bwulfert.data.EnrichedProperty;
import de.bwulfert.data.Property;

public class Controller {

	private BuildPropHandler buildprophandler;
	private DescriptorHandler descriptorhandler;
	private static Controller controller = null;

	private Controller() {
		buildprophandler = new BuildPropHandler();
		descriptorhandler = new DescriptorHandler();
	}

	public static Controller getInstance() {
		
//		if(controller == null){
//			controller = new Controller();
//		}
		
		return controller == null ? new Controller() : controller;
	}

	public List<Descriptor> getDescriptors(XmlResourceParser xmlrp) {
		return descriptorhandler.getDescriptors(xmlrp);
	}

	public List<Property> getProperties() {
		return buildprophandler.getProperties();
	}

	public List<Property> getEndrichedPropertieList(XmlResourceParser xmlrp) {

		ArrayList<Descriptor> descriptorList = descriptorhandler.getDescriptors(xmlrp);

		List<Property> propertyList = getProperties();

		for (Property p : propertyList) {
			for (Descriptor desc : descriptorList) {
				if (p.getKey().equals(desc.getKeyName())) {
					p.setDescribed(true);
					break;
				}
			}
		}

		return propertyList;
	}

	public EnrichedProperty getEnrichedProperty(String key,
			XmlResourceParser xmlrp) {

		ArrayList<Descriptor> descriptorList = descriptorhandler
				.getDescriptors(xmlrp);
		List<Property> propertyList = getProperties();

		Property prop = null;
		Descriptor desc = null;

		EnrichedProperty eproperty = null;

		for (Property p : propertyList) {
			if (p.getKey().startsWith(key)) {
				prop = p;
				break;
			}
		}

		for (Descriptor d : descriptorList) {
			if (d.getKeyName().equals(prop.getKey())) {
				desc = d;
				break;
			}
		}

		if (desc == null) {
			eproperty = new EnrichedProperty(prop.getKey(), prop.getValue(),
					Descriptor.unknown_descriptor);
		} else {
			eproperty = new EnrichedProperty(prop.getKey(), prop.getValue(),
					desc);
		}

		return eproperty;

	}

	public void modifyProperty(String propKey, String value) {
		buildprophandler.modifyProperty(propKey, value);
		buildprophandler.write();
	}

	public boolean isPropertyAllreadyIn(Property property) {
		return buildprophandler.isPropertyAllreadyIncluded(property);
	}

	public void addProperty(Property property) {
		buildprophandler.addProperty(property);
		buildprophandler.write();
	}

	public void deleteProperty(String key){
		buildprophandler.deleteProperty(key);
		buildprophandler.write();	
	}
}
