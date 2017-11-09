package teamseth.cs340.tickettoride.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teamseth.cs340.tickettoride.R;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class WaitingFragment extends Fragment implements View.OnClickListener
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_waiting, container, false);
        return v;
    }

    @Override
    public void onClick(View v) {

    }
}


