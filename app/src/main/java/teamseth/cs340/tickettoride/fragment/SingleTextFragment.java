package teamseth.cs340.tickettoride.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Optional;

import teamseth.cs340.tickettoride.R;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class SingleTextFragment
{
    public static V4Fragment newV4Instance(Optional<String> title, String text) {
        V4Fragment fragment = new V4Fragment();
        Bundle args = new Bundle();
        title.map((String mainTitle) -> { args.putString("title", mainTitle); return mainTitle; });
        args.putString("mainText", text);
        fragment.setArguments(args);
        return fragment;
    }

    public static V1Fragment newV1Instance(Optional<String> title, String text) {
        V1Fragment fragment = new V1Fragment();
        Bundle args = new Bundle();
        title.map((String mainTitle) -> { args.putString("title", mainTitle); return mainTitle; });
        args.putString("mainText", text);
        fragment.setArguments(args);
        return fragment;
    }

    public static class V4Fragment extends android.support.v4.app.Fragment
    {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_single_text, container, false);
            Bundle args = getArguments();
            String mainText = args.getString("mainText");
            TextView mainTextHolder = v.findViewById(R.id.mainText);
            mainTextHolder.setText(mainText);
            try {
                String title = args.getString("title");
                if (title != null && title != "") {
                    getActivity().setTitle(title);
                }
            } catch (Exception e) {
            }
            return v;
        }
    }

    public static class V1Fragment extends android.app.Fragment
    {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_single_text, container, false);
            Bundle args = getArguments();
            String mainText = args.getString("mainText");
            TextView mainTextHolder = v.findViewById(R.id.mainText);
            System.out.println(mainText);
            mainTextHolder.setText(mainText);
            try {
                String title = args.getString("title");
                if (title != null && title != "") {
                    getActivity().setTitle(title);
                }
            } catch (Exception e) {
            }
            return v;
        }
    }
}