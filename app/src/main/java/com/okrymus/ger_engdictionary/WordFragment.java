package com.okrymus.ger_engdictionary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WordFragment extends Fragment {

    private static final String ARG_WORD_ID = "word_id";

    private Word mWord;
    private TextView mGermanWordField;
    private TextView mPOSField;
    private TextView mEnglishWordField;

    public static WordFragment newInstance(String wordId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_WORD_ID, wordId);

        WordFragment fragment = new WordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String wordId = (String) getArguments().getSerializable(ARG_WORD_ID);
        mWord = WordLab.get(getActivity()).getWord(wordId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_word, container, false);

        mGermanWordField = (TextView) v.findViewById(R.id.german_word);
        mEnglishWordField = (TextView) v.findViewById(R.id.english_word);
        mPOSField = (TextView) v.findViewById(R.id.partOfSpeech);

        mGermanWordField.setText(mWord.getGerman());
        mEnglishWordField.setText(mWord.getEnglish());

        if (mWord.getPartOfSpeech().contains("vverb"))
            mPOSField.setText(mWord.getPartOfSpeech().replaceAll("vverb","verb"));
        else
        mPOSField.setText(mWord.getPartOfSpeech());


        return v;
    }
}
