package teamseth.cs340.tickettoride.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by Seth on 11/20/2017.
 */

public class SelectCardsFragment extends DialogFragment {

    private String[] routes;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] route = {null};
        builder.setTitle("Choose Cards")
                .setSingleChoiceItems(routes, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        route[0] = routes[arg1];
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setPositiveButton("Claim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //TODO do the choosing of the dialog box here
                        Toast.makeText(getContext(), "Chose on " + route[0], Toast.LENGTH_SHORT).show();
                        String route1 = "ooooo";
                        String route2 = "kkkkkk";
                        String[] rts = new String[2];
                        rts[0] = route1;
                        //rts[1] = route2;
                        SelectRouteFragment newFragment = new SelectRouteFragment();
                        newFragment.setArray(rts);
                        newFragment.show(getActivity().getFragmentManager(), "datePicker");
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void setArray(String[] rts) {
        routes = rts;
    }
}
