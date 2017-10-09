package teamseth.cs340.tickettoride.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class Toaster {
    private static Toaster instance;

    public static Toaster getInstance() {
        if(instance == null) {
            instance = new Toaster();
        }
        return instance;
    }

    public void makeToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
