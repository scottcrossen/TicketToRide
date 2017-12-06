package teamseth.cs340.tickettoride.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.client.cards.CurrentCards;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.activity.MapActivity;
import teamseth.cs340.tickettoride.util.PlayerTurnTracker;
import teamseth.cs340.tickettoride.util.Toaster;

import static android.view.View.GONE;

/**
 * Created by macrow7 on 11/20/17.
 */

public class NewDestCardsFragment extends DialogFragment implements View.OnClickListener, Observer {

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private Button chooseDestCardsBtn;
    private Optional<Caller> parent;
    private List<DestinationCard> cardsToDecideOn = new LinkedList<DestinationCard>();
    private int timesUpdated = 0;
    private boolean lessThanThree = false;
    private boolean isTwo = false;
    private boolean isOne = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            parent = Optional.of((Caller) getActivity());
        } catch (Exception e) {
            parent = Optional.empty();
        }
        //chooseDestCardsBtn.setEnabled(false);
    }

    @Override
    public void update(Observable o, Object arg) {
        cardsToDecideOn = PlayerTurnTracker.getInstance().getDestinationCardsToDecideOn();
//        int destCardsLeftNum = 30 - ClientModelRoot.cards.others.getDestinationAmountUsed() - ClientModelRoot.cards.getDestinationCards().size();
//        String size = String.valueOf(destCardsLeftNum);
//        System.out.println(size);
//        System.out.println(cardsToDecideOn.size());
        timesUpdated++;
        System.out.println(timesUpdated);
        if (cardsToDecideOn.size() == 3) {
            setDestinationCards(PlayerTurnTracker.getInstance().getDestinationCardsToDecideOn());
        } else if (isTwo && timesUpdated > 1){
            setDestinationCards(PlayerTurnTracker.getInstance().getDestinationCardsToDecideOn());
        } else if (isOne && timesUpdated > 0){
            setDestinationCards(PlayerTurnTracker.getInstance().getDestinationCardsToDecideOn());
        }
//        else if (timesUpdated < 3){
//            update(o, arg);
//        }
//        else if(destCardsLeftNum < 3 && cardsToDecideOn.size() > 0){
//            setDestinationCards(PlayerTurnTracker.getInstance().getDestinationCardsToDecideOn());
//        }
        else {
            checkBox1.setText("Loading...");
            checkBox2.setText("Loading...");
            checkBox3.setText("Loading...");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ClientModelRoot.cards.deleteObserver(this);
    }

    public interface Caller {
        void onNewDestCardsChosen();
    }

    public void setLessThanThree(boolean lessThanThree) {
        this.lessThanThree = lessThanThree;
    }

    public void setTwo(boolean two) {
        isTwo = two;
    }

    public void setOne(boolean one) {
        isOne = one;
    }

    public void setDestinationCards(List<DestinationCard> cards) {
        Iterator<DestinationCard> iterator = cards.iterator();
        if (iterator.hasNext()) {
            DestinationCard next = iterator.next();
            checkBox1.setText(next.toString());
        }
        if (iterator.hasNext()) {
            DestinationCard next = iterator.next();
            checkBox2.setText(next.toString());
        }
        if (iterator.hasNext()) {
            DestinationCard next = iterator.next();
            checkBox3.setText(next.toString());
        }
//        if (isTwo){
//            checkBox3.setVisibility(GONE);
//        }
//        if (isOne){
//            checkBox2.setVisibility(GONE);
//            checkBox3.setVisibility(GONE);
//        }
    }
    @Override
    public void onResume() {
        super.onResume();
        ClientModelRoot.cards.addObserver(this);
        MapActivity map = (MapActivity) getActivity();
        map.disableDrawer();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        MapActivity map = (MapActivity) getActivity();
        map.enableDrawer();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_choose_dest_cards, container, false);
        ListView menu = (ListView) getActivity().findViewById(R.id.left_drawer);

        TextView loading = v.findViewById(R.id.loadingText);
        loading.setVisibility(GONE);
        checkBox1 = v.findViewById(R.id.checkbox1);
        checkBox1.setOnClickListener(this);
        checkBox2 = v.findViewById(R.id.checkbox2);
        checkBox2.setOnClickListener(this);
        checkBox3 = v.findViewById(R.id.checkbox3);
        checkBox3.setOnClickListener(this);
        chooseDestCardsBtn = v.findViewById(R.id.chooseDestCardsButton);
        chooseDestCardsBtn.setOnClickListener(this);
        chooseDestCardsBtn.setEnabled(false);
        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chooseDestCardsButton:
                try {
                    onButtonClicked();
                } catch (ResourceNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.checkbox1:
                enableButton();
                break;
            case R.id.checkbox2:
                enableButton();
                break;
            case R.id.checkbox3:
                enableButton();
                break;
        }
    }


    public void onButtonClicked() throws ResourceNotFoundException {
        List<DestinationCard> destinationCards = PlayerTurnTracker.getInstance().getDestinationCardsToDecideOn();
        List<DestinationCard> returnCards = new LinkedList<>();
        if (cardsToDecideOn.size() > 0 && !checkBox1.isChecked()) {
            returnCards.add(destinationCards.get(0));
        }
        if (cardsToDecideOn.size() > 1 && !checkBox2.isChecked()) {
            returnCards.add(destinationCards.get(1));
        }
        if (cardsToDecideOn.size() > 2 && !checkBox3.isChecked()) {
            returnCards.add(destinationCards.get(2));
        }
        if (cardsToDecideOn.size() > 0) {
            PlayerTurnTracker.getInstance().returnDrawnDestinationCards(getContext(), returnCards);
            dismiss();
        }
    }

    public void enableButton() {
        int count = 0;

        if (checkBox1.isChecked()) {
            count++;
        }

        if (checkBox2.isChecked()) {
            count++;
        }

        if (checkBox3.isChecked()) {
            count++;
        }

        if (count < 1) {
            chooseDestCardsBtn.setEnabled(false);
        } else if (count >= 1) {
            chooseDestCardsBtn.setEnabled(true);
        }
    }
}