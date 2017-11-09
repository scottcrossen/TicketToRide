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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.common.models.server.cards.CityName;
import teamseth.cs340.common.models.server.games.PlayerColor;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.util.DrawView;

/**
 * Created by Seth on 10/14/2017.
 */

public class MapFragment extends Fragment {
    public static final String ARG_TAB_NUMBER = "tab_number";
    static final int PINK = Color.rgb(255,182,193);
    static final int ORANGE = Color.rgb(255,140,0);
    private ImageView vancouver;
    private ImageView seattle;
    private ImageView portland;
    private ImageView sanFrancisco;
    private ImageView losAngeles;
    private ImageView phoenix;
    private ImageView lasVegas;
    private ImageView elPaso;
    private ImageView santaFe;
    private ImageView saltLake;
    private ImageView calgary;
    private ImageView helena;
    private ImageView denver;
    private ImageView winnipeg;
    private ImageView duluth;
    private ImageView omaha;
    private ImageView oklahoma;
    private ImageView kansas;
    private ImageView dallas;
    private ImageView houston;
    private ImageView newOrleans;
    private ImageView littleRock;
    private ImageView saintLouis;
    private ImageView chicago;
    private ImageView nashville;
    private ImageView miami;
    private ImageView atlanta;
    private ImageView charleston;
    private ImageView raleigh;
    private ImageView dc;
    private ImageView pittsburgh;
    private ImageView newYork;
    private ImageView boston;
    private ImageView toronto;
    private ImageView saultStMarie;
    private ImageView montreal;
    private RelativeLayout relativeLayout;
    private DrawView vancouverSeattle;

