package com.okrymus.ger_engdictionary;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/*
 * Panupong Leenawarat
 * Project 3 German-English Dictionary
 * CIT 243 Android Programming
 */

public class DictionaryActivity extends AppCompatActivity {
    private static List<Word> mWords;
    private final short sNOUN = 1;
    private final short sVERB = 2;
    private final short sPREPOSITION = 3;
    private final short sADJECIVE = 4;
    private final short sADVERB = 5;
    private final short sCN = 6;
    private final short sDefault = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dictionary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        WordLab.get(getApplicationContext()).setFunction((short)0);
        mWords = WordLab.get(null).getWords();

        switch (item.getItemId()) {
            case R.id.action_option1:
                if (mWords.size()==0)
                showWarningPopUp();
                else {
                    AlertDialog.Builder warningFileExistedBuilder = new AlertDialog.Builder(this);
                    warningFileExistedBuilder.setTitle(getResources().getString(R.string.alertFileExistedDialog));

                    warningFileExistedBuilder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    showWarningPopUp();
                                }
                            });
                    warningFileExistedBuilder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    AlertDialog warnDialog = warningFileExistedBuilder.create();
                    warnDialog.show();
                }
                return true;
            case R.id.action_option2:
                WordLab.get(null).setFunction(sNOUN);
                startActivity(new Intent(getApplicationContext(), WordListActivity.class));      // Pop up window to display program's info
                return true;
            case R.id.action_option3:
                WordLab.get(null).setFunction(sVERB);
                startActivity(new Intent(getApplicationContext(), WordListActivity.class));      // Pop up window to display program's info
                return true;
            case R.id.action_option4:
                WordLab.get(null).setFunction(sPREPOSITION);
                startActivity(new Intent(getApplicationContext(), WordListActivity.class));      // Pop up window to display program's info
                return true;
            case R.id.action_option5:
                WordLab.get(null).setFunction(sADJECIVE);
                startActivity(new Intent(getApplicationContext(), WordListActivity.class));      // Pop up window to display program's info
                return true;
            case R.id.action_option6:
                WordLab.get(null).setFunction(sADVERB);
                startActivity(new Intent(getApplicationContext(), WordListActivity.class));      // Pop up window to display program's info
                return true;
            case R.id.action_option7:
                WordLab.get(null).setFunction(sCN);
                startActivity(new Intent(getApplicationContext(), WordListActivity.class));      // Pop up window to display program's info
                return true;
            case R.id.action_option8:
                WordLab.get(null).setFunction(sDefault);
                startActivity(new Intent(getApplicationContext(), WordListActivity.class));      // Pop up window to display program's info
                return true;
            case R.id.action_option9:
                AlertDialog.Builder warningExitBuilder = new AlertDialog.Builder(this);
                warningExitBuilder.setTitle(getResources().getString(R.string.alertExitDialog));

                warningExitBuilder.setPositiveButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(1);      // Exit the program
                            }
                        });
                AlertDialog warnDialog = warningExitBuilder.create();
                warnDialog.show();                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {

        menu.findItem(R.id.action_option1).setEnabled(mWords==null);
        menu.findItem(R.id.action_option2).setEnabled(mWords!=null);
        menu.findItem(R.id.action_option3).setEnabled(mWords!=null);
        menu.findItem(R.id.action_option4).setEnabled(mWords!=null);
        menu.findItem(R.id.action_option5).setEnabled(mWords!=null);
        menu.findItem(R.id.action_option6).setEnabled(mWords!=null);
        menu.findItem(R.id.action_option7).setEnabled(mWords!=null);
        menu.findItem(R.id.action_option8).setEnabled(mWords!=null);

        return true;
    }

    //*****************************************************
    // To pop-up alert automatically create word list message
    //*****************************************************
    private void showWarningPopUp() {
        AlertDialog.Builder warningCreateWordsBuilder = new AlertDialog.Builder(this);
        warningCreateWordsBuilder.setTitle(getResources().getString(R.string.alertCreateWordsDialog));
        WordLab.get(getApplicationContext()).addWord();

         WordLab.get(getApplicationContext()).resetList();
        warningCreateWordsBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       // if (mWords == null)
                        WordLab.get(getApplicationContext()).addWord();
                        startActivity(new Intent(getApplicationContext(), CreateWordsList.class));      // Pop up window to display program's info
                        mWords = WordLab.get(null).getWords();
                    }
                });

        warningCreateWordsBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), CreateWordsList.class));      // Pop up window to display program's info
                mWords = WordLab.get(getApplicationContext()).getWords();
            } });

        AlertDialog warnDialog = warningCreateWordsBuilder.create();
        warnDialog.show();


    }
    }

