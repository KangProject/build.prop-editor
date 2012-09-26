package de.bwulfert.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import de.bwulfert.buildpropedit.R;
import de.bwulfert.debug.d;
import de.bwulfert.logical.ShellCommunication;
import de.bwulfert.system.Constants;

public class DialogHandler implements OnClickListener,
		android.view.View.OnClickListener {

	private int last_called_dialog_id = -1;
	private Activity activity = null;

	public DialogHandler(Activity activity) {
		this.activity = activity;
	}

	public void setLast_called_dialog_id(int last_called_dialog_id) {
		this.last_called_dialog_id = last_called_dialog_id;
	}

	public Dialog getFirstLaunchDialog(int id) {
		Dialog firstLaunch = new Dialog(activity);
		firstLaunch.setCancelable(false);
		firstLaunch.requestWindowFeature(Window.FEATURE_NO_TITLE);
		firstLaunch.setContentView(R.layout.firstlaunch);
		firstLaunch.setTitle(R.string.first_launch_welcome_title);
		Button positive = ((Button) firstLaunch
				.findViewById(R.id.firstlaunch_getprompted));
		positive.setOnClickListener(this);
		setLast_called_dialog_id(id);
		return firstLaunch;
	}

	public Dialog getAuthorDialog(int id) {
		Dialog about_author = new Dialog(activity);
		about_author.requestWindowFeature(Window.FEATURE_NO_TITLE);
		about_author.setContentView(R.layout.about_author);
		about_author.setTitle(R.string.app_name);
		setLast_called_dialog_id(id);
		ImageView on = (ImageView) about_author.findViewById(R.id.imageView1);
		ImageView twitter = (ImageView) about_author
				.findViewById(R.id.image_market);
		ImageView paypal = (ImageView) about_author.findViewById(R.id.paypal);
		ImageView xda = (ImageView) about_author.findViewById(R.id.xda);
		xda.setOnClickListener(this);
		twitter.setOnClickListener(this);
		on.setOnClickListener(this);
		paypal.setOnClickListener(this);

		return about_author;
	}

	public AlertDialog getRemountDialog(int id) {
		AlertDialog.Builder b = new AlertDialog.Builder(activity);
		b.setTitle(R.string.remount_title);
		b.setMessage(R.string.remount_message);
		b.setPositiveButton(R.string.remount_button_remount, this);
		b.setNegativeButton(R.string.button_cancel, this);
		CheckBox checkbox = new CheckBox(activity);
		checkbox.setText(R.string.remount_chkbox);
		checkbox.setChecked(false);
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					SharedPreferences settings = activity.getSharedPreferences(
							Constants.prefFile, 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean("show_remount_dialog", false);
					editor.commit();
				}

			}
		});

		b.setView(checkbox);
		setLast_called_dialog_id(id);
		return b.create();
	}

	public AlertDialog getRestoreBackupDialog(int id) {
		AlertDialog.Builder b = new AlertDialog.Builder(activity);
		b.setTitle(R.string.restore_title);
		b.setMessage(R.string.restore_rusure_question);
		b.setPositiveButton(R.string.revert_backup, this);
		b.setNegativeButton(R.string.button_cancel, this);
		setLast_called_dialog_id(id);
		return b.create();
	}

	public AlertDialog getRebootDialog(int id) {
		AlertDialog.Builder b = new AlertDialog.Builder(activity);
		b.setTitle(R.string.reboot_title);
		b.setMessage(R.string.reboot_message);
		b.setPositiveButton(R.string.reboot_button_remount, this);
		b.setNegativeButton(R.string.button_cancel, this);
		setLast_called_dialog_id(id);
		return b.create();
	}

	public void onClick(DialogInterface dialog, int which) {

		if (last_called_dialog_id == R.id.menu_restore) {
			if (which == DialogInterface.BUTTON1) {
				ShellCommunication.getInstance().restoreVanilla(
						Constants.backup_path);
				((PropertyListing) activity)
						.loadCurrentPropertiesAndSetAdapter();
			} else if (which == DialogInterface.BUTTON2) {
				// restore aborted
			}
		} else if (last_called_dialog_id == R.integer.firstlaunch) {

		} else if (last_called_dialog_id == R.integer.remount) {
			if (which == DialogInterface.BUTTON1) {
				ShellCommunication.getInstance().mountSystemAsReadWrite();
				createBackupLikeABoss();
			} else {
				activity.finish();
			}
		} else if (last_called_dialog_id == R.id.menu_reboot) {
			if (which == DialogInterface.BUTTON1) {
				ShellCommunication.getInstance().reboot();
			} else {
				dialog.dismiss();
			}
		} else if (last_called_dialog_id == R.integer.su_need) {
			if (which == DialogInterface.BUTTON_POSITIVE) {
				dialog.dismiss();
			} else if (which == DialogInterface.BUTTON_NEGATIVE) {
				activity.finish();
			}
		}
	}

	private void createBackupLikeABoss() {
		ShellCommunication.getInstance().initialBackupAsRoot(
				Constants.backup_path);
		SharedPreferences settings = activity.getSharedPreferences(
				Constants.prefFile, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("firstlaunch", false);
		editor.commit();
		d.t(activity, "Backup created!");
	}

	public void onClick(View v) {
		if (v.getId() == R.id.imageView1) {
			Intent viewIntent = new Intent("android.intent.action.VIEW",
					Uri.parse("https://twitter.com/#!/android_owl"));
			activity.startActivity(viewIntent);
			activity.dismissDialog(last_called_dialog_id);
		}

		if (v.getId() == R.id.image_market) {
			Intent viewIntent = new Intent(
					"android.intent.action.VIEW",
					Uri.parse("https://play.google.com/store/apps/developer?id=Benjamin+Wulfert"));
			activity.startActivity(viewIntent);
			activity.dismissDialog(last_called_dialog_id);
		}

		if (v.getId() == R.id.paypal) {
			Intent viewIntent = new Intent(
					"android.intent.action.VIEW",
					Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=SPG24VSRXZD7W"));
			activity.startActivity(viewIntent);
			activity.dismissDialog(last_called_dialog_id);
		}

		if (v.getId() == R.id.xda) {
			Intent viewIntent = new Intent(
					"android.intent.action.VIEW",
					Uri.parse("http://forum.xda-developers.com/showthread.php?t=1542949"));
			activity.startActivity(viewIntent);
			activity.dismissDialog(last_called_dialog_id);
		}
		
		if(v.getId() == R.id.firstlaunch_getprompted){
		
			activity.dismissDialog(last_called_dialog_id);
				
			Constants.has_root = ShellCommunication.getInstance().rootPermissionsAvailable();

			//bad
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
				if (Constants.has_root) {
					if (ShellCommunication.getInstance().isSystemWriteable()) {
						createBackupLikeABoss();
					} else {
						activity.showDialog(R.integer.remount);
					}
				} else {
					activity.showDialog(R.integer.su_need);
				}
		}
	}

	// MAKEITPRETTY
	public AlertDialog getRootIsNeededDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("No root access is given! This Tool makes heavy use of it. You can't make changes, add propertys nor restore your build.prop file.");
		builder.setPositiveButton("Continue", this);
		builder.setNegativeButton("finish", this);
		setLast_called_dialog_id(id);
		return builder.create();
	}

}
