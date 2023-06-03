package com.example.elixirmedicityhealthapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private ArrayList<String> arrayList;
    ArrayAdapter adapter;

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        SearchView searchView;
        ListView myListView;
        searchView = (SearchView) view.findViewById(R.id.SearchView);
        myListView = (ListView) view.findViewById(R.id.ListView);
        myListView.setVisibility(View.GONE);
        arrayList = new ArrayList<>();
        arrayList.add("Dr. Anshul Goyal");
        arrayList.add("Dr. P. K. Gupta");
        arrayList.add("Dr. MP Agrawal");
        arrayList.add("Dr. Pankaj Agrawal");
        arrayList.add("Dr. Geeta Mehta");
        arrayList.add("Dr. Charu Jain");
        arrayList.add("Dr. Naveen Kumar Agrawal");
        arrayList.add("Dr. Nirvikalp Agrawal");
        arrayList.add("Dr. Bhawna Gupta");
        arrayList.add("Dr. Shailendra Singh");


        adapter=new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, arrayList);
        myListView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit (String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange (String s) {
                adapter.getFilter().filter(s);
                myListView.setVisibility(View.VISIBLE);
                return false;

            }
        });

return view;

    }
}
