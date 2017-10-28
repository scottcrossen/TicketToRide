package teamseth.cs340.tickettoride.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.util.DrawView;

/**
 * Created by Seth on 10/14/2017.
 */

public class MapFragment extends Fragment {
    public static final String ARG_TAB_NUMBER = "tab_number";

    public MapFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(this.getContext(), "Map", Toast.LENGTH_SHORT).show();
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        int i = getArguments().getInt(ARG_TAB_NUMBER);
        String title = getResources().getStringArray(R.array.tabs_array)[i];
        RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativeMap);

        ImageView vancouver = (ImageView) rootView.findViewById(R.id.vancouverCity);
        ImageView seattle = (ImageView) rootView.findViewById(R.id.seattleCity);

        DrawView vancouverSeattle = new DrawView(this.getContext(),vancouver,seattle, Color.GRAY);
        vancouverSeattle.setBackgroundColor(Color.TRANSPARENT);

        relativeLayout.addView(vancouverSeattle,1500,800);
        //TODO add onclick events for the lines, so, vancouverSeattle.addOnclick() blah blah
        //rootView = drawLines(rootView);
        getActivity().setTitle(title);
        return rootView;
    }

    public View drawLines(View rootView) {
        RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativeMap);

        ImageView vancouver = (ImageView) rootView.findViewById(R.id.vancouverCity);
        ImageView seattle = (ImageView) rootView.findViewById(R.id.seattleCity);

        DrawView vancouverSeattle = new DrawView(this.getContext(),vancouver,seattle, Color.GRAY);
        vancouverSeattle.setBackgroundColor(Color.YELLOW);

        relativeLayout.addView(vancouverSeattle);
        return rootView;
    }
}