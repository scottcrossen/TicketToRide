package Model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Seth on 9/29/2017.
 */
public class GameModel {
    public static final GameModel SINGLETON = new GameModel();

    public static void start(MainActivity ma) {
        mainActivity = ma;
        currentPerson = new Person();
        people = new HashMap<>();
        malePeople = new HashMap<>();
        femalePeople = new HashMap<>();
        maleDadPeople = new HashMap<>();
        femaleDadPeople = new HashMap<>();
        maleMomPeople = new HashMap<>();
        femaleMomPeople = new HashMap<>();
        userEvents = new HashMap<>();
        events = new HashMap<>();
        eventMap = new HashMap<>();
        markers = new HashMap<>();
        filters = new Filters();
        settings = new Settings();
        polylines = new HashSet<>();
    }

    //Unit test doesn't have the Color.parseColor(), so doesn't initialize settings to bypass that
    public static void startTest(MainActivity ma) {
        mainActivity = ma;
        currentPerson = new Person();
        people = new HashMap<>();
        malePeople = new HashMap<>();
        femalePeople = new HashMap<>();
        maleDadPeople = new HashMap<>();
        femaleDadPeople = new HashMap<>();
        maleMomPeople = new HashMap<>();
        femaleMomPeople = new HashMap<>();
        userEvents = new HashMap<>();
        events = new HashMap<>();
        eventMap = new HashMap<>();
        markers = new HashMap<>();
        filters = new Filters();
        polylines = new HashSet<>();
    }

    public ModelApp() {
    }

    public static Set<Polyline> polylines;

    private static MainActivity mainActivity;
    public static HashMap<String, Event> eventMap;
    private String currId;
    private String userNameCurr;
    public static Person currentPerson;
    private static Map<String, Person> people;
    private static Map<String, Set<Event>> maleMomPeople;
    private static Map<String, Set<Event>> femaleMomPeople;
    private static Map<String, Set<Event>> maleDadPeople;
    private static Map<String, Set<Event>> femaleDadPeople;
    private static Map<String, Set<Event>> malePeople;
    private static Map<String, Set<Event>> femalePeople;
    private static Map<String, Event> userEvents;
    //example Map<male, Map<Death, Set<eventsofDeath>>>
    private static Map<String, Event> events;
    private static Filters filters;
    private static Person focusedPerson;
    private static Boolean loggedIn;
    private static String iconMapPart;
    private static Settings settings;
    private static Event focusEvent;
    public static Map<Marker, Event> markers;

    public String getUserNameCurr() {
        return userNameCurr;
    }

    public void setUserNameCurr(String userNameCurr) {
        this.userNameCurr = userNameCurr;
    }

    public static Map<Marker, Event> getMarkers() {
        return markers;
    }

    public static void setAllMarkers(Map<Marker, Event> markers) {
        ModelApp.markers = markers;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public static Map<String, Person> getPeople() {
        return people;
    }

    public static Person getCurrentUser() {
        return currentPerson;
    }

    public static void setCurrentUser(Person currentPerson) {
        ModelApp.currentPerson = currentPerson;
    }

    public static Map<String, Event> getEvents() {
        return events;
    }

    public static Event getEventByID(String id) {
        try {
            Event e = events.get(id);
            return e;
        } catch (Exception e) {
            return null;
        }
    }

    public static Filters getFilters() {
        return filters;
    }

    public static Settings getSettings() {
        return settings;
    }

    public static Person getFocusedPerson() {
        return focusedPerson;
    }

    public static void setFocusedPerson(Person focusedPerson) {
        ModelApp.focusedPerson = focusedPerson;
    }

    public void addPerson(String personID, Person person) {
        people.put(personID, person);
    }

    public static Person getPerson(String personID) {
        return people.get(personID);
    }

    public void setCurrId(String id) {
        this.currId = id;
    }

    public String getCurrId() {
        return currId;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public static void addEvent(Event event) {
        String personID = event.getPerson();
        Map<String, Person> people = ModelApp.SINGLETON.getPeople();
        Person p = getPerson(personID);
        String side = p.getMfSide();
        if (side == null) {
            p.setMfSide("neither");
        }
        p.setEvents(event);
        ModelApp.SINGLETON.events.put(event.getEventID(), event);
        if (!eventMap.containsKey(event.getEventType().toLowerCase())) {
            Map<String, Boolean> filt = ModelApp.SINGLETON.getFilters().getDynamicFilters();
            filt.put(event.getEventType().toLowerCase(), true);
            ModelApp.SINGLETON.getFilters().setDynamicFilters(filt);
        }
        eventMap.put(event.getEventType().toLowerCase(), event);
    }

    public static Map<String, Event> getUserEvents() {
        return userEvents;
    }

    public static String getIconMapPart() {
        return iconMapPart;
    }

    public static void setIconMapPart(String iconMapPart) {
        //ie n normal, m male, f female
        ModelApp.iconMapPart = iconMapPart;
    }

    public static void removePolyLines() {
        for (Polyline p : ModelApp.SINGLETON.polylines) {
            p.remove();
        }
        ModelApp.SINGLETON.polylines.clear();
    }

    public static void setMotherFatherSides() {
        filters.setMarkerColors();
    }

    public static void createFamilyTree() {
        Map<String, Person> people = ModelApp.SINGLETON.getPeople();
        Person mainUser = ModelApp.SINGLETON.getCurrentUser();
        mainUser = ModelApp.SINGLETON.getPerson(mainUser.getPersonID());
        if (mainUser.getMother() != null) {
            Person mother = people.get(mainUser.getMother());
            mother.setMfSide("mom");
            mother.getChildren().put(mainUser.getPersonID(), mainUser);
            setmotherSides(mother);
        }
        if (mainUser.getFather() != null) {
            Person father = people.get(mainUser.getFather());
            father.setMfSide("dad");
            father.getChildren().put(mainUser.getPersonID(), mainUser);
            setfatherSides(father);
        }

    }

    public static void setfatherSides(Person p) {
        if (!p.getMother().equals("null")) {
            Person mother = people.get(p.getMother());
            mother.getChildren().put(p.getPersonID(), p);
            mother.setMfSide("dad");
            setfatherSides(mother);
        }
        if (!p.getFather().equals("null")) {
            Person father = people.get(p.getFather());
            father.setMfSide("dad");
            father.getChildren().put(p.getPersonID(), p);
            setfatherSides(father);
        }
    }

    public static void setmotherSides(Person p) {
        if (!p.getMother().equals("null")) {
            Person mother = people.get(p.getMother());
            mother.getChildren().put(p.getPersonID(), p);
            mother.setMfSide("mom");
            setmotherSides(mother);
        }
        if (!p.getFather().equals("null")) {
            Person father = people.get(p.getFather());
            father.getChildren().put(p.getPersonID(), p);
            father.setMfSide("mom");
            setmotherSides(father);
        }
    }

    public static void resync() {

    }

    public static Event getFocusEvent() {
        return focusEvent;
    }

    public static void setFocusEvent(Event focusEvent) {
        ModelApp.focusEvent = focusEvent;
    }

    public static void logout() {
        ModelApp.SINGLETON.setLoggedIn(false);
        ModelApp.SINGLETON.setCurrentUser(null);
    }
}
