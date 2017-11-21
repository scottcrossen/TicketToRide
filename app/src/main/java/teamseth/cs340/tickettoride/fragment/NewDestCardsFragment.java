package teamseth.cs340.tickettoride.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import teamseth.cs340.common.commands.server.InitialReturnDestinationCardCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.CommandTask;
import teamseth.cs340.tickettoride.util.PlayerTurnTracker;

/**
 * Created by macrow7 on 11/20/17.
 */

public class NewDestCardsFragment extends Fragment implements View.OnClickListener, Observer {

private CheckBox checkBox1;
private CheckBox checkBox2;
private CheckBox checkBox3;
private Button chooseDestCardsBtn;
private Optional<Caller> parent;
private LinkedList<DestinationCard> test = new LinkedList<DestinationCard>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        setMenuVisibility(false);
        try {
        parent = Optional.of((Caller) getActivity());
        } catch (Exception e) {
        parent = Optional.empty();
        }
        //chooseDestCardsBtn.setEnabled(false);
        }

    @Override
    public void update(Observable o, Object arg) {
        //ClientModelRoot.getInstance().cards.getDestinationCards().stream().forEach((DestinationCard card) -> System.out.println(card.toString()));
        if (PlayerTurnTracker.getInstance().getDestinationCardsToDecideOn().size() > 0){
            setDestinationCards(PlayerTurnTracker.getInstance().getDestinationCardsToDecideOn());
        } else {
            checkBox1.setText("Loading...");
            checkBox2.setText("Loading...");
            checkBox3.setText("Loading...");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ClientModelRoot.cards.addObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ClientModelRoot.cards.deleteObserver(this);
    }

    public interface Caller {
    void onFragmentSuccess();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_choose_dest_cards, container, false);
        checkBox1 = v.findViewById(R.id.checkbox1);
        checkBox1.setOnClickListener(this);
        checkBox2 = v.findViewById(R.id.checkbox2);
        checkBox2.setOnClickListener(this);
        checkBox3 = v.findViewById(R.id.checkbox3);
        checkBox3.setOnClickListener(this);
        chooseDestCardsBtn = v.findViewById(R.id.chooseDestCardsButton);
        chooseDestCardsBtn.setOnClickListener(this);
        chooseDestCardsBtn.setEnabled(false);

//        test = ClientModelRoot.getInstance().cards.getDestinationCards();
//
////        setDestinationCards(ClientModelRoot.getInstance().cards.getDestinationCards());
//
////                System.out.println(ClientModelRoot.cards.getDestinationCards());
//        test.add(new DestinationCard(CityName.Vancouver, CityName.SaltLakeCity, 90));
//        test.add(new DestinationCard(CityName.Calgary, CityName.Raleigh, 90));
//        test.add(new DestinationCard(CityName.Toronto, CityName.SantaFe, 90));
//        test.add(new DestinationCard(CityName.Boston, CityName.ElPaso, 90));
//        setDestinationCards(test);

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
                //ClientModelRoot.getInstance().cards.getDestinationCards();
        List<DestinationCard> returnCards = new LinkedList<>();
        if(!checkBox1.isChecked()){
            returnCards.add(destinationCards.get(0));
        }
        if(!checkBox2.isChecked()){
            returnCards.add(destinationCards.get(1));
        }
        if(!checkBox3.isChecked()){
            returnCards.add(destinationCards.get(2));
        }
        PlayerTurnTracker.getInstance().returnDrawnDestinationCards(getContext(), returnCards);
//        android.app.FragmentManager fm = getFragmentManager();
//        fm.beginTransaction().replace(R.id.content_frame, new PlayerFragment());
        getActivity().onBackPressed();


        parent.map((NewDestCardsFragment.Caller caller) -> { caller.onFragmentSuccess(); return caller; });
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
        }
        else if (count >= 1) {
            chooseDestCardsBtn.setEnabled(true);
        }//hello
    }
}
//love andrew