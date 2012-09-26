package de.bwulfert.logical;

import java.io.File;

public class Environment {

	private String[] common_dirs = new String[]{"/system/bin/" , "/system/xbin/"};
	
	public String getUtilPath(String utilname){
		
		String path = "";
		
		for(String current_path : common_dirs){
			File f = new File(current_path + utilname);
			if(f.isFile()){
				path = f.getAbsolutePath();
				break;
			}else {
				path = null;
			}
		}
		
		return path;
		
	}
	
}
