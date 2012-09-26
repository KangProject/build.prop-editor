package de.bwulfert.view;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import de.bwulfert.buildpropedit.R;
import de.bwulfert.data.Property;
import de.bwulfert.listAdapter.CustomKeyValueAdapter;
import de.bwulfert.logical.Controller;
import de.bwulfert.logical.ShellCommunication;
import de.bwulfert.system.Constants;

public class PropertyListing extends ListActivity implements
		OnItemClickListener {

	private String selected_item = "";
	private int selected_item_id = 0;
	private ListView listview;
	private CustomKeyValueAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (isFirstLaunch()) {
			showDialog(R.integer.firstlaunch);
		}else {
			Constants.has_root = ShellCommunication.getInstance().rootPermissionsAvailable();
		}

		listview = getListView();
		listview.setOnItemClickListener(this);

		listview.setDivider(getResources().getDrawable(R.drawable.divider));

		adapter = new CustomKeyValueAdapter(this, Controller.getInstance()
				.getEndrichedPropertieList(
						getResources().getXml(R.xml.description_bundle)));

		registerForContextMenu(getListView());

		setListAdapter(adapter);

		// setContentView(R.layout.property_listing);

		super.onCreate(savedInstanceState);
	}

	public void askForWriteableSystem() {
		if (Constants.has_root && !ShellCommunication.getInstance().isSystemWriteable() && !isFirstLaunch()) {
			showDialog(R.integer.remount);
		}
	}

	@Override
	protected void onResume() {
		askForWriteableSystem();
		loadCurrentPropertiesAndSetAdapter();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		if (Constants.has_root) {
			inflater.inflate(R.menu.main_menu, menu);
		} else {
			inflater.inflate(R.menu.main_menu_nonroot, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		if (item.getTitle().toString().equals(getString(R.string.Create))) {
			Intent ii = new Intent(this, AddProperty.class);
			startActivity(ii);
		} else if (item.getTitle().toString()
				.equals(getString(R.string.Delete))) {
			Controller.getInstance().deleteProperty(selected_item);
			loadCurrentPropertiesAndSetAdapter();
		} else if (item.getTitle().toString().equals(getString(R.string.Update)) || item.getTitle().toString().equals(getString(R.string.View))) {
			Intent i = new Intent(PropertyListing.this, PairEditing.class);
			i.putExtra("key",Controller.getInstance().getProperties().get(selected_item_id).getKey());
			startActivity(i);
		}

		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		AdapterView.AdapterContextMenuInfo info;
		menu.setHeaderTitle(R.string.Update);

		try {

			info = (AdapterView.AdapterContextMenuInfo) menuInfo;

			if (Constants.has_root) {
				menu.add(R.string.Create);
				menu.add(R.string.Delete);
				menu.add(R.string.Update);
			} else {
				menu.add(R.string.View);
			}
		} catch (ClassCastException e) {
			return;
		}

		selected_item_id = (int) getListAdapter().getItemId(info.position);

		selected_item = Controller.getInstance().getProperties()
				.get(selected_item_id).getKey();

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	public void loadCurrentPropertiesAndSetAdapter() {

		adapter.clear();

		for (Property p : Controller.getInstance().getEndrichedPropertieList(
				getResources().getXml(R.xml.description_bundle))) {
			adapter.add(p);
		}

	}

	public void onItemClick(AdapterView<?> adapterview, View view, int pos,
			long arg3) {

		Intent i = new Intent(PropertyListing.this, PairEditing.class);
		i.putExtra("key", Controller.getInstance().getProperties().get(pos)
				.getKey());
		startActivity(i);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		showDialog(item.getItemId());

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case R.id.menu_about:
			dialog = new DialogHandler(this).getAuthorDialog(id);
			break;
		case R.id.menu_restore:
			dialog = new DialogHandler(this).getRestoreBackupDialog(id);
			break;
		case R.integer.firstlaunch:
			dialog = new DialogHandler(this).getFirstLaunchDialog(id);
			break;
		case R.integer.remount:

			if (showRemountDialog()) {
				dialog = new DialogHandler(this).getRemountDialog(id);
			} else {
				ShellCommunication.getInstance().mountSystemAsReadWrite();
			}

			break;
		case R.id.menu_reboot:
			dialog = new DialogHandler(this).getRebootDialog(id);
			break;
		// case R.id.menu_preferences:
		// Intent i = new Intent(this, Preference.class);
		// startActivity(i);
		// break;
		case R.id.menu_add_property:
			Intent ii = new Intent(this, AddProperty.class);
			startActivity(ii);
			break;
		case R.integer.su_need:
			dialog = new DialogHandler(this).getRootIsNeededDialog(id);
			break;
		default:
			dialog = null;
		}
		return dialog;

	}

	private boolean isFirstLaunch() {
		SharedPreferences settings = getSharedPreferences(Constants.prefFile, 0);
		return settings.getBoolean("firstlaunch", true);
	}

	private boolean showRemountDialog() {
		SharedPreferences settings = getSharedPreferences(Constants.prefFile, 0);
		return settings.getBoolean("show_remount_dialog", true);
	}

}
