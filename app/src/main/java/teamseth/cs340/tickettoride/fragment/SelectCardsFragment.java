package teamseth.cs340.tickettoride.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.tickettoride.R;

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
        ResourceColor color = claimThisRoute[0].getColor();
        switch (color) {
            case RAINBOW:
                break;
            case BLACK:
                final RadioButton blackBtn = view.findViewById(R.id.choose_black_cards);
                blackBtn.setVisibility(View.VISIBLE);
                break;
            case BLUE:
                final RadioButton blueBtn = view.findViewById(R.id.choose_blue_cards);
                blueBtn.setVisibility(View.VISIBLE);
                break;
            case ORANGE:
                final RadioButton orangeBtn = view.findViewById(R.id.choose_orange_cards);
                orangeBtn.setVisibility(View.VISIBLE);
                break;
            case YELLOW:
                final RadioButton yellowBtn = view.findViewById(R.id.choose_yellow_cards);
                yellowBtn.setVisibility(View.VISIBLE);
                break;
            case WHITE:
                final RadioButton whiteBtn = view.findViewById(R.id.choose_white_cards);
                whiteBtn.setVisibility(View.VISIBLE);
                break;
            case GREEN:
                final RadioButton greenBtn = view.findViewById(R.id.choose_green_cards);
                greenBtn.setVisibility(View.VISIBLE);
                break;
            case PURPLE:
                final RadioButton purpleBtn = view.findViewById(R.id.choose_purple_cards);
                purpleBtn.setVisibility(View.VISIBLE);
                break;
            case RED:
                final RadioButton redBtn = view.findViewById(R.id.choose_red_cards);
                redBtn.setVisibility(View.VISIBLE);
                break;
        }

        builder.setTitle("Choose Cards")
                //.setView(R.layout.dialog_select_cards)
                /*.setSingleChoiceItems(routes, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        selection[0] = arg1;
                    }
                })*/
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
                        if(useLocomotive.isChecked()) {
                            EditText number = (EditText) view.findViewById(R.id.number_rainbow_cards_to_use);
                            int numRainbowToUse = Integer.parseInt(number.getText().toString());
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
