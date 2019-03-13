package com.okrymus.ger_engdictionary;

import android.support.v4.app.Fragment;

public class WordListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new WordListFragment();
    }
}
