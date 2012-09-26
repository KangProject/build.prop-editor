package de.bwulfert.widgets;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.Button;
import de.bwulfert.buildpropedit.R;

public class CustomButton extends Button {

	private Paint p;

	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		p = new Paint();
		p.setAntiAlias(true);
		p.setTextSize(getTextSize());
		setBackgroundDrawable(null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		innerRect = new RectF(3,3,getWidth() - 3, getHeight() - 3);
		outerRect = new RectF(2,2,getWidth() - 2, getHeight() - 2);
	}

	RectF innerRect, outerRect;
	
	@Override
	protected void onDraw(Canvas canvas) {
		p.setStyle(Style.STROKE);
		p.setColor(Color.WHITE);
		canvas.drawRoundRect(innerRect, 10, 10, p);
		p.setColor(getResources().getColor(R.color.main_text));
		canvas.drawRoundRect(outerRect, 10, 10, p);
		super.onDraw(canvas);
	}
}
