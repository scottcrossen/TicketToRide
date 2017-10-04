package teamseth.cs340.tickettoride.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import teamseth.cs340.tickettoride.Activity.GameListActivity;
import teamseth.cs340.tickettoride.R;

/**
 * Created by mike on 10/4/17.
 */

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    private ArrayList<String> mGameLists;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mGameListLabel;
        private TextView mGameListDescription;
        private Switch mGameListSwitch;
        private String mGameList;
        private OnGameListSwitchChangedListener mCallback;

        private static final String FILTER_KEY = "FILTER";

        public ViewHolder(View v)
        {
            super(v);

            mGameListLabel = (TextView) v.findViewById(R.id.game_name);
            mGameListDescription = (TextView) v.findViewById(R.id.game_players);
//            mGameListSwitch = (Switch) v.findViewById(R.id.filter_switch);
//            mGameListSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    String filter = (String) buttonView.getText();
////                    filter.toLowerCase();
//                    mCallback.onGameListSwitchFlipped(isChecked, filter);
//                }
//            });
//            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent filterIntent = new Intent(context, GameListActivity.class);
            filterIntent.putExtra(FILTER_KEY, mGameList);
            context.startActivity(filterIntent);
            //May not need
        }

        public void bindGameList(String filter)
        {
            // TODO: 10/4/17 refactor for game list
//            mGameList = filter;
//            if(filter.contentEquals("mother") || filter.contentEquals("father"))
//            {
//                filter = filter.substring(0,1).toUpperCase() + filter.substring(1).toLowerCase();
//                mGameListLabel.setText(filter + "'s Side");
//                mGameListDescription.setText("FILTER BY " + filter.toUpperCase() + "'S SIDE OF FAMILY");
//                if(filter.contentEquals("mother")) mGameListSwitch.setChecked(FamilyMap.SINGLETON.isMotherSide());
//                else mGameListSwitch.setChecked(FamilyMap.SINGLETON.isFatherSide());
//            } else if(filter.contentEquals("male") || filter.contentEquals("female"))
//            {
//                filter = filter.substring(0,1).toUpperCase() + filter.substring(1).toLowerCase();
//                mGameListLabel.setText(filter + " Events");
//                mGameListDescription.setText("FILTER EVENTS BASED ON GENDER");
//                filter = filter.toLowerCase();
//                if(filter.contentEquals("female")) mGameListSwitch.setChecked(FamilyMap.SINGLETON.isFemaleEvents());
//                else mGameListSwitch.setChecked(FamilyMap.SINGLETON.isMaleEvents());
//            } else
//            {
//                filter = filter.substring(0,1).toUpperCase() + filter.substring(1).toLowerCase();
//                mGameListLabel.setText(filter + " Events");
//                mGameListDescription.setText("FILTER BY " + filter.toUpperCase() + " EVENTS");
//                filter = filter.toLowerCase();
//                FamilyMap.SINGLETON.addTypeEvent(filter, true);
//                mGameListSwitch.setChecked(FamilyMap.SINGLETON.isTypeEvent(filter));
//            }
//            filter.toLowerCase();
//            mGameListSwitch.setText(filter);
//            mGameListSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    String filter = (String) buttonView.getText();
//                    filter = filter.toLowerCase();
//                    if(filter.contentEquals("mother")) FamilyMap.SINGLETON.setMotherSide(isChecked);
//                    else if(filter.contentEquals("father")) FamilyMap.SINGLETON.setFatherSide(isChecked);
//                    else if(filter.contentEquals("male")) FamilyMap.SINGLETON.setMaleEvents(isChecked);
//                    else if(filter.contentEquals("female")) FamilyMap.SINGLETON.setFemaleEvents(isChecked);
//                    else FamilyMap.SINGLETON.setTypeEvent(filter, isChecked);
////                    filter.toLowerCase();
////                    mCallback.onGameListSwitchFlipped(isChecked, filter);
//                }
//            });
        }
    }

    public GameListAdapter(ArrayList<String> mTextViews) {
        this.mGameLists = mTextViews;
    }
    @Override
    public GameListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_list_item, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(GameListAdapter.ViewHolder holder, int position) {
        String itemGameList = mGameLists.get(position);
        holder.bindGameList(itemGameList);
    }

    @Override
    public int getItemCount() {
        return mGameLists.size();
    }

    public interface OnGameListSwitchChangedListener
    {
        void onGameListSwitchFlipped(boolean isChecked, String filter);
    }
}
