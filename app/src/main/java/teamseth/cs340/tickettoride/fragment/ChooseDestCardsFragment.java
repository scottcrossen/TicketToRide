package teamseth.cs340.tickettoride.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import teamseth.cs340.common.commands.server.InitialReturnDestinationCardCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.CommandTask;

/**
 * Created by ajols on 10/14/2017.
 */

public class ChooseDestCardsFragment extends Fragment implements View.OnClickListener {

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private Button chooseDestCardsBtn;
    private Optional<Caller> parent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            parent = Optional.of((Caller) getActivity());
        } catch (Exception e) {
            parent = Optional.empty();
        }
        //chooseDestCardsBtn.setEnabled(false);
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
        setDestinationCards(ClientModelRoot.getInstance().cards.getDestinationCards());
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
        List<DestinationCard> destinationCards = ClientModelRoot.getInstance().cards.getDestinationCards();
        DestinationCard returnCard = null;
        if (!checkBox1.isChecked()) {
            returnCard = destinationCards.get(0);
        }
        if (!checkBox2.isChecked()) {
            returnCard = destinationCards.get(0);
        }
        if (!checkBox3.isChecked()) {
            returnCard = destinationCards.get(0);
        }

        new CommandTask(getContext()).execute(new InitialReturnDestinationCardCommand(Optional.ofNullable(returnCard)));
        parent.map((Caller caller) -> { caller.onFragmentSuccess(); return caller; });
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

        if (count < 2) {
            chooseDestCardsBtn.setEnabled(false);
        }
        else if (count >= 2) {
            chooseDestCardsBtn.setEnabled(true);
        }
    }
}
