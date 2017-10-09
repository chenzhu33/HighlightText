package com.carelife.highlighter;

import com.carelife.highlighter.highlight.SplitResult;
import com.carelife.highlighter.highlight.Util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 单元测试
 *
 * Created by chenzhuwei on 17/10/8.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 25)
public class SplitTest {
    @Test
    public void testSlitToWords() {
        String text = "Having a suite of unit and integration tests has many benefits.\n" +
                "In most cases they";

        String[] keywords = new String[]{"a suite of", "has many benefits", "In most cases",
                "have not broken existing behaviour",
                "Starting off with", "choice for", "throughout the project", "starting point to",
                "move through the codebase", "one more reason to do", "here"};

        List<SplitResult> result = Util.splitToSingleWords(text, Arrays.asList(keywords));

        List<SplitResult> expect = new ArrayList<>();
        expect.add(new SplitResult("Having", 0));
        expect.add(new SplitResult("a suite of", 1));
        expect.add(new SplitResult("unit", 0));
        expect.add(new SplitResult("and", 0));
        expect.add(new SplitResult("integration", 0));
        expect.add(new SplitResult("tests", 0));
        expect.add(new SplitResult("has many benefits.", 1));
        expect.add(new SplitResult("\n", 2));
        expect.add(new SplitResult("In most cases", 1));
        expect.add(new SplitResult("they", 0));

        Assert.assertArrayEquals(result.toArray(), expect.toArray());
    }

    @Test
    public void testEmptyKeywords() {
        String text = "Having a unit and";

        String[] keywords = new String[]{};

        List<SplitResult> result = Util.splitToSingleWords(text, Arrays.asList(keywords));

        List<SplitResult> expect = new ArrayList<>();
        expect.add(new SplitResult("Having", 0));
        expect.add(new SplitResult("a", 0));
        expect.add(new SplitResult("unit", 0));
        expect.add(new SplitResult("and", 0));

        Assert.assertArrayEquals(result.toArray(), expect.toArray());
    }

    @Test
    public void testEmptyText() {
        String text = "";

        String[] keywords = new String[]{"aa", "bb"};

        List<SplitResult> result = Util.splitToSingleWords(text, Arrays.asList(keywords));

        List<SplitResult> expect = new ArrayList<>();

        Assert.assertArrayEquals(result.toArray(), expect.toArray());
    }
}
