package com.okrymus.ger_engdictionary;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by panupong_lee on 3/29/17.
 */

public class WordActivity extends SingleFragmentActivity {

    private static final String EXTRA_WORD_ID =
            "com.okrymus.ger_engdictionary.word_id";

    public static Intent newIntent(Context packageContext, String wordId) {
        Intent intent = new Intent(packageContext, WordActivity.class);
        intent.putExtra(EXTRA_WORD_ID, wordId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String wordId = (String) getIntent()
                .getSerializableExtra(EXTRA_WORD_ID);
        return WordFragment.newInstance(wordId);
    }

}
