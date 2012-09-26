package de.bwulfert.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomizedTextView extends TextView {

	public CustomizedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Arial.ttf");
		
		setTypeface(tf);
	}
	

}
