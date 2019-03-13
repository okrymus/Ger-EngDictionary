package com.okrymus.ger_engdictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.text.Normalizer;
import java.util.regex.Pattern;


public class WordListFragment extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView mWordRecyclerView;
    private WordAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_word_list, container, false);

        mWordRecyclerView = (RecyclerView) view
                .findViewById(R.id.word_recycler_view);
        mWordRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_pupup, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(this);

        /*MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
// Do something when collapsed
                        mAdapter.setFilter(m);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
// Do something when expanded
                        return true; // Return true to expand action view
                    }
                });*/
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Word> filteredModelList = filter(WordLab.get(null).getWords(), newText);

        mAdapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Word> filter(List<Word> models, String query) {
        query = query.toLowerCase();final List<Word> filteredModelList = new ArrayList<>();
        for (Word model : models) {
            final String text = model.getGerman().toLowerCase();
            final String text2 = model.getEnglish().toLowerCase();
            final String text3 = deAccent(text);
            final String text4 = deAccent(text2);
            final String text5 = stripAccents(text);
            final String text6 = stripAccents(text2);

            if (text.contains(query))
                filteredModelList.add(model);
            else if (text2.contains(query))
                filteredModelList.add(model);
            else if (text3.contains(query))
                filteredModelList.add(model);
            else if (text4.contains(query))
                filteredModelList.add(model);
            else if (text5.contains(query))
                filteredModelList.add(model);
            else if (text6.contains(query))
                filteredModelList.add(model);

        }
        return filteredModelList;
    }

    public static String stripAccents(String s)
    {
        s = s.replaceAll("[^\\p{ASCII}]", "");
        return s;
    }

    public String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    private void updateUI() {
        WordLab wordLab = WordLab.get(getActivity());
        List<Word> words = wordLab.getWords();

        mAdapter = new WordAdapter(words);
        mWordRecyclerView.setAdapter(mAdapter);
    }

    private class WordHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView mGermanTextView;
        private TextView mEnglishTextView;
        private TextView mPartOfSpeechTextView;

        private Word mWord;

        public WordHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mGermanTextView = (TextView) itemView.findViewById(R.id.list_item_german_title_text_view);
            mEnglishTextView =(TextView) itemView.findViewById(R.id.list_item_english_text_view);
            mPartOfSpeechTextView =(TextView) itemView.findViewById(R.id.list_item_partOfSpeech_text_view);
        }

        public void bindWord(Word word) {
            mWord = word;
            mGermanTextView.setText(mWord.getGerman());
            mEnglishTextView.setText(mWord.getEnglish());

            List list = new ArrayList<String>();

            if (mWord.getPartOfSpeech().contains("noun"))
                list.add("noun");
            if (mWord.getPartOfSpeech().contains("vverb"))
                list.add("verb");
            if (mWord.getPartOfSpeech().contains("preposition"))
                list.add("preposition");
            if (mWord.getPartOfSpeech().contains("adjective"))
                list.add("adjective");
            if (mWord.getPartOfSpeech().contains("adverb"))
                list.add("adverb");
            if (mWord.getPartOfSpeech().contains("cardinal number"))
                list.add("cardinal number");

            String partOdSpeech = list.toString();
            partOdSpeech = partOdSpeech.substring(0,partOdSpeech.length()-1); ;
            partOdSpeech = partOdSpeech.substring(1);

            mPartOfSpeechTextView.setText(partOdSpeech);

        }

        @Override
        public void onClick(View v) {

                Intent intent = WordPagerActivity.newIntent(getActivity(), mWord.getGerman());
                startActivity(intent);

            /*Toast.makeText(getActivity(),


                    mWord.getGerman() + " clicked!", Toast.LENGTH_SHORT)
                    .show();*/
        }
    }

    private class WordAdapter extends RecyclerView.Adapter<WordHolder> {

        private List<Word> mWords;

        public WordAdapter(List<Word> words) {
            mWords = words;
        }

        @Override
        public WordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_word, parent, false);

            return new WordHolder(view);
        }

        @Override
        public void onBindViewHolder(WordHolder holder, int position) {
            Word word = mWords.get(position);
            holder.bindWord(word);
        }

        @Override
        public int getItemCount() {
            return mWords.size();
        }

        public void setFilter(List<Word> countryModels) {
            mWords = new ArrayList<>();
            mWords.addAll(countryModels);
            notifyDataSetChanged();
        }
    }
}
