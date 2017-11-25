package teamseth.cs340.tickettoride.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.common.models.server.cards.CityName;
import teamseth.cs340.common.models.server.games.PlayerColor;
import teamseth.cs340.common.util.client.Login;
import teamseth.cs340.tickettoride.R;
import teamseth.cs340.tickettoride.util.DrawView;
import teamseth.cs340.tickettoride.util.MapView;

/**
 * Created by Seth on 10/14/2017.
 */

public class MapFragment extends Fragment implements IUpdatableFragment {
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
    private MapView mapView;

    private DrawView vancouverSeattle;
    private Set<DrawView> allRoutes = new HashSet<>();
    private Set<Route> allTheGameRoutes = new HashSet<>();
    private Set<Route> allClaimedRoutes = new HashSet<Route>();

    public MapFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        disableHardwareRendering(rootView);
        int i = getArguments().getInt(ARG_TAB_NUMBER);
        String title = getResources().getStringArray(R.array.tabs_array)[i];
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativeMap);
        mapView = new MapView(getContext());
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

        PlayerColor colo = PlayerColor.GREEN;
        try {
            colo = ClientModelRoot.getInstance().games.getActive().getPlayerColors().get(Login.getUserId());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

        rootView.setBackgroundColor(convertPlayerColorFromEnum(colo));

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
                removeRouteFromView(route);
                DrawView claimedRoute = new DrawView(this.getContext(), route, startCity, endCity);
                claimedRoute.isOwned(true);
                route.setOwned(true);
                claimedRoute.setBackgroundColor(Color.TRANSPARENT);
                allRoutes.add(claimedRoute);
                relativeLayout.addView(claimedRoute, 2500, 1800);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        });
    }

    private void removeRouteFromView(Route route) {
        for (DrawView dr : allRoutes) {
            if((dr.getRoute().equals(route))) {
                relativeLayout.removeView(dr);
                allRoutes.remove(dr);
            }
        }
    }

    public static void disableHardwareRendering(View v) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    private int convertPlayerColorFromEnum(PlayerColor color) {
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

    public void update() {
        Set<Route> newRoutes = ClientModelRoot.board.getAllClaimedRoutes().stream().filter((Route route) -> {
            boolean alreadyExists = allClaimedRoutes.stream().filter((Route drawn) -> {
                return route.compareCitiesAndColor(drawn) && route.getClaimedPlayer().equals(drawn.getClaimedPlayer());
            }).count() > 0;
            return !alreadyExists;
        }).collect(Collectors.toSet());
        newRoutes.forEach((Route route) -> claimRoute(route));
        allClaimedRoutes.addAll(newRoutes);

        allTheGameRoutes = ClientModelRoot.board.getAllRoutes();

        allRoutes.clear();
        for(Route rt : allTheGameRoutes) {
            DrawView dr = new DrawView(this.getContext(), rt, convertCityNametoImageView(rt.getCity1()),
                    convertCityNametoImageView(rt.getCity2()));
            allRoutes.add(dr);
        }

        /*for(Route rt : allClaimedRoutes) {
            DrawView dr = new DrawView(this.getContext(), rt, convertCityNametoImageView(rt.getCity1()),
                    convertCityNametoImageView(rt.getCity2()));

            allRoutes.add(dr);
        }*/
        drawTheRoutes();
    }

    private void drawTheRoutes() {
        relativeLayout.removeView(mapView);
        mapView.removeAllRoutes();
        for (DrawView imRoute : allRoutes) {
            imRoute.setBackgroundColor(Color.TRANSPARENT);
            mapView.addRouteToMap(imRoute);
        }
        relativeLayout.addView(mapView, 2500, 1800);
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

}