package de.bwulfert.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import android.util.Log;
import de.bwulfert.debug.d;
import de.bwulfert.logical.ShellCommunication;
import de.bwulfert.system.Constants;

public class BuildPropWriter {

	/**
	 * 
	 * @param hmap
	 *            - LinkedHasmap - Including comments as well as pairing
	 */
	public void writeMapAsFile(LinkedHashMap<String, String> hmap) {
		try {

			Collection<String> keys = hmap.keySet();
			Collection<String> values = hmap.values();
			Iterator<String> ki = keys.iterator(), vi = values.iterator();
			ShellCommunication shell = new ShellCommunication();

			// ------------------------------------------------------------------//

			// delete the old build.prop
//			os.writeBytes("/system/xbin/rm " + Constants.buildPropPath);
			
			shell.executeAndRetrieveAsBlock("/system/xbin/rm " + Constants.buildPropPath, true);
			
//			os.writeBytes("\n");
//			os.flush();

			// create a new file build.prop
//			os.writeBytes("/system/xbin/touch " + Constants.buildPropPath);
			shell.executeAndRetrieveAsBlock("/system/xbin/touch " + Constants.buildPropPath, true);
//			os.writeBytes("\n");
//			os.flush();

			// create the statement
			// e.g.: echo "ro.build.id=abcde" >> /system/build.prop
					
			String executedCommand = "";
			String statement = "";
			ArrayList<String> commands = new ArrayList<String>();
			
			
			while (ki.hasNext() && vi.hasNext()) {
				
				String vis = vi.next();
				String kis = ki.next();
				
		
//				os.writeBytes("echo \"");
//				os.flush();
				
				if (kis.startsWith(Constants.comment)) {
					if (!kis.equals("")) {
//						os.writeBytes(vis);
						statement = statement.concat(vis);
						
//						Log.i("log", vis);
					} else {
						// os.writeBytes(vis + "=");
//						os.writeBytes(vis + "=");
						statement = statement.concat(vis + "=");
					}
				} else {
					// os.writeBytes(kis + "=" + vis);
//					os.writeBytes(kis + "=" + vis);
					statement = statement.concat(kis + "=" + vis);
				}
				
//				os.writeBytes("\"");
				statement = statement.concat("\"");
				
//				os.flush();
								
				//pipe the whole command into the file at the path
//				os.writeBytes(" >> "+ Constants.buildPropPath);
//				statement = statement.concat(" >> "+ Constants.buildPropPath);

//				os.writeBytes(command);
				
//				os.writeBytes("\n");				
				
				// execute!
//				os.flush();
				
//				os.

				executedCommand = "echo -e \""+ statement + " >> "+ Constants.buildPropPath;
				
				
				commands.add(executedCommand);				
				
				statement = "";

			}
			
			shell.executeListAndRetrieveAsBlock(commands, true);
		
			
			
//			d.l("close");
//			os.close();

			
		} catch (Exception e) {
			Log.i("filemgr", "ERROR: " + e.getMessage());
		}
	}
}
