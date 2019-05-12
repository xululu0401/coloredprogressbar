package com.xululu.coloredprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author: pipilu
 * Time: 2019/5/12 11:46
 */
public class ColoredProgressBar extends View {

    //外层圆环的画笔
    private Paint mOutCirPaint;
    //内层圆环的画笔
    private Paint mInnerCirPaint;
    //字体画笔
    private Paint mTextPaint;
    //字体颜色
    private int mTextColor;
    //字体大小
    private int mTextSize;
    //圆环起始颜色
    private int mStatrColor;
    //圆环终止颜色
    private int mEndColor;
    //底部圆形颜色
    private int mInnerCirColor;
    //圆环颜色
    private int mOutCirColor;
    //总进度·
    private int mTotalProgress = 100;
    //当前进度
    private int mCurrentProgress;
    //圆环半径
    private float mRadius;
    //分段颜色
    private int[] SECTION_COLORS = new int[2];
    //是否展示文字
    private boolean mCanshowText;
    //字体高度，用于指定字体的位置
    private float mTextHeight;
    //字体的宽度
    private float mTextWidth;
    //圆环kuandu
    private float mStrokeWidth;


    public ColoredProgressBar(Context context) {
        this(context, null);
    }

    public ColoredProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColoredProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ColoredProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrbutes(context, attrs);
        initTools();
    }

    private void initAttrbutes(Context context, AttributeSet attrs) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.lib_view_ColoredProgressbar, 0, 0);
        mRadius = array.getDimension(R.styleable.lib_view_ColoredProgressbar_lib_view_radius, 80);
        mTextColor = array.getColor(R.styleable.lib_view_ColoredProgressbar_lib_view_cusPbTextColor, Color.WHITE);
        mTextSize = array.getDimensionPixelSize(R.styleable.lib_view_ColoredProgressbar_lib_view_cusPbTextSize, 12);
        mStatrColor = array.getColor(R.styleable.lib_view_ColoredProgressbar_lib_view_startColor, Color.RED);
        mEndColor = array.getColor(R.styleable.lib_view_ColoredProgressbar_lib_view_endColor, Color.BLUE);
        mInnerCirColor = array.getColor(R.styleable.lib_view_ColoredProgressbar_lib_view_inner_circleColor, Color.parseColor("#0000ee"));
        mOutCirColor = array.getColor(R.styleable.lib_view_ColoredProgressbar_lib_view_circleColor, Color.BLUE);
        mCanshowText = array.getBoolean(R.styleable.lib_view_ColoredProgressbar_lib_view_text_visible, true);
        mStrokeWidth = array.getDimension(R.styleable.lib_view_ColoredProgressbar_lib_view_strokeWidth, 10);
        SECTION_COLORS[0] = mStatrColor;
        SECTION_COLORS[1] = mEndColor;
    }

    /**
     * 对画笔工具进行初始化
     */
    private void initTools() {
        mOutCirPaint = new Paint();
        mOutCirPaint.setAntiAlias(true);
        mOutCirPaint.setDither(true);
        mOutCirPaint.setStyle(Paint.Style.STROKE);
        mOutCirPaint.setStrokeWidth(mStrokeWidth);

        mInnerCirPaint = new Paint();
        mInnerCirPaint.setAntiAlias(true);
        mInnerCirPaint.setDither(true);
        mInnerCirPaint.setStyle(Paint.Style.STROKE);
        mInnerCirPaint.setStrokeWidth(mStrokeWidth);
        mInnerCirPaint.setColor(mInnerCirColor);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTextHeight = fm.descent + Math.abs(fm.ascent);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        SweepGradient sweepGradient = new SweepGradient(getWidth() / 2, getHeight() / 2 - mRadius - mStrokeWidth, SECTION_COLORS, null);
        LinearGradient gradient = new LinearGradient(0, 0, getWidth(),
                getHeight(), SECTION_COLORS, null, Shader.TileMode.REPEAT);
        mOutCirPaint.setShader(sweepGradient);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(getWidth() / 2 - mRadius, getHeight() / 2 - mRadius,
                getWidth() / 2 + mRadius, getHeight() / 2 + mRadius);
        canvas.drawArc(rectF, -90, 360, false, mInnerCirPaint);
        canvas.drawArc(rectF, -90, ((float) mCurrentProgress / mTotalProgress) * 360, false, mOutCirPaint);
        if (mCanshowText) {
            String text = mCurrentProgress + "%";
            mTextWidth = mTextPaint.measureText(text, 0, text.length());
            canvas.drawText(text, getWidth() / 2 - mTextWidth / 2, getHeight() / 2 + mTextHeight / 2, mTextPaint);

        }
    }

    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        mCurrentProgress = progress;
        postInvalidate();
    }

    public int getTotalProgress() {
        return mTotalProgress;
    }
}
