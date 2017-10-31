package teamseth.cs340.tickettoride.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import teamseth.cs340.tickettoride.R;

/**
 * Created by Seth on 10/14/2017.
 */

public class HistoryFragment extends Fragment {
    public static final String ARG_TAB_NUMBER = "tab_number";

    public HistoryFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(this.getContext(), "CommandHistory", Toast.LENGTH_SHORT).show();
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        int i = getArguments().getInt(ARG_TAB_NUMBER);
        String title = getResources().getStringArray(R.array.tabs_array)[i];

        ListView listview = (ListView) rootView.findViewById(R.id.history_list);

        String[] historyList = new String[] { "proof", "that", "this", "list",
                "works"};

        //TODO add the list of history to this list so they will display

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                historyList);

        listview.setAdapter(listViewAdapter);

        getActivity().setTitle(title);
        return rootView;
    }
}
