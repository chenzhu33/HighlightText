package com.carelife.highlighter;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.carelife.highlighter.highlight.HighlightTextView;

import java.util.Arrays;

/**
 * For test
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HighlightTextView textView = (HighlightTextView) findViewById(R.id.text);
        textView.setDefaultColor(Color.BLUE);
        textView.setHighlightColor(Color.RED);

        String text = "Having a suite of unit and integration tests has many benefits.\n" +
                "In most cases they are there to provide confidence that changes have not broken existing behaviour.\n" +
                "Starting off with the less complex data classes was the clear choice for me. \n" +
                "They are being used throughout the project, yet their complexity is comparatively low. This makes them an ideal starting point to set off the journey into a new language.\n" +
                "After migrating some of these using the Kotlin code converter, which is built into Android Studio, executing tests and making them pass, I worked my way up until eventually ending up migrating the tests themselves to Kotlin.\n" +
                "Without tests, I would have been required to go through the touched features after each change, and manually verify them.\n" +
                "By having this automated it was a lot quicker and easier to move through the codebase, migrating code as I went along.\n" +
                "So, if you don’t have your app tested properly yet, there’s one more reason to do so right here";

        String[] keywords = new String[]{"a suite of", "has many benefits", "In most cases",
                "have not broken existing behaviour",
                "Starting off with", "choice for", "throughout the project", "starting point to",
                "move through the codebase", "one more reason to do"};

        textView.setDisplayedText(text, Arrays.asList(keywords));

    }
}
