package com.okrymus.ger_engdictionary;

import android.support.v7.app.AppCompatActivity;

import java.text.Collator;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import android.content.Context;

import android.util.Log;
/**
 * Created by panupong_lee on 3/27/17.
 */

public class WordLab extends AppCompatActivity {
    private static final String TAG = "WordLab";
    private static final String FILENAME = "words.java";

    private static WordLab sWordLab ;
    private ArrayList<Word> mWords;
    private DictionaryJSONSerializer mSerializer;
    private Context mAppContext;


    private final short sNOUN = 1;
    private final short sVERB = 2;
    private final short sPREPOSITION = 3;
    private final short sADJECIVE = 4;
    private final short sADVERB = 5;
    private final short sCN = 6;

    private static short setGetFunction = 0;

    public static WordLab get(Context context) {
        if (sWordLab == null) {
            sWordLab = new WordLab(context);
        }
        return sWordLab;
    }

    private WordLab(Context context) {
        mAppContext = context;
        mSerializer = new DictionaryJSONSerializer(context, FILENAME);

        try {
            mWords = mSerializer.loadWords();
        } catch (Exception e) {
            mWords = new ArrayList<Word>();
            Log.e(TAG, "Error loading words: ", e);
        }
    }

    public void resetList(){
        mWords = new ArrayList<Word>();
    }

    public void addWord(Word c) {
        mWords.add(c);
        Collections.sort(mWords, new MyComparator());
        saveWords();
    }

    public boolean saveWords() {
        try {
            mSerializer.saveWords(mWords);
            Log.d(TAG, "words saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving words: " + e);
            return false;
        }
    }

    public void removeWord(Word c) {
        mWords.remove(c);
        saveWords();
    }

    public void addWord(){
        mWords.add(new Word("der Mann","noun","man"));
        mWords.add(new Word("das Öl","noun","oil"));
        mWords.add(new Word("die Frau","noun","woman"));
        mWords.add(new Word("das Fräulein","noun","Miss"));
        mWords.add(new Word("das Mädchen","noun","girl"));
        mWords.add(new Word("das Kind","noun","child"));
        mWords.add(new Word("das Haus","noun","house"));
        mWords.add(new Word("die Schule","noun","school"));
        mWords.add(new Word("die Tür","noun","door"));
        mWords.add(new Word("kommen","vverb","to come"));
        mWords.add(new Word("gehen","vverb","to go"));
        mWords.add(new Word("sehen","vverb","to see"));
        mWords.add(new Word("wollen","vverb","to want"));
        mWords.add(new Word("wissen","vverb","to know"));
        mWords.add(new Word("können","vverb","to be able"));
        mWords.add(new Word("vor","preposition","before"));
        mWords.add(new Word("nach","preposition","after"));
        mWords.add(new Word("zu","preposition","to"));
        mWords.add(new Word("bis","preposition","until"));
        mWords.add(new Word("ohne","preposition","without"));
        mWords.add(new Word("eins","cardinal number","one"));
        mWords.add(new Word("zwei","cardinal number","two"));
        mWords.add(new Word("drei","cardinal number","three"));
        mWords.add(new Word("hoch","adverb","high"));
        mWords.add(new Word("gut","adjective/adverb","good"));

        Collections.sort(mWords, new MyComparator());
        saveWords();
    }

    public List<Word> getWords() {
        switch (setGetFunction) {
            case sNOUN:
                return getListOfNoun();
            case sVERB:
                return getListOfVerb();
            case sPREPOSITION:
                return getListOfPreposition();
            case sADJECIVE:
                return getListOfAdjective();
            case sADVERB:
                return getListOfAdverb();
            case sCN: return getListOfCN();
                default: return mWords;
        }
    }

    public void setFunction(short f){
        setGetFunction = f;
    }

    public short getFunction(){
        return setGetFunction;
    }

    public List<Word> getListOfNoun(){
         List<Word> mList = new ArrayList<Word>();

        for (Word word: mWords) {
            if (word.getPartOfSpeech().contains("noun"))
                mList.add(word);
        }
        return mList;
    }

    public List<Word> getListOfVerb(){
        List<Word> mList = new ArrayList<Word>();

        for (Word word: mWords) {
            if (word.getPartOfSpeech().contains("vverb"))
                mList.add(word);
        }
        return mList;
    }

    public List<Word> getListOfAdjective(){
        List<Word> mList = new ArrayList<Word>();

        for (Word word: mWords) {
            if (word.getPartOfSpeech().contains("adjective"))
                mList.add(word);
        }
        return mList;
    }

    public List<Word> getListOfAdverb(){
        List<Word> mList = new ArrayList<Word>();

        for (Word word: mWords) {
            if (word.getPartOfSpeech().contains("adverb"))
                mList.add(word);
        }
        return mList;
    }

    public List<Word> getListOfPreposition(){
        List<Word> mList = new ArrayList<Word>();

        for (Word word: mWords) {
            if (word.getPartOfSpeech().contains("preposition"))
                mList.add(word);
        }
        return mList;
    }

    public List<Word> getListOfCN(){
        List<Word> mList = new ArrayList<Word>();

        for (Word word: mWords) {
            if (word.getPartOfSpeech().contains("cardinal number"))
                mList.add(word);
        }
        return mList;
    }


    public Word getWord(String german) {
        for (Word word : mWords) {
            if (word.getGerman().equals(german)) {
                return word;
            }
        }
        return null;
    }

    class MyComparator implements Comparator<Word> {
        public int compare(Word arg1, Word arg2) {
            Collator germanCollator = Collator.getInstance(Locale.GERMAN); //Your locale here
            germanCollator.setStrength(Collator.PRIMARY);
            String word1 = arg1.getGerman();
            String word2 = arg2.getGerman();
            if (arg1.getPartOfSpeech().contains("noun")){
                //word1 = word1.substring(3);
                word1= word1.replaceAll("der ","");
                word1= word1.replaceAll("das ","");
                word1= word1.replaceAll("die ","");
                word1 = word1.toLowerCase();
            }else
                word1 = word1.toLowerCase();

            if (arg2.getPartOfSpeech().contains("noun")){
               // word1 = word1.substring(3);
                word2 = word2.replaceAll("der ","");
                word2 = word2.replaceAll("das ","");
                word2 = word2.replaceAll("die ","");
                word2 = word2.toLowerCase();
            }else
                word2 = word2.toLowerCase();

            return germanCollator.compare(word1, word2);
        }
        }
}

