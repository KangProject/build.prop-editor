package de.bwulfert.debug;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class d {


	private static String log_tag = "buildprop_log";
	
	public void setLog_tag(String log_tag) {
		d.log_tag = log_tag;
	}
	
	public String getLog_tag() {
		return log_tag;
	}
	
	public static void l(String msg){
		Log.i(log_tag, ""+msg);
	}
	
	public static void l(boolean b){
	Log.i(log_tag, ""+(b));
	}
	
	public static void l(int i){
	Log.i(log_tag, ""+(i));
	}

	public static void l(float f){
	Log.i(log_tag, ""+(f));
	}
	
	public static void t(Context c, String msg){
		Toast.makeText(c, ""+msg, Toast.LENGTH_SHORT).show();
	}
	
	public static void t(Context c, boolean msg){
		Toast.makeText(c, ""+msg, Toast.LENGTH_SHORT).show();
	}
}
