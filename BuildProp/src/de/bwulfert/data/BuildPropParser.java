package de.bwulfert.data;


import java.io.File;
import java.util.LinkedHashMap;
import java.util.Scanner;

import android.util.Log;
import de.bwulfert.system.Constants;

public class BuildPropParser {	
	
	/**
	 * Reads the build.prop file and creates a key/value lhm out of it.
	 * Including pairs and comments
	 * @return LinkedHashMap - bare, naked, line for line build.prop
	 */
	public LinkedHashMap<String, String> parse() {

		String worker = "";
		File buildprop = new File("/system/build.prop");

		LinkedHashMap<String, String> buildpropfile = new LinkedHashMap<String, String>();

		int counter = 0;

		try {

			Scanner s = new Scanner(buildprop);

			while (s.hasNext()) {

				worker = s.nextLine();

				if (worker.startsWith("#") || worker.length() == 0) {
					buildpropfile.put(Constants.comment + counter, worker);
					counter++;
				} else {
					buildpropfile.put( worker.substring(0, worker.indexOf("=")), worker.substring(worker.indexOf("=") + 1, worker.length()));
				}

			}

			s.close();

		} catch (Exception e) {
			Log.i("buildpropeditor", "info: " + e.getMessage());
		}
		return buildpropfile;
	}

}
