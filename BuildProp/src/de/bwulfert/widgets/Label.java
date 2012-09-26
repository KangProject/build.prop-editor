package de.bwulfert.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;
import de.bwulfert.buildpropedit.R;

public class Label extends TextView {

	private Paint p;
	public boolean centered = false;

	public Label(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		p = new Paint();
		p.setAntiAlias(true);
		p.setColor(R.color.main_text);
		p.setTextSize(getTextSize());	
				
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Label);
        boolean centered = a.getBoolean(R.styleable.Label_centered, false);
        
        this.centered = centered;
        
        a.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		p.setColor(R.color.main_text);
		if(centered){
			Rect r = new Rect();

			p.getTextBounds(getText().toString(), 0, getText().length(), r);
			
			canvas.drawText(getText().toString(), (getWidth() / 2) - (r.width() / 2), 0 + getTextSize() - 3, p);
		}else {
			canvas.drawText(getText().toString(), 4, 0 + getTextSize() - 3, p);
		}
		canvas.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2, p);
		p.setColor(Color.WHITE);
		canvas.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1, p);
	}

}
