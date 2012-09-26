package de.bwulfert.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import de.bwulfert.buildpropedit.R;
import de.bwulfert.data.Descriptor;
import de.bwulfert.data.Property;
import de.bwulfert.debug.d;
import de.bwulfert.logical.Controller;

public class AddProperty extends Activity implements OnClickListener,
		OnItemSelectedListener {

	private Spinner keySpinner, valueSpinner;
	private ArrayList<String> descriptorValues, descriptorNames;
	private String propertyKey = "", propertyValue = "";
	private List<Descriptor> descriptorList;
	private ArrayAdapter<String> valueSpinnerAdapter;
	private Descriptor descriptor;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.add_property);

		descriptorList = Controller.getInstance().getDescriptors(
				getResources().getXml(R.xml.description_bundle));

		// keys - get the spinner-reference from the view
		keySpinner = (Spinner) findViewById(R.id.spinnerKeys);
		keySpinner.setPrompt("Select a key");
		// create a new keyName list
		descriptorNames = new ArrayList<String>();
		// get the descriptorNames from the descriptorList
		for (Descriptor description : descriptorList) {
			descriptorNames.add(description.getKeyName());
		}
		// create a new adapter
		ArrayAdapter<String> keySpinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, descriptorNames);
		// setup the new adapter
		keySpinner.setAdapter(keySpinnerAdapter);
		// setup the onItemSelectedListener

		// values - get the spinner-reference from the view
		valueSpinner = (Spinner) findViewById(R.id.spinnerValues);
		valueSpinner.setPrompt("Select a value");
		descriptorValues = new ArrayList<String>();
		valueSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, descriptorValues);
		valueSpinner.setAdapter(valueSpinnerAdapter);

		keySpinner.setOnItemSelectedListener(this);
		valueSpinner.setEnabled(false);
		valueSpinner.setOnItemSelectedListener(this);
						
	}

	boolean UIisReady = false;

	public void writeChangesToProp(View view) {
		
		TextView possiblePropertyKey = (TextView) findViewById(R.id.tv_propertyKey);
		propertyKey = possiblePropertyKey.getText().toString();

		TextView possiblePropertyValue = (TextView) findViewById(R.id.tv_PropertyValue);
		propertyValue = possiblePropertyValue.getText().toString();
		
		Property newProperty = new Property(propertyKey, propertyValue);

		if (Controller.getInstance().isPropertyAllreadyIn(newProperty)) {
			d.t(this, "Key already in file! If you want to update the Key, please choose it from the list");
		} else {
			Controller.getInstance().addProperty(newProperty);
			keySpinner.setSelection(0);
			valueSpinnerAdapter.clear();
			valueSpinner.setSelection(0);
			// finish();
		}

	}

	public void backToTop(View view) {
		finish();
	}

	public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

		if (UIisReady) {

			if (parentView.getId() == R.id.spinnerKeys) {
				
				propertyKey = ((TextView)selectedItemView).getText().toString();
				
				for (Descriptor desc : descriptorList) {
					if (desc.getKeyName().equals(propertyKey)) {
						descriptor = desc;
						break;
					}
				}
				

				valueSpinner.setEnabled(false);
				
				valueSpinnerAdapter.clear();
								
				descriptorValues = descriptor.getValues();

				for (String value : descriptorValues) {
					valueSpinnerAdapter.add(value);
				}

				
				valueSpinner.setEnabled(true);
				
				TextView description = ((TextView) findViewById(R.id.tvDescription));
				
				description.setText(descriptor.getDescription());
				
				TextView possiblePropertyKey = (TextView) findViewById(R.id.tv_propertyKey);
				
				possiblePropertyKey.setText(((TextView) selectedItemView).getText().toString());
				
			}else if (parentView.getId() == R.id.spinnerValues) {

								
				TextView possiblePropertyValue = (TextView) findViewById(R.id.tv_PropertyValue);

				possiblePropertyValue.setText(((TextView) selectedItemView).getText().toString());

				propertyValue = possiblePropertyValue.getText().toString();
			}
			
		}

		UIisReady = true;
	}

	public void onNothingSelected(AdapterView<?> parentView) {	}

	public void onClick(View v) {	}


}