    public MapFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(this.getContext(), "Map", Toast.LENGTH_SHORT).show();
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        disableHardwareRendering(rootView);
        int i = getArguments().getInt(ARG_TAB_NUMBER);
        String title = getResources().getStringArray(R.array.tabs_array)[i];
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativeMap);

        vancouver = (ImageView) rootView.findViewById(R.id.vancouverCity);
        seattle = (ImageView) rootView.findViewById(R.id.seattleCity);
        portland = (ImageView) rootView.findViewById(R.id.portlandCity);
        sanFrancisco = (ImageView) rootView.findViewById(R.id.sanFranciscoCity);
        losAngeles = (ImageView) rootView.findViewById(R.id.losAngelesCity);
        phoenix = (ImageView) rootView.findViewById(R.id.phoenixCity);
        lasVegas = (ImageView) rootView.findViewById(R.id.lasVegasCity);
        elPaso = (ImageView) rootView.findViewById(R.id.elPasoCity);
        santaFe = (ImageView) rootView.findViewById(R.id.santFeCity);
        saltLake = (ImageView) rootView.findViewById(R.id.saltLakeCity);
        calgary = (ImageView) rootView.findViewById(R.id.calgaryCity);
        helena = (ImageView) rootView.findViewById(R.id.helenaCity);
        denver = (ImageView) rootView.findViewById(R.id.denverCity);
        winnipeg = (ImageView) rootView.findViewById(R.id.winnipegCity);
        duluth = (ImageView) rootView.findViewById(R.id.duluthCity);
        omaha = (ImageView) rootView.findViewById(R.id.omahaCity);
        oklahoma = (ImageView) rootView.findViewById(R.id.oklahomaCity);
        kansas = (ImageView) rootView.findViewById(R.id.kansasCity);
        dallas = (ImageView) rootView.findViewById(R.id.dallasCity);
        houston = (ImageView) rootView.findViewById(R.id.houstonCity);
        newOrleans = (ImageView) rootView.findViewById(R.id.newOrleansCity);
        littleRock = (ImageView) rootView.findViewById(R.id.littleRockCity);
        saintLouis = (ImageView) rootView.findViewById(R.id.saintLouisCity);
        chicago = (ImageView) rootView.findViewById(R.id.chicagoCity);
        nashville = (ImageView) rootView.findViewById(R.id.nashvilleCity);
        miami = (ImageView) rootView.findViewById(R.id.miamiCity);
        atlanta = (ImageView) rootView.findViewById(R.id.atlantaCity);
        charleston = (ImageView) rootView.findViewById(R.id.charlestonCity);
        raleigh = (ImageView) rootView.findViewById(R.id.raleighCity);
        dc = (ImageView) rootView.findViewById(R.id.dcCity);
        pittsburgh = (ImageView) rootView.findViewById(R.id.pittsburghCity);
        newYork = (ImageView) rootView.findViewById(R.id.newYorkCity);
        boston = (ImageView) rootView.findViewById(R.id.bostonCity);
        toronto = (ImageView) rootView.findViewById(R.id.torontoCity);
        saultStMarie = (ImageView) rootView.findViewById(R.id.saultStMarieCity);
        montreal = (ImageView) rootView.findViewById(R.id.montrealCity);

        // TODO potentially add in the x and y offsets so the lines look cleaner, not as necessary though
        vancouverSeattle = new DrawView(this.getContext(),vancouver,seattle, Color.GRAY);
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
        DrawView lasVegasSaltLake = new DrawView(this.getContext(),lasVegas,saltLake, ORANGE);
        lasVegasSaltLake.setBackgroundColor(Color.TRANSPARENT);
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
        DrawView saltLakeHelena = new DrawView(this.getContext(),saltLake,helena, PINK);
        saltLakeHelena.setBackgroundColor(Color.TRANSPARENT);
        DrawView saltLakeDenver = new DrawView(this.getContext(),saltLake,denver, Color.RED);
        saltLakeDenver.setBackgroundColor(Color.TRANSPARENT);
        DrawView helenaWinnipeg = new DrawView(this.getContext(),helena,winnipeg, Color.BLUE);
        helenaWinnipeg.setBackgroundColor(Color.TRANSPARENT);
        DrawView helenaDuluth = new DrawView(this.getContext(),helena,duluth, ORANGE);
        helenaDuluth.setBackgroundColor(Color.TRANSPARENT);
        DrawView helenaOmaha = new DrawView(this.getContext(),helena,omaha, Color.RED);
        helenaOmaha.setBackgroundColor(Color.TRANSPARENT);
        DrawView helenaDenver = new DrawView(this.getContext(),helena,denver, Color.GREEN);
        helenaDenver.setBackgroundColor(Color.TRANSPARENT);
        DrawView denverOmaha = new DrawView(this.getContext(),denver,omaha, PINK);
        denverOmaha.setBackgroundColor(Color.TRANSPARENT);
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
        DrawView winnipegSaultStMarie = new DrawView(this.getContext(),winnipeg,saultStMarie, Color.GRAY);
        winnipegSaultStMarie.setBackgroundColor(Color.TRANSPARENT);
        DrawView winnipegDuluth = new DrawView(this.getContext(),winnipeg,duluth, Color.BLACK);
        winnipegDuluth.setBackgroundColor(Color.TRANSPARENT);
        DrawView duluthSaultStMarie = new DrawView(this.getContext(),duluth,saultStMarie, Color.GRAY);
        duluthSaultStMarie.setBackgroundColor(Color.TRANSPARENT);
        DrawView duluthToronto = new DrawView(this.getContext(),duluth,toronto, PINK);
        duluthToronto.setBackgroundColor(Color.TRANSPARENT);
        DrawView duluthChicago = new DrawView(this.getContext(),duluth,chicago, Color.RED);
        duluthChicago.setBackgroundColor(Color.TRANSPARENT);
        DrawView duluthOmaha = new DrawView(this.getContext(),duluth,omaha, Color.GRAY);
        duluthOmaha.setBackgroundColor(Color.TRANSPARENT);
        DrawView omahaChicago = new DrawView(this.getContext(),omaha,chicago, Color.BLUE);
        omahaChicago.setBackgroundColor(Color.TRANSPARENT);
        DrawView omahaKansas = new DrawView(this.getContext(),omaha,kansas, Color.GRAY);
        omahaKansas.setBackgroundColor(Color.TRANSPARENT);
        DrawView kansasStLouis = new DrawView(this.getContext(),kansas,saintLouis, Color.BLUE);
        kansasStLouis.setBackgroundColor(Color.TRANSPARENT);
        DrawView kansasOklahoma = new DrawView(this.getContext(),kansas,oklahoma, Color.GRAY);
        kansasOklahoma.setBackgroundColor(Color.TRANSPARENT);
        DrawView oklahomaLittleRock = new DrawView(this.getContext(),oklahoma,littleRock, Color.WHITE);
        oklahomaLittleRock.setBackgroundColor(Color.TRANSPARENT);
        DrawView oklahomaDallas = new DrawView(this.getContext(),oklahoma,dallas, Color.GRAY);
        oklahomaDallas.setBackgroundColor(Color.TRANSPARENT);
        DrawView dallasHouston = new DrawView(this.getContext(),dallas,houston, Color.GRAY);
        dallasHouston.setBackgroundColor(Color.TRANSPARENT);
        DrawView dallasLittleRock = new DrawView(this.getContext(),dallas,littleRock, Color.GRAY);
        dallasLittleRock.setBackgroundColor(Color.TRANSPARENT);
        DrawView houstonNewOrleans = new DrawView(this.getContext(),houston,newOrleans, Color.GRAY);
        houstonNewOrleans.setBackgroundColor(Color.TRANSPARENT);
        DrawView littleRockNewOrleans = new DrawView(this.getContext(),littleRock,newOrleans, Color.GREEN);
        littleRockNewOrleans.setBackgroundColor(Color.TRANSPARENT);
        DrawView newOrleansMiami = new DrawView(this.getContext(),newOrleans,miami, Color.RED);
        newOrleansMiami.setBackgroundColor(Color.TRANSPARENT);
        DrawView newOrleansAtlanta = new DrawView(this.getContext(),newOrleans,atlanta, Color.YELLOW);
        newOrleansAtlanta.setBackgroundColor(Color.TRANSPARENT);
        DrawView atlantaMiami = new DrawView(this.getContext(),atlanta,miami, Color.BLUE);
        atlantaMiami.setBackgroundColor(Color.TRANSPARENT);
        DrawView atlantaCharleston = new DrawView(this.getContext(),atlanta,charleston, Color.GRAY);
        atlantaCharleston.setBackgroundColor(Color.TRANSPARENT);
        DrawView charlestonMiami = new DrawView(this.getContext(),charleston,miami, PINK);
        charlestonMiami.setBackgroundColor(Color.TRANSPARENT);
        DrawView littleRockNashville = new DrawView(this.getContext(),littleRock,nashville, Color.WHITE);
        littleRockNashville.setBackgroundColor(Color.TRANSPARENT);
        DrawView saintLouisLittleRock = new DrawView(this.getContext(),saintLouis,littleRock, Color.GRAY);
        saintLouisLittleRock.setBackgroundColor(Color.TRANSPARENT);
        DrawView saintLouisNashville = new DrawView(this.getContext(),saintLouis,nashville, Color.GRAY);
        saintLouisNashville.setBackgroundColor(Color.TRANSPARENT);
        DrawView nashvilleAtlanta = new DrawView(this.getContext(),nashville,atlanta, Color.GRAY);
        nashvilleAtlanta.setBackgroundColor(Color.TRANSPARENT);
        DrawView atlantaRaleigh = new DrawView(this.getContext(),atlanta,raleigh, Color.GRAY);
        atlantaRaleigh.setBackgroundColor(Color.TRANSPARENT);
        DrawView raleighCharleston = new DrawView(this.getContext(),raleigh,charleston, Color.GRAY);
        raleighCharleston.setBackgroundColor(Color.TRANSPARENT);
        DrawView nashvilleRaleigh = new DrawView(this.getContext(),nashville,raleigh, Color.BLACK);
        nashvilleRaleigh.setBackgroundColor(Color.TRANSPARENT);
        DrawView pittsburghNashville = new DrawView(this.getContext(),pittsburgh,nashville, Color.YELLOW);
        pittsburghNashville.setBackgroundColor(Color.TRANSPARENT);
        DrawView saintLouisPittsburgh = new DrawView(this.getContext(),saintLouis,pittsburgh, Color.GREEN);
        saintLouisPittsburgh.setBackgroundColor(Color.TRANSPARENT);
        DrawView chicagoSaintLouis = new DrawView(this.getContext(),chicago,saintLouis, Color.GREEN);
        chicagoSaintLouis.setBackgroundColor(Color.TRANSPARENT);
        DrawView chicagoPittsburgh = new DrawView(this.getContext(),chicago,pittsburgh, Color.BLACK);
        chicagoPittsburgh.setBackgroundColor(Color.TRANSPARENT);
        DrawView chicagoToronto = new DrawView(this.getContext(),chicago,toronto, Color.WHITE);
        chicagoToronto.setBackgroundColor(Color.TRANSPARENT);
        DrawView saultStMarieToronto = new DrawView(this.getContext(),saultStMarie,toronto, Color.GRAY);
        saultStMarieToronto.setBackgroundColor(Color.TRANSPARENT);
        DrawView torontoPittsburgh = new DrawView(this.getContext(),toronto,pittsburgh, Color.GRAY);
        torontoPittsburgh.setBackgroundColor(Color.TRANSPARENT);
        DrawView torontoMontreal = new DrawView(this.getContext(),toronto,montreal, Color.GRAY);
        torontoMontreal.setBackgroundColor(Color.TRANSPARENT);
        DrawView saultStMarieMontreal = new DrawView(this.getContext(),elPaso,oklahoma, Color.BLACK);
        saultStMarieMontreal.setBackgroundColor(Color.TRANSPARENT);
        DrawView montrealBoston = new DrawView(this.getContext(),montreal,boston, Color.GRAY);
        montrealBoston.setBackgroundColor(Color.TRANSPARENT);
        DrawView montrealNewYork = new DrawView(this.getContext(),montreal,newYork, Color.BLUE);
        montrealNewYork.setBackgroundColor(Color.TRANSPARENT);
        DrawView bostonNewYork = new DrawView(this.getContext(),boston,newYork, Color.YELLOW);
        bostonNewYork.setBackgroundColor(Color.TRANSPARENT);
        DrawView pittsburghNewYork = new DrawView(this.getContext(),pittsburgh,newYork, Color.GREEN);
        pittsburghNewYork.setBackgroundColor(Color.TRANSPARENT);
        DrawView newYorkDC = new DrawView(this.getContext(),newYork,dc, Color.BLACK);
        newYorkDC.setBackgroundColor(Color.TRANSPARENT);
        DrawView pittsburghDC = new DrawView(this.getContext(),pittsburgh,dc, Color.GRAY);
        pittsburghDC.setBackgroundColor(Color.TRANSPARENT);
        DrawView pittsburghRaleigh = new DrawView(this.getContext(),pittsburgh,raleigh, Color.GRAY);
        pittsburghRaleigh.setBackgroundColor(Color.TRANSPARENT);
        DrawView dcRaleigh = new DrawView(this.getContext(),dc,raleigh, Color.GRAY);
        dcRaleigh.setBackgroundColor(Color.TRANSPARENT);

        relativeLayout.addView(atlantaCharleston,2500,1800);
        relativeLayout.addView(atlantaMiami,2500,1800);
        relativeLayout.addView(atlantaRaleigh,2500,1800);
        relativeLayout.addView(bostonNewYork,2500,1800);
        relativeLayout.addView(calgaryHelena,2500,1800);
        relativeLayout.addView(calgaryWinnipeg,2500,1800);
        relativeLayout.addView(charlestonMiami,2500,1800);
        relativeLayout.addView(chicagoPittsburgh,2500,1800);
        relativeLayout.addView(chicagoSaintLouis,2500,1800);
        relativeLayout.addView(chicagoToronto,2500,1800);
        relativeLayout.addView(dallasHouston,2500,1800);
        relativeLayout.addView(dallasLittleRock,2500,1800);
        relativeLayout.addView(dcRaleigh,2500,1800);
        relativeLayout.addView(denverOklahoma,2500,1800);
        relativeLayout.addView(denverOmaha,2500,1800);
        relativeLayout.addView(denverSantaFe,2500,1800);
        relativeLayout.addView(duluthChicago,2500,1800);
        relativeLayout.addView(duluthOmaha,2500,1800);
        relativeLayout.addView(duluthSaultStMarie,2500,1800);
        relativeLayout.addView(duluthToronto,2500,1800);
        relativeLayout.addView(elPasoDallas,2500,1800);
        relativeLayout.addView(elPasoHouston,2500,1800);
        relativeLayout.addView(elPasoOklahoma,2500,1800);
        relativeLayout.addView(helenaDenver,2500,1800);
        relativeLayout.addView(helenaDuluth,2500,1800);
        relativeLayout.addView(helenaOmaha,2500,1800);
        relativeLayout.addView(helenaWinnipeg,2500,1800);
        relativeLayout.addView(houstonNewOrleans,2500,1800);
        relativeLayout.addView(kansasOklahoma,2500,1800);
        relativeLayout.addView(kansasStLouis,2500,1800);
        relativeLayout.addView(lasVegasSaltLake,2500,1800);
        relativeLayout.addView(littleRockNashville,2500,1800);
        relativeLayout.addView(littleRockNewOrleans,2500,1800);
        relativeLayout.addView(losAngelesElPaso,2500,1800);
        relativeLayout.addView(losAngelesLasVegas,2500,1800);
        relativeLayout.addView(losAngelesPhoenix,2500,1800);
        relativeLayout.addView(montrealBoston,2500,1800);
        relativeLayout.addView(montrealNewYork,2500,1800);
        relativeLayout.addView(nashvilleAtlanta,2500,1800);
        relativeLayout.addView(nashvilleRaleigh,2500,1800);
        relativeLayout.addView(newOrleansAtlanta,2500,1800);
        relativeLayout.addView(newOrleansMiami,2500,1800);
        relativeLayout.addView(newYorkDC,2500,1800);
        relativeLayout.addView(oklahomaDallas,2500,1800);
        relativeLayout.addView(oklahomaLittleRock,2500,1800);
        relativeLayout.addView(omahaChicago,2500,1800);
        relativeLayout.addView(omahaKansas,2500,1800);
        relativeLayout.addView(phoenixDenver,2500,1800);
        relativeLayout.addView(phoenixElPaso,2500,1800);
        relativeLayout.addView(phoenixSantaFe,2500,1800);
        relativeLayout.addView(pittsburghDC,2500,1800);
        relativeLayout.addView(pittsburghNashville,2500,1800);
        relativeLayout.addView(pittsburghNewYork,2500,1800);
        relativeLayout.addView(pittsburghRaleigh,2500,1800);
        relativeLayout.addView(portlandSaltLake,2500,1800);
        relativeLayout.addView(portlandSanFran,2500,1800);
        relativeLayout.addView(raleighCharleston,2500,1800);
        relativeLayout.addView(saintLouisLittleRock,2500,1800);
        relativeLayout.addView(saintLouisNashville,2500,1800);
        relativeLayout.addView(saintLouisPittsburgh,2500,1800);
        relativeLayout.addView(saltLakeDenver,2500,1800);
        relativeLayout.addView(saltLakeHelena,2500,1800);
        relativeLayout.addView(sanFranLosAngeles,2500,1800);
        relativeLayout.addView(sanFranSaltLake,2500,1800);
        relativeLayout.addView(santaFeElPaso,2500,1800);
        relativeLayout.addView(santaFeOklahoma,2500,1800);
        relativeLayout.addView(saultStMarieMontreal,2500,1800);
        relativeLayout.addView(saultStMarieToronto,2500,1800);
        relativeLayout.addView(seattleCalgary,2500,1800);
        relativeLayout.addView(seattleHelena,2500,1800);
        relativeLayout.addView(seattlePortland,2500,1800);
        relativeLayout.addView(torontoMontreal,2500,1800);
        relativeLayout.addView(torontoPittsburgh,2500,1800);
        relativeLayout.addView(vancouverCalgary,2500,1800);
        relativeLayout.addView(vancouverSeattle,2500,1800);
        relativeLayout.addView(winnipegDuluth,2500,1800);
        relativeLayout.addView(winnipegSaultStMarie,2500,1800);

        //TODO phase 3 add onclick events for the lines, so, vancouverSeattle.addOnclick() blah blah
        //rootView = drawLines(rootView);
        getActivity().setTitle(title);

        allClaimedRoutes = new HashSet<Route>();
        update();
        return rootView;
    }

    private void claimRoute(Route route) {
        CityName city1 = route.getCity1();
        CityName city2 = route.getCity2();
        route.getClaimedPlayer().map((UUID playerId) -> {
            try {
                PlayerColor color = ClientModelRoot.getInstance().games.getActive().getPlayerColors().get(playerId);
                ImageView startCity = convertCityNametoImageView(city1);
                ImageView endCity = convertCityNametoImageView(city2);
                int routeColor = convertColorFromEnum(color);
                relativeLayout.removeView(vancouverSeattle);
                vancouverSeattle = new DrawView(this.getContext(), startCity, endCity, routeColor);
                vancouverSeattle.isOwned(true);
                vancouverSeattle.setBackgroundColor(Color.TRANSPARENT);
                relativeLayout.addView(vancouverSeattle, 2500, 1800);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        });

        // TODO: Seth: implement this
    }

    private int convertColorFromEnum(PlayerColor color) {
        switch (color) {
            case GREEN:
                return Color.GREEN;
            case RED:
                return Color.RED;
            case BLACK:
                return Color.BLACK;
            case BLUE:
                return Color.BLUE;
            case YELLOW:
                return Color.YELLOW;
        }
        return Color.BLUE;
    }

    private ImageView convertCityNametoImageView(CityName cityName) {
        switch (cityName) {
            case Vancouver:
                return vancouver;
            case Seattle:
                return seattle;
            case Portland:
                return portland;
            case SanFrancisco:
                return sanFrancisco;
            case LosAngeles:
                return losAngeles;
            case Phoenix:
                return phoenix;
            case LasVegas:
                return lasVegas;
            case ElPaso:
                return elPaso;
            case SantaFe:
                return santaFe;
            case SaltLakeCity:
                return saltLake;
            case Calgary:
                return calgary;
            case Helena:
                return helena;
            case Denver:
                return denver;
            case Winnipeg:
                return winnipeg;
            case Duluth:
                return duluth;
            case Omaha:
                return omaha;
            case OklahomaCity:
                return oklahoma;
            case KansasCity:
                return kansas;
            case Dallas:
                return dallas;
            case Houston:
                return houston;
            case NewOrleans:
                return newOrleans;
            case LittleRock:
                return littleRock;
            case SaintLouis:
                return saintLouis;
            case Chicago:
                return chicago;
            case Nashville:
                return nashville;
            case Miami:
                return miami;
            case Atlanta:
                return atlanta;
            case Charleston:
                return charleston;
            case Raleigh:
                return raleigh;
            case DC:
                return dc;
            case Pittsburgh:
                return pittsburgh;
            case NewYork:
                return newYork;
            case Boston:
                return boston;
            case Toronto:
                return toronto;
            case StMarie:
                return saultStMarie;
            case Montreal:
                return montreal;
        }
        return seattle;
    }

    public static void disableHardwareRendering(View v) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    private Set<Route> allClaimedRoutes = new HashSet<Route>();
    public void update() {
        Set<Route> newRoutes = ClientModelRoot.board.getAllClaimedRoutes().stream().filter((Route route) -> {
            boolean alreadyExists = allClaimedRoutes.stream().filter((Route drawn) -> {
                return route.compareCitiesAndColor(drawn) && route.getClaimedPlayer().equals(drawn.getClaimedPlayer());
            }).count() > 0;
            return !alreadyExists;
        }).collect(Collectors.toSet());
        newRoutes.forEach((Route route) -> claimRoute(route));
        allClaimedRoutes.addAll(newRoutes);
    }
}