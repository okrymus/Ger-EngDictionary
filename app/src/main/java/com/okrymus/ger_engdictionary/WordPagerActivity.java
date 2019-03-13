package com.okrymus.ger_engdictionary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class WordPagerActivity extends AppCompatActivity {

    private static final String EXTRA_WORD_ID =
            "com.okrymus.ger_engdictionary.word_id";

    private ViewPager mViewPager;
    private List<Word> mWords;

    public static Intent newIntent(Context packageContext, String wordId) {
        Intent intent = new Intent(packageContext, WordPagerActivity.class);
        intent.putExtra(EXTRA_WORD_ID, wordId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_pager);

        String wordId = (String) getIntent()
                .getSerializableExtra(EXTRA_WORD_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_word_pager_view_pager);

        setTitle("Definitions");


        mWords = WordLab.get(this).getWords();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Word word = mWords.get(position);
                return WordFragment.newInstance(word.getGerman());
            }

            @Override
            public int getCount() {
                return mWords.size();
            }
        });

        for (int i = 0; i < mWords.size(); i++) {
            if (mWords.get(i).getGerman().equals(wordId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
