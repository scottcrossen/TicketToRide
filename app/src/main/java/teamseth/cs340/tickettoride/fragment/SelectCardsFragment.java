package teamseth.cs340.tickettoride.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.util.PlayerTurnTracker;
import teamseth.cs340.tickettoride.util.Toaster;

import static teamseth.cs340.common.models.server.cards.ResourceColor.RAINBOW;

/**
 * Created by Seth on 11/20/2017.
 */

public class SelectCardsFragment extends DialogFragment {

    private Route[] claimThisRoute;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] route = {null};
        final int[] selection = {0};
        LayoutInflater linf = LayoutInflater.from(getActivity());
        final View view = linf.inflate(R.layout.dialog_select_cards, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        TextView yellowCardText;
        TextView blackCardText;
        TextView blueCardText;
        TextView greenCardText;
        TextView redCardText;
        TextView purpleCardText;
        TextView orangeCardText;
        TextView locomotiveCardText;
        TextView whiteCardText;
        builder.setView(view);
        yellowCardText = (TextView) view.findViewById(R.id.yellowTrainCardsLeft);
        blackCardText = (TextView) view.findViewById(R.id.blackTrainCardsLeft);
        blueCardText = (TextView) view.findViewById(R.id.blueTrainCardsLeft);
        greenCardText = (TextView) view.findViewById(R.id.greenTrainCardsLeft);
        redCardText = (TextView) view.findViewById(R.id.redTrainCardsLeft);
        purpleCardText = (TextView) view.findViewById(R.id.purpleTrainCardsLeft);
        orangeCardText = (TextView) view.findViewById(R.id.orangeTrainCardsLeft);
        locomotiveCardText = (TextView) view.findViewById(R.id.locomotiveCardsLeft);
        whiteCardText = (TextView) view.findViewById(R.id.whiteTrainCardsLeft);
        yellowCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.YELLOW)).count()));
        yellowCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        blackCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.BLACK)).count()));
        blackCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        blueCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.BLUE)).count()));
        blueCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        greenCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.GREEN)).count()));
        greenCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        redCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.RED)).count()));
        redCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        purpleCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.PURPLE)).count()));
        purpleCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        orangeCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.ORANGE)).count()));
        orangeCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        locomotiveCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.RAINBOW)).count()));
        locomotiveCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        whiteCardText.setText(Long.toString(ClientModelRoot.getInstance().cards.getResourceCards().stream().filter((ResourceColor color) -> color.equals(ResourceColor.WHITE)).count()));
        whiteCardText.setShadowLayer(15, 0, 0, Color.BLACK);
        final RadioGroup cardColor = view.findViewById(R.id.card_selected_to_use);
        final CheckBox useLocomotive = view.findViewById(R.id.choose_rainbow_cards);
        final RadioButton blackBtn = view.findViewById(R.id.choose_black_cards);
        final RadioButton blueBtn = view.findViewById(R.id.choose_blue_cards);
        final RadioButton orangeBtn = view.findViewById(R.id.choose_orange_cards);
        final RadioButton yellowBtn = view.findViewById(R.id.choose_yellow_cards);
        final RadioButton whiteBtn = view.findViewById(R.id.choose_white_cards);
        final RadioButton greenBtn = view.findViewById(R.id.choose_green_cards);
        final RadioButton purpleBtn = view.findViewById(R.id.choose_purple_cards);
        final RadioButton redBtn = view.findViewById(R.id.choose_red_cards);
        final RadioButton locoBtn = view.findViewById(R.id.choose_no_colored_cards);
        final EditText number = (EditText) view.findViewById(R.id.number_rainbow_cards_to_use);
        RadioButton[] colorButtons = new RadioButton[9];
        ResourceColor color = claimThisRoute[0].getColor();
        switch (color) {
            case RAINBOW:
                blackBtn.setVisibility(View.VISIBLE);
                blackBtn.setChecked(true);
                blueBtn.setVisibility(View.VISIBLE);
                orangeBtn.setVisibility(View.VISIBLE);
                yellowBtn.setVisibility(View.VISIBLE);
                whiteBtn.setVisibility(View.VISIBLE);
                greenBtn.setVisibility(View.VISIBLE);
                purpleBtn.setVisibility(View.VISIBLE);
                redBtn.setVisibility(View.VISIBLE);
                break;
            case BLACK:
                blackBtn.setVisibility(View.VISIBLE);
                blackBtn.setChecked(true);
                break;
            case BLUE:
                blueBtn.setVisibility(View.VISIBLE);
                blueBtn.setChecked(true);
                break;
            case ORANGE:
                orangeBtn.setVisibility(View.VISIBLE);
                orangeBtn.setChecked(true);
                break;
            case YELLOW:
                yellowBtn.setVisibility(View.VISIBLE);
                yellowBtn.setChecked(true);
                break;
            case WHITE:
                whiteBtn.setVisibility(View.VISIBLE);
                whiteBtn.setChecked(true);
                break;
            case GREEN:
                greenBtn.setVisibility(View.VISIBLE);
                greenBtn.setChecked(true);
                break;
            case PURPLE:
                purpleBtn.setVisibility(View.VISIBLE);
                purpleBtn.setChecked(true);
                break;
            case RED:
                redBtn.setVisibility(View.VISIBLE);
                redBtn.setChecked(true);
                break;
        }

        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                useLocomotive.setChecked(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        builder.setTitle("Choose Cards")
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setPositiveButton("Claim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        int routeLength = claimThisRoute[0].getLength();
                        ArrayList<ResourceColor> colors = new ArrayList<ResourceColor>();
                        ResourceColor col = ResourceColor.RAINBOW;
                        if(useLocomotive.isChecked()) {
                            String num = number.getText().toString();
                            if(!num.isEmpty()) {
                                int numRainbowToUse = Integer.parseInt(num);
                                if(numRainbowToUse >= routeLength)
                                {
                                    numRainbowToUse = routeLength;
                                }
                                for(int i = 0; i < numRainbowToUse; i++) {
                                    colors.add(RAINBOW);
                                }

                                routeLength = routeLength - numRainbowToUse;
                            }
                        }
                        if(blackBtn.isChecked()) {
                            col = ResourceColor.BLACK;
                        }
                        else if(blueBtn.isChecked()) {
                            col = ResourceColor.BLUE;
                        }
                        else if(greenBtn.isChecked()) {
                            col = ResourceColor.GREEN;
                        }
                        else if(orangeBtn.isChecked()){
                            col = ResourceColor.ORANGE;
                        }
                        else if(purpleBtn.isChecked()) {
                            col = ResourceColor.PURPLE;
                        }
                        else if(redBtn.isChecked()) {
                            col = ResourceColor.RED;
                        }
                        else if(whiteBtn.isChecked()) {
                            col = ResourceColor.WHITE;
                        }
                        else if(yellowBtn.isChecked()) {
                            col = ResourceColor.YELLOW;
                        }
                        else if(locoBtn.isChecked()) {
                            col = ResourceColor.RAINBOW;
                        }
                        for(int j = 0; j < routeLength; j++) {
                            //grab the radio button that is selected, and select that color
                            colors.add(col);
                        }
                        if(PlayerTurnTracker.getInstance().claimRoute(getContext(),claimThisRoute[0],colors))
                        {
                            if (Login.debug) Toaster.shortT(getContext(), "Claimed a Route");
                        }
                        else {
                            Toaster.shortT(getContext(), "Cannot Claim Route");
                        }
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void setRoute(Route[] actRoutes) {
        this.claimThisRoute = actRoutes;
    }
}
