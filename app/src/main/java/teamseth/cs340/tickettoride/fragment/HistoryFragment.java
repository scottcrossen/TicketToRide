package teamseth.cs340.tickettoride.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.tickettoride.R;

/**
 * Created by Seth on 10/14/2017.
 */

public class HistoryFragment extends Fragment implements IUpdatableFragment {
    public static final String ARG_TAB_NUMBER = "tab_number";

    public HistoryFragment() {
        // Empty constructor required for fragment subclasses
    }

    ListView listview;

    public void update() {
        List<String> history = null;
        try {
            history = ClientModelRoot.getInstance().history.getHistory();
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        String[] historyList = new String[history.size()];
        history.toArray(historyList);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                historyList);

        listview.setAdapter(listViewAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        int i = getArguments().getInt(ARG_TAB_NUMBER);
        String title = getResources().getStringArray(R.array.tabs_array)[i];

        listview = (ListView) rootView.findViewById(R.id.history_list);

        update();

        getActivity().setTitle(title);
        return rootView;
    }
}
