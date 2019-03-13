package com.okrymus.ger_engdictionary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();
    private final short sNOUN = 1;
    private final short sVERB = 2;
    private final short sPREPOSITION = 3;
    private final short sADJECIVE = 4;
    private final short sADVERB = 5;
    private final short sCN = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        switch (WordLab.get(this).getFunction()){
            case sNOUN: setTitle("German to English nouns"); break;
            case sVERB: setTitle("German to English verbs"); break;
            case sPREPOSITION: setTitle("German to English prepositions"); break;
            case sADJECIVE: setTitle("German to English adjectives"); break;
            case sADVERB: setTitle("German to English adverbs"); break;
            case sCN: setTitle("German to English cardinal numbers"); break;
            default: setTitle("Search");
        }

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

}
