package de.bwulfert.logical;

import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import android.util.Log;
import de.bwulfert.debug.d;

public class ShellCommunication {

	/**
	 * ShellCommunication
	 * 
	 * This component checks the availability of the su binary.
	 * 
	 * @author Benjamin Wulfert
	 * @version 1.0
	 * 
	 */

	/**
	 * Compares the current userID if it equals to the rootID (=0)
	 * 
	 * @return true - su available | false - su not available
	 * */

	private static ShellCommunication instance = null;
	private static Environment env = null;

	private static String su_path;
	private static String busybox_path;

	public static ShellCommunication getInstance() {
		if (instance == null) {
			instance = new ShellCommunication();
			env = new Environment();
			su_path = env.getUtilPath("su");
			busybox_path = env.getUtilPath("busybox");
		}
		return instance;
	}

	public boolean rootPermissionsAvailable() {

		boolean rootPermissions = false;

		if (su_path != null) {
			if (executeAndRetrieveAsBlock(busybox_path + " id", true).contains(
					"uid=0(root) gid=0(root)")) {
				rootPermissions = true;
			}
			// TODO
			else if (executeAndRetrieveAsBlock("which su", true).startsWith(
					"/system")) {
				rootPermissions = true;
			}
		}

		return rootPermissions;
	}

	/**
	 * Ablauf: Ausführung einer nativen Android App inkl. root rechten.
	 * 
	 * 1.) su binary muss vorhanden sein 2.) su-rechte müssen vom user
	 * stattgegeben werden 3.) ausführbares command muss gegeben sein.
	 * 
	 * incode
	 * 
	 * 1.) führe su aus um root rechte zu erlangen 2.) erstelle outputstream um
	 * mit terminal zu kommunizieren 3.) write(command) 'n flush it out 4.)
	 * 
	 * @return
	 */

	public void execute(String command) {
		try {

			Process p = Runtime.getRuntime().exec(su_path);

			DataOutputStream os = new DataOutputStream(p.getOutputStream());

			os.flush();

			os.writeBytes(command + "\n");
			os.flush();

			os.close();

		} catch (Exception e) {
			Log.i("root", e.getMessage());
		}
	}

	public String executeAndRetrieveAsBlock(String command, boolean asRoot) {
		return Arrays.toString(executeAndRetrieve(command, asRoot).toArray());
	}

	public ArrayList<String> executeAndRetrieve(String command, boolean asRoot) {

		ArrayList<String> resultSet = null;

		try {

			Process p = Runtime.getRuntime().exec(
					asRoot ? su_path : busybox_path + " sh");

			DataOutputStream os = new DataOutputStream(p.getOutputStream());
			InputStreamThread stdin = new InputStreamThread(p.getInputStream(),
					"stdin");
			InputStreamThread stderr = new InputStreamThread(
					p.getErrorStream(), "sterr");

			stdin.start();
			stderr.start();

			// perform the command as root
			os.writeBytes(command + "\n");
			os.flush();

			os.writeBytes("exit\n");
			os.flush();

			stdin.join();
			stderr.join();

			p.waitFor();

			stdin.interrupt();
			stderr.interrupt();

			if (stderr.getResultSet().size() == 0) {
				resultSet = stdin.getResultSet();
			} else {
				resultSet = stderr.getResultSet();
			}

		} catch (Exception e) {
			Log.i("log", "Exception happend: ", e);
		}

		return resultSet;

	}

	public ArrayList<String> executeAndRetrieveList(ArrayList<String> commands,
			boolean asRoot) {

		ArrayList<String> resultSet = null;

		try {

			Process p = Runtime.getRuntime().exec(
					asRoot ? su_path : busybox_path + " sh");

			DataOutputStream os = new DataOutputStream(p.getOutputStream());
			InputStreamThread stdin = new InputStreamThread(p.getInputStream(),
					"stdin");
			InputStreamThread stderr = new InputStreamThread(
					p.getErrorStream(), "sterr");

			stdin.start();
			stderr.start();

			for (String command : commands) {
				os.writeBytes(command + "\n");
				os.flush();
			}

			os.writeBytes("exit\n");
			os.flush();

			stdin.join();
			stderr.join();

			p.waitFor();

			stdin.interrupt();
			stderr.interrupt();

			if (stderr.getResultSet().size() == 0) {
				resultSet = stdin.getResultSet();
			} else {
				resultSet = stderr.getResultSet();
			}

		} catch (Exception e) {
			Log.i("log", "Exception happend: ", e);
		}

		return resultSet;

	}

	public String executeListAndRetrieveAsBlock(ArrayList<String> commands,
			boolean asRoot) {
		return Arrays.toString(executeAndRetrieveList(commands, asRoot)
				.toArray());
	}

	public boolean isSystemWriteable() {
		boolean writeable = false;

		if (executeAndRetrieveAsBlock(
				busybox_path + " mount | " + busybox_path + " grep system",
				false).contains("rw,relatime")) {
			writeable = true;
		}

		return writeable;
	}

	public void initialBackupAsRoot(String appDataPath) {
		execute(busybox_path + " cp /system/build.prop " + appDataPath
				+ "build.prop");
	}

	/**
	 * 
	 * @param dataPath
	 * @return
	 */

	public void restoreVanilla(String dataPath) {

		// TODO 
		// Check for build.prop backup availability
		d.l("/system/build.prop exists -> " + new File("/system/build.prop").isFile());

		executeAndRetrieveAsBlock(busybox_path + " rm /system/build.prop", true);

		// restore from vanilla prop
		// > REQUIREMENT = build.prop has to be saved!
		executeAndRetrieveAsBlock(busybox_path + " cp " + dataPath
				+ "/build.prop" + " " + "/system/build.prop", true);
	}

	public void reboot() {
		d.l(executeAndRetrieveAsBlock(busybox_path + " reboot", true));
	}

	public void mountSystemAsReadWrite() {
		execute(busybox_path
				+ " mount -o remount,rw -t yaffs2 /dev/block/mtdblock3 /system");
	}

	public void mountSystemAsReadOnly() {
		execute(busybox_path
				+ " mount -o ro,remount -t yaffs2 /dev/block/mtdblock3 /system");
	}

}
