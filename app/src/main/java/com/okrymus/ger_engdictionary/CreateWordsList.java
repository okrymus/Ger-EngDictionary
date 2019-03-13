package com.okrymus.ger_engdictionary;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panupong_lee on 3/27/17.
 */


    public class CreateWordsList extends Activity {
    private List<Word> mWords;
    private EditText mEnglish;
    private EditText mGerman;
    private CheckBox mNounCheckbox;
    private CheckBox mVerbCheckbox;
    private CheckBox mAdverbCheckbox;
    private CheckBox mAdjectiveCheckbox;
    private CheckBox mPrepositionCheckbox;
    private CheckBox mCardinalNumberCheckbox;
    private Button mNextButton;
    private Button mBackButton;
    private Button mClearButton;
    private ImageButton mAddButton;
    private ImageButton mRemoveButton;
    private int mCurrentIndex = 0;
    ArrayList<String> list ;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.content_createwordslist);

        //WordLab.get(this).addWord();
        mWords = WordLab.get(this).getWords();
        mCurrentIndex = mWords.size()-1;


            /********/
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            // Set window's size
            getWindow().setLayout((int)(width),(int)(height*.7));



        mGerman = (EditText)findViewById(R.id.inputGermanEditText);
        mEnglish = (EditText)findViewById(R.id.inputEnglishEditText);
        mNounCheckbox = (CheckBox) findViewById(R.id.checkbox_noun);
        mVerbCheckbox = (CheckBox) findViewById(R.id.checkbox_verb);

        mAdjectiveCheckbox = (CheckBox) findViewById(R.id.checkbox_adjective);
        mAdverbCheckbox = (CheckBox) findViewById(R.id.checkbox_adverb);
        mPrepositionCheckbox = (CheckBox) findViewById(R.id.checkbox_preposition);
        mCardinalNumberCheckbox = (CheckBox) findViewById(R.id.checkbox_cardinalNumber);

        setTitle(getResources().getString(R.string.title_activity_createWordList)+"  ("+(mCurrentIndex+1)+"/"+mWords.size()+")");
        // mWords = WordLab.get(this).getWords();

        if (mWords.size() != 0)
            updateUI();

        mBackButton = (Button) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentIndex == 0)
                    mCurrentIndex = mWords.size()-1;
                    else
                mCurrentIndex = (mCurrentIndex - 1) % mWords.size();

                setTitle(getResources().getString(R.string.title_activity_createWordList)+"  ("+(mCurrentIndex+1)+"/"+mWords.size()+")");
                updateUI();

            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentIndex == (mWords.size()+1)){
                    mCurrentIndex = 0;
                }
                else
                mCurrentIndex = (mCurrentIndex + 1) % mWords.size();

                setTitle(getResources().getString(R.string.title_activity_createWordList)+"  ("+(mCurrentIndex+1)+"/"+mWords.size()+")");
                updateUI();
            }
        });

        if (mWords.size()==0){
            mNextButton.setEnabled(false);
            mBackButton.setEnabled(false);
        }
        //list = new ArrayList<String>();

        mAddButton = (ImageButton) findViewById(R.id.addButton);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {

                    list = new ArrayList<String>();

                    if (mNounCheckbox.isChecked())
                        list.add("noun");
                    if (mVerbCheckbox.isChecked())
                        list.add("vverb");
                    if (mPrepositionCheckbox.isChecked())
                        list.add("preposition");
                    if (mAdjectiveCheckbox.isChecked())
                        list.add("adjective");
                    if (mAdverbCheckbox.isChecked())
                        list.add("adverb");
                    if (mCardinalNumberCheckbox.isChecked())
                        list.add("cardinal number");

                    WordLab.get(null).addWord(new Word(mGerman.getText().toString(), list.toString(), mEnglish.getText().toString()));
                  //  mGerman.setText(list.toString());
                    upDateList();
                    mCurrentIndex = mWords.size()-1;
                    updateUI();
                    setTitle(getResources().getString(R.string.title_activity_createWordList) + "  (" + (mCurrentIndex + 1) + "/" + mWords.size() + ")");

                    mRemoveButton.setEnabled(true);
                    mBackButton.setEnabled(true);
                    mNextButton.setEnabled(true);

                    if (mWords.size()== 1)
                        mRemoveButton.setEnabled(false);

                }
            }
        });

        mRemoveButton = (ImageButton) findViewById(R.id.removeImageButton);
        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWords.size()!=0) {
                    WordLab.get(null).removeWord(mWords.get(mCurrentIndex));
                    upDateList();
                    if (mCurrentIndex == mWords.size())
                        mCurrentIndex = 0;
                    updateUI();
                    setTitle(getResources().getString(R.string.title_activity_createWordList) + "  (" + (mCurrentIndex + 1) + "/" + mWords.size() + ")");


                }
                if (mWords.size() == 1 || mWords.size() == 0)
                    mRemoveButton.setEnabled(false);
            }
        });


        mClearButton = (Button) findViewById(R.id.clearButton);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGerman.setText("");
                mEnglish.setText("");

                mNounCheckbox.setChecked(false);
                mVerbCheckbox.setChecked(false);
                mPrepositionCheckbox.setChecked(false);
                mAdverbCheckbox.setChecked(false);
                mAdjectiveCheckbox.setChecked(false);
                mCardinalNumberCheckbox.setChecked(false);
            }
        });


        }

    //*****************************************************
    // Validate user's input
    //*****************************************************
    private boolean validateInput() {
        boolean isValidInput = true;

        // If text field is empty, process...
        if (mGerman.getText().toString().equals("")) {
            mGerman.setError(getString(R.string.msg_no_present));
            isValidInput = false;
        }
        // If text field is empty, process...
        if (mEnglish.getText().toString().equals("")) {
            mEnglish.setError(getString(R.string.msg_no_present));
            isValidInput = false;
        }
        return isValidInput;
    }

    private void upDateList(){ mWords= WordLab.get(this).getWords();}


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkbox_noun:
               //if (checked)list.add("noun"); else list.remove("noun"); break;
            case R.id.checkbox_verb:
                //if (checked) list.add("vverb"); else list.remove("vverb"); break;
            case R.id.checkbox_preposition:
                //if (checked)list.add("preposition");else list.remove("preposition"); break;
            case R.id.checkbox_adjective:
                //if (checked)list.add("adjective"); else list.remove("adjective"); break;
            case R.id.checkbox_adverb:
               //if (checked)list.add("adverb"); else list.remove("adverb"); break;
            case R.id.checkbox_cardinalNumber:
               //if (checked)list.add("cardinal number"); else list.remove("cardinal number");break;
        }
    }

    private void updateUI(){
        mGerman.setText(mWords.get(mCurrentIndex).getGerman());
        mEnglish.setText(mWords.get(mCurrentIndex).getEnglish());

        if (mWords.get(mCurrentIndex).getPartOfSpeech().contains("noun"))
            mNounCheckbox.setChecked(true);
        else if (!mWords.get(mCurrentIndex).getPartOfSpeech().contains("noun"))
            mNounCheckbox.setChecked(false);

        if (mWords.get(mCurrentIndex).getPartOfSpeech().contains("vverb"))
            mVerbCheckbox.setChecked(true);
        else if (!mWords.get(mCurrentIndex).getPartOfSpeech().contains("vverb"))
            mVerbCheckbox.setChecked(false);

        if (mWords.get(mCurrentIndex).getPartOfSpeech().contains("adjective"))
            mAdjectiveCheckbox.setChecked(true);
        else if (!mWords.get(mCurrentIndex).getPartOfSpeech().contains("adjective"))
            mAdjectiveCheckbox.setChecked(false);

        if (mWords.get(mCurrentIndex).getPartOfSpeech().contains("adverb"))
            mAdverbCheckbox.setChecked(true);
        else if (!mWords.get(mCurrentIndex).getPartOfSpeech().contains("adverb"))
            mAdverbCheckbox.setChecked(false);

        if (mWords.get(mCurrentIndex).getPartOfSpeech().contains("preposition"))
            mPrepositionCheckbox.setChecked(true);
        else if (!mWords.get(mCurrentIndex).getPartOfSpeech().contains("preposition"))
            mPrepositionCheckbox.setChecked(false);

        if (mWords.get(mCurrentIndex).getPartOfSpeech().contains("cardinal number"))
            mCardinalNumberCheckbox.setChecked(true);
        else if (!mWords.get(mCurrentIndex).getPartOfSpeech().contains("cardinal number"))
            mCardinalNumberCheckbox.setChecked(false);
    }

}




