package teamseth.cs340.tickettoride.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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

import java.util.ArrayList;

import teamseth.cs340.common.commands.server.ClaimRouteCommand;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.communicator.CommandTask;

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

        builder.setView(view);

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
                        //TODO do the choosing of the dialog box here
                        //route[0] = routes[selection[0]];
                        //TODO check the selected cards here
                        //maybe set the number of each card the player has on the screen
                        //don't allow player to go above the length of route
                        //Select which color of card to use, then ask for number of RAINBOW cards they want to use
                        //TODO Claim the route here
                        //this is the route-> claimThisRoute[0];
                        int routeLength = claimThisRoute[0].getLength();
                        ArrayList<ResourceColor> colors = new ArrayList<ResourceColor>();
                        ResourceColor col = ResourceColor.RAINBOW;
                        if(useLocomotive.isChecked()) {
                            String num = number.getText().toString();
                            if(!num.isEmpty()) {
                                int numRainbowToUse = Integer.parseInt(num);
                                if(numRainbowToUse > routeLength)
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

                        try {
                            new CommandTask(getContext()).execute(
                                    new ClaimRouteCommand(claimThisRoute[0].getCity1(),
                                            claimThisRoute[0].getCity2(),
                                            colors));
                        } catch (ResourceNotFoundException e) {
                            e.printStackTrace();
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
