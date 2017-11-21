package teamseth.cs340.tickettoride.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import teamseth.cs340.common.models.server.boards.Route;

/**
 * Created by Seth on 11/20/2017.
 */

public class SelectRouteFragment extends DialogFragment {

    private String[] routes;
    private Route[] actRoutes;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] route = {null};
        final Route[] actRoute = {null};
        final int[] selection = {0};
        builder.setTitle("Select Route")
                .setSingleChoiceItems(routes, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        selection[0] = arg1;
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setPositiveButton("Claim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        actRoute[0] = actRoutes[selection[0]];
                        SelectCardsFragment newFragment = new SelectCardsFragment();
                        newFragment.setRoute(actRoute);
                        newFragment.show(getActivity().getFragmentManager(), "Choose Cards");
                    }
                })
                ;
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void setArray(String[] rts) {
        routes = rts;
    }

    public void setRoutes(Route[] actRoutes) {
        this.actRoutes = actRoutes;
    }
}
