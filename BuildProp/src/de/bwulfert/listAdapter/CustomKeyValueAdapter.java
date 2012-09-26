package de.bwulfert.listAdapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.bwulfert.buildpropedit.R;
import de.bwulfert.data.Property;

public class CustomKeyValueAdapter extends ArrayAdapter<Property> {

	private final Activity activity;
	private final List<Property> properties;

	public CustomKeyValueAdapter(Activity activity, List<Property> list) {
		super(activity, R.layout.property_listing_pairitem, list);
		this.activity = activity;
		this.properties = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;
		Property property = null;
		
		if(rowView == null)
		{
			// Get a new instance of the row layout view
			LayoutInflater inflater = activity.getLayoutInflater();

			//new layout!
			rowView = inflater.inflate(R.layout.property_listing_tweak, null);

			//old layout
//			rowView = inflater.inflate(R.layout.property_listing_pairitem, null);
			
			// Hold the view objects in an object,
			// so they don't need to be re-fetched
			property = properties.get(position);

			// Cache the view objects in the tag,
			// so they can be re-accessed later
			rowView.setTag(property);
			
		} else {
			property = (Property) rowView.getTag();
			property = properties.get(position);
		}
		
		ImageView icon = ((ImageView) rowView.findViewById(R.id.descriped));
		
		if(property.isDescribed()){
			icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.note_available));
		} else {
			icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.note_unavailable));
		}
						
		((TextView) rowView.findViewById(R.id.key)).setText(property.getKey());
		((TextView) rowView.findViewById(R.id.value)).setText(property.getValue());
		
		return rowView;
	}
}
