package teamseth.cs340.tickettoride.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

import teamseth.cs340.tickettoride.R;

/**
 * Created by ajols on 10/14/2017.
 */

public class ChooseDestCardsFragment extends Fragment implements View.OnClickListener {

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private Button chooseDestCardsBtn;
    private ArrayList destCards = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //chooseDestCardsBtn.setEnabled(false);
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
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chooseDestCardsButton:
                onButtonClicked();
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

    public void onButtonClicked() {

        if (checkBox1.isChecked()) {
            destCards.add("destCard1");
        }
        if (checkBox2.isChecked()) {
            destCards.add("destCard2");
        }
        if (checkBox3.isChecked()) {
            destCards.add("destCard3");
        }

        for (int i = 0; i < destCards.size(); i++) {

            Toast.makeText(getActivity(), "destCards[" + i + "]: " + destCards.get(i),
                    Toast.LENGTH_LONG).show();

            //System.out.println("destCards[" + i + "]: " + destCards.get(i));
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

        Toast.makeText(getActivity(), "count: " + count,
                Toast.LENGTH_LONG).show();

        if (count < 2) {
            chooseDestCardsBtn.setEnabled(false);
        }
        else if (count >= 2) {
            chooseDestCardsBtn.setEnabled(true);
        }
    }
}
