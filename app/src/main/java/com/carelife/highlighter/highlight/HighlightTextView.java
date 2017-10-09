package com.carelife.highlighter.highlight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.List;

/**
 * 高亮词自定义TextView
 *
 * Created by chenzhuwei on 17/10/8.
 */
public class HighlightTextView extends TextView {

    private int defaultColor;
    private int highlightColor;
    private List<String> keywords;

    /**
     * 缓存
     */
    private List<SplitResult> cache;
    private boolean cacheInvalidate;

    public HighlightTextView(Context context) {
        this(context, null);
    }

    public HighlightTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HighlightTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HighlightTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // TODO 需要重算宽高
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint paint = super.getPaint();
        Paint.FontMetrics fm = paint.getFontMetrics();

        float spaceWidth = getPaint().measureText(" ");
        float baseline = fm.descent - fm.ascent;
        float x;
        float y = baseline;

        float textWidth = 0;
        float measureWidth;
        if (cache == null || cacheInvalidate) {
            cache = Util.splitToSingleWords(getText().toString(), keywords);
            cacheInvalidate = false;
        }
        for (SplitResult result : cache) {
            if (result.type == SplitResult.TYPE_RETURN) {
                textWidth = 0;
                y += baseline + fm.leading;
            } else {
                float totalWidth = getWidth() - getPaddingRight() - getPaddingLeft();
                measureWidth = paint.measureText(result.text);
                if (textWidth + measureWidth > totalWidth) {
                    textWidth = 0;
                    x = getPaddingLeft();
                    y += baseline + fm.leading;
                    result.text = result.text + " ";
                    textWidth += measureWidth + spaceWidth;
                } else if (textWidth + measureWidth + spaceWidth > totalWidth) {
                    x = textWidth;
                    textWidth += measureWidth;
                } else {
                    result.text = result.text + " ";
                    x = textWidth;
                    textWidth += measureWidth + spaceWidth;
                }

                if (result.type == SplitResult.TYPE_NORMAL) {
                    paint.setColor(defaultColor);
                } else {
                    paint.setColor(highlightColor);
                }
                canvas.drawText(result.text, x, y, paint);
            }
        }
    }

    /**
     * 设置默认色
     * @param color 颜色
     */
    public void setDefaultColor(@ColorInt int color) {
        defaultColor = color;
    }

    /**
     * 设置高亮色
     * @param color 颜色
     */
    public void setHighlightColor(@ColorInt int color) {
        highlightColor = color;
    }

    /**
     * 设置需要展示的文本
     * @param text 内容
     * @param highlighted 高亮词
     */
    public void setDisplayedText(String text, List<String> highlighted) {
        this.keywords = highlighted;
        cacheInvalidate = true;
        setText(text);
    }
}
