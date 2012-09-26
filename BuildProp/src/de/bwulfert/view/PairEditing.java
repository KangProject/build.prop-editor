package de.bwulfert.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import de.bwulfert.buildpropedit.R;
import de.bwulfert.data.EnrichedProperty;
import de.bwulfert.logical.Controller;
import de.bwulfert.system.Constants;

public class PairEditing extends Activity implements OnClickListener {

	private EnrichedProperty sessionProperty;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.property_editing);

		String key = getIntent().getExtras().getString("key");

		sessionProperty = Controller.getInstance().getEnrichedProperty(key,
				getResources().getXml(R.xml.description_bundle));

		((TextView) findViewById(R.id.tvHeader)).setText(key);
		((TextView) findViewById(R.id.tvDescription)).setText(sessionProperty
				.getDescription());
		((TextView) findViewById(R.id.tv_currentValue))
				.setText(getString(R.string.Current_Value)
						+ sessionProperty.getCurrentValue());

		boolean valueHasDescriptor = !sessionProperty.getKey()
				.equals("unknown");

		RadioGroup rbg = ((RadioGroup) findViewById(R.id.rg_recommendedValues));
		rbg.removeAllViews();

		if (Constants.has_root) {
			RadioButton rb = new RadioButton(this);
			rb.setOnClickListener(this);
			rb.setTextSize(11);
			rb.setText(Constants.custom_value);
			rb.setTextColor(getResources().getColor(R.color.main_text));
			rbg.addView(rb);

			if (valueHasDescriptor) {
				if (sessionProperty.getValues().size() != 0) {
					for (String value : sessionProperty.getValues()) {
						rb = new RadioButton(this);
						rb.setOnClickListener(this);
						rb.setTextColor(getResources().getColor(
								R.color.main_text));
						rb.setText(value);
						rb.setTextSize(11);
						rbg.addView(rb);
					}
				}
			}
		}

		captionFromSelectedRadioButton = sessionProperty.getCurrentValue();
	}

	@Override
	protected void onResume() {
		Button saveValueToFile = ((Button) findViewById(R.id.bt_saveToPropFile));
		if (!Constants.has_root) {
			saveValueToFile.setVisibility(View.GONE);
		}
		captionFromSelectedRadioButton = sessionProperty.getCurrentValue();
		super.onResume();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
	}

	public void writeChangesToProp(View view) {

		// Update the current property
		sessionProperty.setCurrentValue(captionFromSelectedRadioButton);

		// Update the controller
		Controller.getInstance().modifyProperty(
				getIntent().getStringExtra("key"),
				captionFromSelectedRadioButton);

		// Update the TextView
		((TextView) findViewById(R.id.tv_currentValue))
				.setText(getString(R.string.Current_Value)
						+ sessionProperty.getCurrentValue());

		// Making toast..
		Toast.makeText(this, R.string.Toast_BP_Saved, Toast.LENGTH_SHORT)
				.show();

		finish();

	}

	public void backToTop(View view) {
		finish();
	}

	private String captionFromSelectedRadioButton = "";

	public void onClick(View v) {

		captionFromSelectedRadioButton = ((RadioButton) v).getText().toString();
		// sessionProperty.setCurrentValue(captionFromSelectedRadio);

		if (captionFromSelectedRadioButton.startsWith(Constants.custom_value)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			final EditText editText = new EditText(v.getContext());

			editText.setText(sessionProperty.getCurrentValue());

			if (sessionProperty.getKeyboardLayout().equals(
					Constants.KeyboardLayout_NumBlock)) {
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			} else if (sessionProperty.getKeyboardLayout().equals(
					Constants.KeyboardLayout_Default)
					|| sessionProperty.getKeyboardLayout().equals(
							Constants.KeyboardLayout_CharacterInput)
					|| sessionProperty.getKeyboardLayout().equals(
							Constants.KeyboardLayout_FileDialog)) {
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			}

			builder.setView(editText);
			builder.setMessage(R.string.pairediting_custom_value)
					.setCancelable(false)
					.setPositiveButton(R.string.mkay,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									captionFromSelectedRadioButton = editText
											.getText().toString();
									dialog.dismiss();
								}
							})
					.setNegativeButton(R.string.button_cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			builder.create().show();
		}

	}

}
