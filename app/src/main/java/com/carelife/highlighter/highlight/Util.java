package com.carelife.highlighter.highlight;

import android.util.ArrayMap;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * Created by chenzhuwei on 17/10/8.
 */
public class Util {

    /**
     * 将文本分割成单词结果，如果属于高亮显示的词，则作为一个结果，每个结果都会标注类型 普通、高亮词、回车
     *
     * @param text     需要分割的文本
     * @param keywords 高亮词list
     * @return 分割结果
     */
    public static List<SplitResult> splitToSingleWords(String text, List<String> keywords) {
        List<SplitResult> result = new ArrayList<>();
        Map<Integer, Pair<Integer, Integer>> matcherMap = new ArrayMap<>();
        for (String keyword : keywords) {
            Pattern p = Pattern.compile(keyword);
            Matcher m = p.matcher(text);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                matcherMap.put(start, new Pair<>(start, end));
            }
        }

        char[] textArray = text.toCharArray();
        int start = 0;
        int end;
        for (end = 0; end < text.length(); end++) {
            if (textArray[end] == ' ' || textArray[end] == '\n' || end == text.length() - 1) {
                if (end == text.length() - 1) {
                    end++;
                }
                Pair<Integer, Integer> pair = matcherMap.get(start);
                if (pair != null) {
                    if (pair.second == end || (pair.second == end - 1 && !isAlphabet(textArray[end - 1]))) {
                        // find one
                        result.add(new SplitResult(String.valueOf(textArray, start, end - start), SplitResult.TYPE_HIGHLIGHT));
                        start = end + 1;
                    }
                } else {
                    result.add(new SplitResult(String.valueOf(textArray, start, end - start), SplitResult.TYPE_NORMAL));
                    start = end + 1;
                }
                if (end == text.length()) {
                    end--;
                }
                if (textArray[end] == '\n') {
                    result.add(new SplitResult("\n", SplitResult.TYPE_RETURN));
                }
            }
        }
        return result;
    }

    private static boolean isAlphabet(char text) {
        return (text >= 'a' && text <= 'z') || (text >= '0' && text <= '9');
    }
}
