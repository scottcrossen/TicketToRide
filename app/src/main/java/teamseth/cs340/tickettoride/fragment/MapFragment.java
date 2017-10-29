package teamseth.cs340.tickettoride.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.util.DrawView;

/**
 * Created by Seth on 10/14/2017.
 */

public class MapFragment extends Fragment {
    public static final String ARG_TAB_NUMBER = "tab_number";

    public MapFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(this.getContext(), "Map", Toast.LENGTH_SHORT).show();
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        int i = getArguments().getInt(ARG_TAB_NUMBER);
        String title = getResources().getStringArray(R.array.tabs_array)[i];
        RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativeMap);

        ImageView vancouver = (ImageView) rootView.findViewById(R.id.vancouverCity);
        ImageView seattle = (ImageView) rootView.findViewById(R.id.seattleCity);
        ImageView portland = (ImageView) rootView.findViewById(R.id.portlandCity);
        ImageView sanFrancisco = (ImageView) rootView.findViewById(R.id.sanFranciscoCity);
        ImageView losAngeles = (ImageView) rootView.findViewById(R.id.losAngelesCity);
        ImageView phoenix = (ImageView) rootView.findViewById(R.id.phoenixCity);
        ImageView lasVegas = (ImageView) rootView.findViewById(R.id.lasVegasCity);
        ImageView elPaso = (ImageView) rootView.findViewById(R.id.elPasoCity);
        ImageView santaFe = (ImageView) rootView.findViewById(R.id.santFeCity);
        ImageView saltLake = (ImageView) rootView.findViewById(R.id.saltLakeCity);
        ImageView calgary = (ImageView) rootView.findViewById(R.id.calgaryCity);
        ImageView helena = (ImageView) rootView.findViewById(R.id.helenaCity);
        ImageView denver = (ImageView) rootView.findViewById(R.id.denverCity);
        ImageView winnipeg = (ImageView) rootView.findViewById(R.id.winnipegCity);
        ImageView duluth = (ImageView) rootView.findViewById(R.id.duluthCity);
        ImageView omaha = (ImageView) rootView.findViewById(R.id.omahaCity);
        ImageView oklahoma = (ImageView) rootView.findViewById(R.id.oklahomaCity);
        ImageView kansas = (ImageView) rootView.findViewById(R.id.kansasCity);
        ImageView dallas = (ImageView) rootView.findViewById(R.id.dallasCity);
        ImageView houston = (ImageView) rootView.findViewById(R.id.houstonCity);
        ImageView newOrleans = (ImageView) rootView.findViewById(R.id.newOrleansCity);
        ImageView littleRock = (ImageView) rootView.findViewById(R.id.littleRockCity);
        ImageView saintLouis = (ImageView) rootView.findViewById(R.id.saintLouisCity);
        ImageView chicago = (ImageView) rootView.findViewById(R.id.chicagoCity);
        ImageView nashville = (ImageView) rootView.findViewById(R.id.nashvilleCity);
        ImageView miami = (ImageView) rootView.findViewById(R.id.miamiCity);
        ImageView atlanta = (ImageView) rootView.findViewById(R.id.atlantaCity);
        ImageView charleston = (ImageView) rootView.findViewById(R.id.charlestonCity);
        ImageView raleigh = (ImageView) rootView.findViewById(R.id.raleighCity);
        ImageView dc = (ImageView) rootView.findViewById(R.id.dcCity);
        ImageView pittsburgh = (ImageView) rootView.findViewById(R.id.pittsburghCity);
        ImageView newYork = (ImageView) rootView.findViewById(R.id.newYorkCity);
        ImageView boston = (ImageView) rootView.findViewById(R.id.bostonCity);
        ImageView toronto = (ImageView) rootView.findViewById(R.id.torontoCity);
        ImageView saultStMarie = (ImageView) rootView.findViewById(R.id.saultStMarieCity);
        ImageView montreal = (ImageView) rootView.findViewById(R.id.montrealCity);

        // TODO potentially add in the x and y offsets so the lines look cleaner, not as necessary though
        DrawView vancouverSeattle = new DrawView(this.getContext(),vancouver,seattle, Color.GRAY);
        vancouverSeattle.setBackgroundColor(Color.TRANSPARENT);
        DrawView seattlePortland = new DrawView(this.getContext(),seattle,portland, Color.GRAY);
        seattlePortland.setBackgroundColor(Color.TRANSPARENT);
        DrawView vancouverCalgary = new DrawView(this.getContext(),vancouver,calgary, Color.GRAY);
        vancouverCalgary.setBackgroundColor(Color.TRANSPARENT);
        DrawView calgaryWinnipeg = new DrawView(this.getContext(),calgary,winnipeg, Color.WHITE);
        calgaryWinnipeg.setBackgroundColor(Color.TRANSPARENT);
        DrawView seattleCalgary = new DrawView(this.getContext(),seattle,calgary, Color.GRAY);
        seattleCalgary.setBackgroundColor(Color.TRANSPARENT);
        DrawView calgaryHelena = new DrawView(this.getContext(),calgary,helena, Color.GRAY);
        calgaryHelena.setBackgroundColor(Color.TRANSPARENT);
        DrawView portlandSaltLake = new DrawView(this.getContext(),portland,saltLake, Color.BLUE);
        portlandSaltLake.setBackgroundColor(Color.TRANSPARENT);
        DrawView portlandSanFran = new DrawView(this.getContext(),portland,sanFrancisco, Color.GREEN);
        portlandSanFran.setBackgroundColor(Color.TRANSPARENT);
        DrawView sanFranSaltLake = new DrawView(this.getContext(),sanFrancisco,saltLake, Color.WHITE);
        sanFranSaltLake.setBackgroundColor(Color.TRANSPARENT);
        DrawView sanFranLosAngeles = new DrawView(this.getContext(),sanFrancisco,losAngeles, Color.YELLOW);
        sanFranLosAngeles.setBackgroundColor(Color.TRANSPARENT);
        DrawView losAngelesLasVegas = new DrawView(this.getContext(),losAngeles,lasVegas, Color.GRAY);
        losAngelesLasVegas.setBackgroundColor(Color.TRANSPARENT);
        //TODO get orange color
        //DrawView lasVegasSaltLake = new DrawView(this.getContext(),lasVegas,saltLake, Color.ORANGE);
        //lasVegasSaltLake.setBackgroundColor(Color.TRANSPARENT);
        DrawView losAngelesPhoenix = new DrawView(this.getContext(),losAngeles,phoenix, Color.GRAY);
        losAngelesPhoenix.setBackgroundColor(Color.TRANSPARENT);
        DrawView losAngelesElPaso = new DrawView(this.getContext(),losAngeles,elPaso, Color.BLACK);
        losAngelesElPaso.setBackgroundColor(Color.TRANSPARENT);
        DrawView phoenixDenver = new DrawView(this.getContext(),phoenix,denver, Color.WHITE);
        phoenixDenver.setBackgroundColor(Color.TRANSPARENT);
        DrawView phoenixSantaFe = new DrawView(this.getContext(),phoenix,santaFe, Color.GRAY);
        phoenixSantaFe.setBackgroundColor(Color.TRANSPARENT);
        DrawView phoenixElPaso = new DrawView(this.getContext(),phoenix,elPaso, Color.GRAY);
        phoenixElPaso.setBackgroundColor(Color.TRANSPARENT);
        DrawView seattleHelena = new DrawView(this.getContext(),seattle,helena, Color.YELLOW);
        seattleHelena.setBackgroundColor(Color.TRANSPARENT);
        //TODO get pink color
        //DrawView saltLakeHelena = new DrawView(this.getContext(),saltLake,helena, Color.PINK);
        //saltLakeHelena.setBackgroundColor(Color.TRANSPARENT);
        DrawView saltLakeDenver = new DrawView(this.getContext(),saltLake,denver, Color.RED);
        saltLakeDenver.setBackgroundColor(Color.TRANSPARENT);
        DrawView helenaWinnipeg = new DrawView(this.getContext(),helena,winnipeg, Color.BLUE);
        helenaWinnipeg.setBackgroundColor(Color.TRANSPARENT);
        //DrawView helenaDuluth = new DrawView(this.getContext(),helena,duluth, Color.ORANGE);
        //helenaDuluth.setBackgroundColor(Color.TRANSPARENT);
        DrawView helenaOmaha = new DrawView(this.getContext(),helena,omaha, Color.RED);
        helenaOmaha.setBackgroundColor(Color.TRANSPARENT);
        DrawView helenaDenver = new DrawView(this.getContext(),helena,denver, Color.GREEN);
        helenaDenver.setBackgroundColor(Color.TRANSPARENT);
        //DrawView denverOmaha = new DrawView(this.getContext(),denver,omaha, Color.PINK);
        //denverOmaha.setBackgroundColor(Color.TRANSPARENT);
        DrawView denverOklahoma = new DrawView(this.getContext(),denver,oklahoma, Color.RED);
        denverOklahoma.setBackgroundColor(Color.TRANSPARENT);
        DrawView denverSantaFe = new DrawView(this.getContext(),denver,santaFe, Color.GRAY);
        denverSantaFe.setBackgroundColor(Color.TRANSPARENT);
        DrawView santaFeOklahoma = new DrawView(this.getContext(),santaFe,oklahoma, Color.BLUE);
        santaFeOklahoma.setBackgroundColor(Color.TRANSPARENT);
        DrawView santaFeElPaso = new DrawView(this.getContext(),santaFe,elPaso, Color.GRAY);
        santaFeElPaso.setBackgroundColor(Color.TRANSPARENT);
        DrawView elPasoOklahoma = new DrawView(this.getContext(),elPaso,oklahoma, Color.YELLOW);
        elPasoOklahoma.setBackgroundColor(Color.TRANSPARENT);
        DrawView elPasoDallas = new DrawView(this.getContext(),elPaso,dallas, Color.RED);
        elPasoDallas.setBackgroundColor(Color.TRANSPARENT);
        DrawView elPasoHouston = new DrawView(this.getContext(),elPaso,houston, Color.GREEN);
        elPasoHouston.setBackgroundColor(Color.TRANSPARENT);

        relativeLayout.addView(vancouverSeattle,1500,800);
        relativeLayout.addView(seattlePortland,1500,800);
        relativeLayout.addView(vancouverCalgary,1500,800);
        relativeLayout.addView(calgaryWinnipeg,1500,800);
        relativeLayout.addView(seattleCalgary,1500,800);
        relativeLayout.addView(calgaryHelena,1500,800);
        relativeLayout.addView(portlandSaltLake,1500,800);
        relativeLayout.addView(portlandSanFran,1500,800);

        //TODO add onclick events for the lines, so, vancouverSeattle.addOnclick() blah blah
        //rootView = drawLines(rootView);
        getActivity().setTitle(title);
        return rootView;
    }

    public View drawLines(View rootView) {
        RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativeMap);

        ImageView vancouver = (ImageView) rootView.findViewById(R.id.vancouverCity);
        ImageView seattle = (ImageView) rootView.findViewById(R.id.seattleCity);

        DrawView vancouverSeattle = new DrawView(this.getContext(),vancouver,seattle, Color.GRAY);
        vancouverSeattle.setBackgroundColor(Color.YELLOW);

        relativeLayout.addView(vancouverSeattle);
        return rootView;
    }
}