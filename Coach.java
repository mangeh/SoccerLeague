import com.teamtreehouse.model.Player;

import java.util.*;

/**
 * Created by Magnus on 2016-10-26.
 */
public class Coach {

    private IOPrompter ioPrompter;
    SortedMap<Team, Set<Player>> leagueMap;


    public Coach() {
        ioPrompter = new IOPrompter();
    }

    public Map<Team, Set<Player>> startCoachInterface(SortedMap<Team, Set<Player>> leagueMap) {
        this.leagueMap = leagueMap;
        makeMenuChoice();
        return this.leagueMap;
    }
    private void makeMenuChoice() {
        ioPrompter.printCoachMenu();
        Integer choice = ioPrompter.getChoice();
        switch (choice) {
            case 1:
                getRoster();
                break;
            case 0:
                break;
            default:
                ioPrompter.printError();
                makeMenuChoice();
                break;
        }
    }

    private void getRoster() {
        String coachName = ioPrompter.getCoachNameInput();
        String teamName = ioPrompter.getTeamNameInput();
        Team team =  new Team();
        team.setCoachName(coachName);
        team.setTeamName(teamName);

        Set<Player> roster = leagueMap.get(team);
        if (roster != null) {
            ioPrompter.printRoster(roster);
        } else {
            ioPrompter.printPlayerSetError();
        }

    }



}
