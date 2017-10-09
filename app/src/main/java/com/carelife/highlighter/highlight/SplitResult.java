package com.carelife.highlighter.highlight;

/**
 * 分割结果
 *
 * Created by chenzhuwei on 17/10/8.
 */
public class SplitResult {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HIGHLIGHT = 1;
    public static final int TYPE_RETURN = 2;

    /**
     * 分割结果词
     */
    public String text;

    /**
     * 类型
     */
    public final int type;

    public SplitResult(String text, int type) {
        this.text = text;
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SplitResult
                && this.text.equals(((SplitResult) obj).text)
                && this.type == ((SplitResult) obj).type;
    }
}
