import com.teamtreehouse.model.Player;

import java.util.Set;
import java.util.SortedMap;

/**
 * Created by Magnus on 2016-10-26.
 */
public class Menu {


    SortedMap<Team, Set<Player>> leagueMap;
    Organizer organizer;
    Coach coach;
    IOPrompter ioPrompter;
    Player[] players;

    public Menu(Player[] players) {
        ioPrompter = new IOPrompter();
        organizer = new Organizer(players);
        coach = new Coach();
        this.players = players;
    }

    public void mainMenu() {


        ioPrompter.printMainMenu();
        Integer choice = ioPrompter.getChoice();
        switch (choice) {
            case 1:
                setLeagueMap(organizer.startOrganising());
                mainMenu();
                break;
            case 2:
                coach.startCoachInterface(getLeagueMap());
                mainMenu();
                break;
            case 0:
                System.out.println("Ok, bye");
                break;
            default:
                ioPrompter.printError();
                mainMenu();
        }
    }

    public SortedMap<Team, Set<Player>> getLeagueMap() {
        return leagueMap;
    }

    public void setLeagueMap(SortedMap<Team, Set<Player>> leagueMap) {
        this.leagueMap = leagueMap;
    }
}
