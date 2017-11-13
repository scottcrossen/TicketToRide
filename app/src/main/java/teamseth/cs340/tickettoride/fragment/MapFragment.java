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

import teamseth.cs340.common.exceptions.ResourceNotFoundException;
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
    private Set<DrawView> allRoutes = new HashSet<>();

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
        vancouverSeattle = new DrawView(this.getContext(),vancouver,seattle, Color.GRAY,10,-10,10,-10);
        DrawView vancouverSeattle2 = new DrawView(this.getContext(),vancouver,seattle, Color.GREEN,0,10,0,10);
        vancouverSeattle.setDoubleRoute(true);
        vancouverSeattle2.setDoubleRoute(true);
        vancouverSeattle.setDoubleRouteName(vancouverSeattle2);
        vancouverSeattle2.setDoubleRouteName(vancouverSeattle);
        allRoutes.add(vancouverSeattle);
        allRoutes.add(vancouverSeattle2);
        DrawView seattlePortland = new DrawView(this.getContext(),seattle,portland, Color.GRAY);
        allRoutes.add(seattlePortland);
        DrawView vancouverCalgary = new DrawView(this.getContext(),vancouver,calgary, Color.GRAY);
        allRoutes.add(vancouverCalgary);
        DrawView calgaryWinnipeg = new DrawView(this.getContext(),calgary,winnipeg, Color.WHITE);
        allRoutes.add(calgaryWinnipeg);
        DrawView seattleCalgary = new DrawView(this.getContext(),seattle,calgary, Color.GRAY);
        allRoutes.add(seattleCalgary);
        DrawView calgaryHelena = new DrawView(this.getContext(),calgary,helena, Color.GRAY);
        allRoutes.add(calgaryHelena);
        DrawView portlandSaltLake = new DrawView(this.getContext(),portland,saltLake, Color.BLUE);
        allRoutes.add(portlandSaltLake);
        DrawView portlandSanFran = new DrawView(this.getContext(),portland,sanFrancisco, Color.GREEN);
        allRoutes.add(portlandSanFran);
        DrawView sanFranSaltLake = new DrawView(this.getContext(),sanFrancisco,saltLake, Color.WHITE);
        allRoutes.add(sanFranSaltLake);
        DrawView sanFranLosAngeles = new DrawView(this.getContext(),sanFrancisco,losAngeles, Color.YELLOW);
        allRoutes.add(sanFranLosAngeles);
        DrawView losAngelesLasVegas = new DrawView(this.getContext(),losAngeles,lasVegas, Color.GRAY);
        allRoutes.add(losAngelesLasVegas);
        DrawView lasVegasSaltLake = new DrawView(this.getContext(),lasVegas,saltLake, ORANGE);
        allRoutes.add(lasVegasSaltLake);
        DrawView losAngelesPhoenix = new DrawView(this.getContext(),losAngeles,phoenix, Color.GRAY);
        allRoutes.add(losAngelesPhoenix);
        DrawView losAngelesElPaso = new DrawView(this.getContext(),losAngeles,elPaso, Color.BLACK);
        allRoutes.add(losAngelesElPaso);
        DrawView phoenixDenver = new DrawView(this.getContext(),phoenix,denver, Color.WHITE);
        allRoutes.add(phoenixDenver);
        DrawView phoenixSantaFe = new DrawView(this.getContext(),phoenix,santaFe, Color.GRAY);
        allRoutes.add(phoenixSantaFe);
        DrawView phoenixElPaso = new DrawView(this.getContext(),phoenix,elPaso, Color.GRAY);
        allRoutes.add(phoenixElPaso);
        DrawView seattleHelena = new DrawView(this.getContext(),seattle,helena, Color.YELLOW);
        allRoutes.add(seattleHelena);
        DrawView saltLakeHelena = new DrawView(this.getContext(),saltLake,helena, PINK);
        allRoutes.add(saltLakeHelena);
        DrawView saltLakeDenver = new DrawView(this.getContext(),saltLake,denver, Color.RED);
        allRoutes.add(saltLakeDenver);
        DrawView helenaWinnipeg = new DrawView(this.getContext(),helena,winnipeg, Color.BLUE);
        allRoutes.add(helenaWinnipeg);
        DrawView helenaDuluth = new DrawView(this.getContext(),helena,duluth, ORANGE);
        allRoutes.add(helenaDuluth);
        DrawView helenaOmaha = new DrawView(this.getContext(),helena,omaha, Color.RED);
        allRoutes.add(helenaOmaha);
        DrawView helenaDenver = new DrawView(this.getContext(),helena,denver, Color.GREEN);
        allRoutes.add(helenaDenver);
        DrawView denverOmaha = new DrawView(this.getContext(),denver,omaha, PINK);
        allRoutes.add(denverOmaha);
        DrawView denverOklahoma = new DrawView(this.getContext(),denver,oklahoma, Color.RED);
        allRoutes.add(denverOklahoma);
        DrawView denverSantaFe = new DrawView(this.getContext(),denver,santaFe, Color.GRAY);
        allRoutes.add(denverSantaFe);
        DrawView santaFeOklahoma = new DrawView(this.getContext(),santaFe,oklahoma, Color.BLUE);
        allRoutes.add(santaFeOklahoma);
        DrawView santaFeElPaso = new DrawView(this.getContext(),santaFe,elPaso, Color.GRAY);
        allRoutes.add(santaFeElPaso);
        DrawView elPasoOklahoma = new DrawView(this.getContext(),elPaso,oklahoma, Color.YELLOW);
        allRoutes.add(elPasoOklahoma);
        DrawView elPasoDallas = new DrawView(this.getContext(),elPaso,dallas, Color.RED);
        allRoutes.add(elPasoDallas);
        DrawView elPasoHouston = new DrawView(this.getContext(),elPaso,houston, Color.GREEN);
        allRoutes.add(elPasoHouston);
        DrawView winnipegSaultStMarie = new DrawView(this.getContext(),winnipeg,saultStMarie, Color.GRAY);
        allRoutes.add(winnipegSaultStMarie);
        DrawView winnipegDuluth = new DrawView(this.getContext(),winnipeg,duluth, Color.BLACK);
        allRoutes.add(winnipegDuluth);
        DrawView duluthSaultStMarie = new DrawView(this.getContext(),duluth,saultStMarie, Color.GRAY);
        allRoutes.add(duluthSaultStMarie);
        DrawView duluthToronto = new DrawView(this.getContext(),duluth,toronto, PINK);
        allRoutes.add(duluthToronto);
        DrawView duluthChicago = new DrawView(this.getContext(),duluth,chicago, Color.RED);
        allRoutes.add(duluthChicago);
        DrawView duluthOmaha = new DrawView(this.getContext(),duluth,omaha, Color.GRAY);
        allRoutes.add(duluthOmaha);
        DrawView omahaChicago = new DrawView(this.getContext(),omaha,chicago, Color.BLUE);
        allRoutes.add(omahaChicago);
        DrawView omahaKansas = new DrawView(this.getContext(),omaha,kansas, Color.GRAY);
        allRoutes.add(omahaKansas);
        DrawView kansasStLouis = new DrawView(this.getContext(),kansas,saintLouis, Color.BLUE);
        allRoutes.add(kansasStLouis);
        DrawView kansasOklahoma = new DrawView(this.getContext(),kansas,oklahoma, Color.GRAY);
        allRoutes.add(kansasOklahoma);
        DrawView oklahomaLittleRock = new DrawView(this.getContext(),oklahoma,littleRock, Color.WHITE);
        allRoutes.add(oklahomaLittleRock);
        DrawView oklahomaDallas = new DrawView(this.getContext(),oklahoma,dallas, Color.GRAY);
        allRoutes.add(oklahomaDallas);
        DrawView dallasHouston = new DrawView(this.getContext(),dallas,houston, Color.GRAY);
        allRoutes.add(dallasHouston);
        DrawView dallasLittleRock = new DrawView(this.getContext(),dallas,littleRock, Color.GRAY);
        allRoutes.add(dallasLittleRock);
        DrawView houstonNewOrleans = new DrawView(this.getContext(),houston,newOrleans, Color.GRAY);
        allRoutes.add(houstonNewOrleans);
        DrawView littleRockNewOrleans = new DrawView(this.getContext(),littleRock,newOrleans, Color.GREEN);
        allRoutes.add(littleRockNewOrleans);
        DrawView newOrleansMiami = new DrawView(this.getContext(),newOrleans,miami, Color.RED);
        allRoutes.add(newOrleansMiami);
        DrawView newOrleansAtlanta = new DrawView(this.getContext(),newOrleans,atlanta, Color.YELLOW);
        allRoutes.add(newOrleansAtlanta);
        DrawView atlantaMiami = new DrawView(this.getContext(),atlanta,miami, Color.BLUE);
        allRoutes.add(atlantaMiami);
        DrawView atlantaCharleston = new DrawView(this.getContext(),atlanta,charleston, Color.GRAY);
        allRoutes.add(atlantaCharleston);
        DrawView charlestonMiami = new DrawView(this.getContext(),charleston,miami, PINK);
        allRoutes.add(charlestonMiami);
        DrawView littleRockNashville = new DrawView(this.getContext(),littleRock,nashville, Color.WHITE);
        allRoutes.add(littleRockNashville);
        DrawView saintLouisLittleRock = new DrawView(this.getContext(),saintLouis,littleRock, Color.GRAY);
        allRoutes.add(saintLouisLittleRock);
        DrawView saintLouisNashville = new DrawView(this.getContext(),saintLouis,nashville, Color.GRAY);
        allRoutes.add(saintLouisNashville);
        DrawView nashvilleAtlanta = new DrawView(this.getContext(),nashville,atlanta, Color.GRAY);
        allRoutes.add(nashvilleAtlanta);
        DrawView atlantaRaleigh = new DrawView(this.getContext(),atlanta,raleigh, Color.GRAY);
        allRoutes.add(atlantaRaleigh);
        DrawView raleighCharleston = new DrawView(this.getContext(),raleigh,charleston, Color.GRAY);
        allRoutes.add(raleighCharleston);
        DrawView nashvilleRaleigh = new DrawView(this.getContext(),nashville,raleigh, Color.BLACK);
        allRoutes.add(nashvilleRaleigh);
        DrawView pittsburghNashville = new DrawView(this.getContext(),pittsburgh,nashville, Color.YELLOW);
        allRoutes.add(pittsburghNashville);
        DrawView saintLouisPittsburgh = new DrawView(this.getContext(),saintLouis,pittsburgh, Color.GREEN);
        allRoutes.add(saintLouisPittsburgh);
        DrawView chicagoSaintLouis = new DrawView(this.getContext(),chicago,saintLouis, Color.GREEN);
        allRoutes.add(chicagoSaintLouis);
        DrawView chicagoPittsburgh = new DrawView(this.getContext(),chicago,pittsburgh, Color.BLACK);
        allRoutes.add(chicagoPittsburgh);
        DrawView chicagoToronto = new DrawView(this.getContext(),chicago,toronto, Color.WHITE);
        allRoutes.add(chicagoToronto);
        DrawView saultStMarieToronto = new DrawView(this.getContext(),saultStMarie,toronto, Color.GRAY);
        allRoutes.add(saultStMarieToronto);
        DrawView torontoPittsburgh = new DrawView(this.getContext(),toronto,pittsburgh, Color.GRAY);
        allRoutes.add(torontoPittsburgh);
        DrawView torontoMontreal = new DrawView(this.getContext(),toronto,montreal, Color.GRAY);
        allRoutes.add(torontoMontreal);
        DrawView saultStMarieMontreal = new DrawView(this.getContext(),elPaso,oklahoma, Color.BLACK);
        allRoutes.add(saultStMarieMontreal);
        DrawView montrealBoston = new DrawView(this.getContext(),montreal,boston, Color.GRAY);
        allRoutes.add(montrealBoston);
        DrawView montrealNewYork = new DrawView(this.getContext(),montreal,newYork, Color.BLUE);
        allRoutes.add(montrealNewYork);
        DrawView bostonNewYork = new DrawView(this.getContext(),boston,newYork, Color.YELLOW);
        allRoutes.add(bostonNewYork);
        DrawView pittsburghNewYork = new DrawView(this.getContext(),pittsburgh,newYork, Color.GREEN);
        allRoutes.add(pittsburghNewYork);
        DrawView newYorkDC = new DrawView(this.getContext(),newYork,dc, Color.BLACK);
        allRoutes.add(newYorkDC);
        DrawView pittsburghDC = new DrawView(this.getContext(),pittsburgh,dc, Color.GRAY);
        allRoutes.add(pittsburghDC);
        DrawView pittsburghRaleigh = new DrawView(this.getContext(),pittsburgh,raleigh, Color.GRAY);
        allRoutes.add(pittsburghRaleigh);
        DrawView dcRaleigh = new DrawView(this.getContext(),dc,raleigh, Color.GRAY);
        allRoutes.add(dcRaleigh);

        //TODO add double routes using overloaded DrawView function

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
            } catch (ResourceNotFoundException e) {
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
        drawTheRoutes();
    }

    private void drawTheRoutes() {
        for(DrawView imRoute : allRoutes) {
            imRoute.setBackgroundColor(Color.TRANSPARENT);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(imRoute.getWidth(),
                    imRoute.getHeight());
            imRoute.setLayoutParams(params);
            relativeLayout.addView(imRoute,2500,1800);
//            imRoute.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                        Toast.makeText(getContext(), "Clicked on Route", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//            });
        }
    }
}