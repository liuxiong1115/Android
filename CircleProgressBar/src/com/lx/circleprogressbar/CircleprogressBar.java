package com.lx.circleprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircleprogressBar extends View {
	/**
	 * 扫描模式
	 */
	public static final int MODE_SCANNING = 0;
	/**
	 * 填充模式
	 */
	public static final int MODE_FILL = 1;
	/**
	 * 默认初始角度
	 */
	private final float DEFAULT_INITIAL_ANGLE = 90;
	/**
	 * 画板所处矩形区域
	 */
	private RectF mCircleRectF = new RectF();
	/**
	 * 模式
	 */
	private int mMode = MODE_SCANNING;
	
	private Paint mBottomPaint;
	private Paint mSectorPaint;
	private Paint mTextPaint;
	
	/**
	 * 初始角度
	 */
	private float initialAngle = DEFAULT_INITIAL_ANGLE;
	private float mMax = 100;
	private float mProgress = 0;
	private String mCurrentDrawText = "0%";
	
	public CircleprogressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray mCircleProgressBar = getContext().obtainStyledAttributes(attrs,R.styleable.CircleProgressBar);
		int mode = mCircleProgressBar.getInt(R.styleable.CircleProgressBar_mode, 0);
		this.mMode = mode;//设置模式
		
		int mMax = mCircleProgressBar.getInt(R.styleable.CircleProgressBar_max, 100);
		this.mMax = mMax;
		
		float initialAngle = mCircleProgressBar.getFloat(R.styleable.CircleProgressBar_initialAngle, DEFAULT_INITIAL_ANGLE);
		this.initialAngle = initialAngle;
		
		mBottomPaint = new Paint();
		mBottomPaint.setStyle(Paint.Style.FILL);// 充满
		mBottomPaint.setAntiAlias(true);// 设置画笔的锯齿效果
		int bottomCirCleColor = mCircleProgressBar.getColor(R.styleable.CircleProgressBar_bottom_circle_color, Color.LTGRAY);
		mBottomPaint.setColor(bottomCirCleColor);

		mSectorPaint = new Paint();
		mSectorPaint.setStyle(Paint.Style.FILL);// 充满
		mSectorPaint.setAntiAlias(true);// 设置画笔的锯齿效果
		int topCircleColor = mCircleProgressBar.getColor(R.styleable.CircleProgressBar_top_circle_color, Color.parseColor("#3498DB"));
		mSectorPaint.setColor(topCircleColor);
		
		mTextPaint = new Paint();
		mTextPaint.setStrokeWidth(10);
		mTextPaint.setStyle(Paint.Style.FILL);// 充满
		mTextPaint.setAntiAlias(true);// 设置画笔的锯齿效果
		float textSize = mCircleProgressBar.getFloat(R.styleable.CircleProgressBar_textSize , 20);
		textSize = DisplayUtil.sp2px(context, textSize);
		mTextPaint.setTextSize(textSize);
		
		int textColor = mCircleProgressBar.getInt(R.styleable.CircleProgressBar_textColor , 0);
		if(textColor != 0)
			mTextPaint.setColor(textColor);
		else
			mTextPaint.setColor(Color.WHITE);
	}

	public CircleprogressBar(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		mCircleRectF.left = 0;
		mCircleRectF.top = 0;
		mCircleRectF.right = getWidth();
		mCircleRectF.bottom = getHeight();
	}

	/**
	 * 设置进度
	 * @author 刘雄   2015-6-25 下午4:09:30
	 * @param pro
	 * @return void
	 */
	public void setProgress(int pro) {
		mProgress = pro;
		invalidate();
	}

	/**
	 * 获得当前进度值
	 * @author 刘雄   2015-6-25 下午4:10:54
	 * @return
	 * @return float
	 */
	public float getProgress() {
		return mProgress;
	}

	/**
	 * 设置最大进度值
	 * @author 刘雄   2015-6-25 下午4:11:19
	 * @param max
	 * @return void
	 */
	public void setMax(float max) {
		this.mMax = max;
	}

	/**
	 * 获取最大进度值
	 * @author 刘雄   2015-6-25 下午4:12:11
	 * @return
	 * @return float
	 */
	public float getMax() {
		return mMax;
	}
	
	/**
	 * 设置模式
	 * @author 刘雄   2015-6-25 下午4:13:16
	 * @param mode
	 * @return void
	 */
	public void setMode(int mode){
		this.mMode = mode;
		postInvalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawBottomCircle(canvas);
		if(mMode == MODE_SCANNING)
			drawTopScanningCircle(canvas);
		if(mMode == MODE_FILL)
			drawTopFillCircle(canvas);
		drawCurrentText(canvas);
	}

	/**
	 * 绘制底部背景
	 * @author 刘雄   2015-6-25 下午4:13:40
	 * @param canvas
	 * @return void
	 */
	private void drawBottomCircle(Canvas canvas) {
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2,
				mBottomPaint);//
	}

	/**
	 * 绘制填充模式进度
	 * @author 刘雄   2015-6-25 下午4:14:02
	 * @param canvas
	 * @return void
	 */
	private void drawTopFillCircle(Canvas canvas) {
		canvas.drawArc(mCircleRectF , getStartAngle() , getSweepAngle() , false, mSectorPaint);
	}
	
	/**
	 * 绘制扫描模式进度
	 * @author 刘雄   2015-6-25 下午4:17:41
	 * @param canvas
	 * @return void
	 */
	private void drawTopScanningCircle(Canvas canvas) {
		canvas.drawArc(mCircleRectF , -90 , getSweepAngle() , true, mSectorPaint);
	}
	
	
	/**
	 * 绘制文本
	 * @param canvas
	 */
	private void drawCurrentText(Canvas canvas) {
		int percent = (int)((getProgress() / getMax()) * 100);
		mCurrentDrawText = percent + "%";
		float textWidth = mTextPaint.measureText(mCurrentDrawText);   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间 
		FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();  
	    int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top; 
		canvas.drawText(mCurrentDrawText,mCircleRectF.right/2 - textWidth/2, baseline , mTextPaint);
	}
	
	/**
	 * 计算开始角度
	 * @author 刘雄   2015-6-25 下午4:14:43
	 * @return
	 * @return float
	 */
	private float getStartAngle(){
		float startAngle = DEFAULT_INITIAL_ANGLE - (getProgress() * 180 / getMax());
		return startAngle;
	}
	
	/**
	 * 计算应扫描角度
	 * @author 刘雄   2015-6-19 下午4:35:03
	 * @return 返回当前应扫描过的角度
	 */
	private float getSweepAngle(){
		float sweepAngle = (getProgress() * 360 / getMax());
		return sweepAngle;
	}
}
