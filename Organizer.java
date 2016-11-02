import com.sun.deploy.util.OrderedHashSet;
import com.teamtreehouse.model.Player;

import java.util.*;

/**
 * Created by Magnus on 2016-10-26.
 */
public class Organizer {

    private IOPrompter ioPrompter;
    private SortedSet<Player> playerSet;
    SortedMap<Team, Set<Player>> leagueMap;

    private static final String TALL = "47-50";
    private static final String MEDIUM = "41-46";
    private static final String SHORT = "35-40";

    public Organizer(Player[] players) {
        ioPrompter = new IOPrompter();
        leagueMap = new TreeMap<>();
        playerSet = new TreeSet<>(Arrays.asList(players));
    }

    public SortedMap<Team, Set<Player>> startOrganising() {

        makeMenuChoice();
        return leagueMap;
    }

    private void makeMenuChoice() {
        ioPrompter.printOrganizerMenu();
        Integer choice = ioPrompter.getChoice();
        switch (choice) {
            case 1:
                addTeam(leagueMap);
                break;
            case 2:
                listAllPlayers();
                Player p = getPlayer();
                if (p!=null) {
                    makePlayerChoice(p);
                }
                break;
            case 3:
                listAllTeams();
                Team t = getTeam();
                makeTeamChoice(t);
                break;
            case 4:
                leagueBalanceReport();
                break;
            case 0:
                break;
            default:
                ioPrompter.printError();
                makeMenuChoice();
                break;
        }

//        return leagueMap;

    }


    private void makePlayerChoice(Player p) {
        ioPrompter.printPlayerMenu(p);
        int choice = ioPrompter.getChoice();
        switch (choice) {
            case 1:
                addPlayerToTeam(p);
                break;
            case 2:
                removePlayerFromTeam(p);
                break;
            case 0:
                makeMenuChoice();
                break;
            default:
                ioPrompter.printError();
                makePlayerChoice(p);
                break;
        }

    }

    private void makeTeamChoice(Team t) {
        ioPrompter.printOrganizerTeamMenu(t);
        int choice = ioPrompter.getChoice();
        switch (choice) {
            case 1:
                getHeightReport(t);
                break;
            case 2:
                showRoster(t);
                break;
            case 0:
                makeMenuChoice();
                break;
            default:
                ioPrompter.printError();
                makeTeamChoice(t);
                break;
        }

    }

    private void showRoster(Team t) {

        ioPrompter.printRoster(leagueMap.get(t));


    }

    private void getHeightReport(Team t) {
        Map<String, Set<Player>> heightMap = new TreeMap<>();
        Set<Player> playersInTeam = leagueMap.get(t);
        heightMap.put(SHORT, new HashSet<>());
        heightMap.put(MEDIUM, new HashSet<>());
        heightMap.put(TALL, new HashSet<>());

        for (Player player : playersInTeam) {
            int heightOfPlayer = player.getHeightInInches();
            if (heightOfPlayer >= 35 && heightOfPlayer <= 40) {
                Set<Player> playerSet = heightMap.get(SHORT);
                playerSet.add(player);
                heightMap.put(SHORT, playerSet);
            } else if (heightOfPlayer >= 41 && heightOfPlayer <= 46) {
                Set<Player> playerSet = heightMap.get(MEDIUM);
                playerSet.add(player);
                heightMap.put(MEDIUM, playerSet);
            } else if (heightOfPlayer >= 47 && heightOfPlayer <= 50) {
                Set<Player> playerSet = heightMap.get(TALL);
                playerSet.add(player);
                heightMap.put(TALL, playerSet);
            }

        }

        ioPrompter.printHeightReport(heightMap);
        makeTeamChoice(t);
    }

    private void addTeam(Map leagueMap) {
        Team team = new Team();
        team.setCoachName(ioPrompter.getString("coach name"));
        team.setTeamName(ioPrompter.getString("team name"));
        leagueMap.put(team, new TreeSet<>());
        ioPrompter.displayTeams(leagueMap);
        makeMenuChoice();
    }

    private void addPlayerToTeam(Player playerToAdd) {
        ArrayList<Team> availableTeams = new ArrayList<>();
        for (Map.Entry<Team, Set<Player>> teamandplayers : leagueMap.entrySet()) {
            boolean playerExistInTeam = false;
            for (Player player : teamandplayers.getValue()) {
                if (player.equals(playerToAdd)) {
                    playerExistInTeam = true;
                }

            }
            if (!playerExistInTeam && teamandplayers.getValue().size() < 11) {
                availableTeams.add(teamandplayers.getKey());
            }
        }
        if (availableTeams.size() < 1) {
            ioPrompter.noTeamsAvailable();
        } else {
            Team selectedTeam = ioPrompter.displayTeams(availableTeams, leagueMap);
            Set players = leagueMap.get(selectedTeam);
            players.add(playerToAdd);
            leagueMap.put(selectedTeam, players);
        }
        makeMenuChoice();
    }


    private void removePlayerFromTeam(Player playerToRemove) {

        ArrayList<Team> availableTeams = new ArrayList<>();

        for (Map.Entry<Team, Set<Player>> teamandplayers : leagueMap.entrySet()) {
            boolean playerExistInTeam = false;
            for (Player player : teamandplayers.getValue()) {
                if (player.equals(playerToRemove)) {
                    playerExistInTeam = true;
                }

            }
            if (playerExistInTeam) {
                availableTeams.add(teamandplayers.getKey());
            }
        }
        if (availableTeams.size() < 1) {
            ioPrompter.noTeamsAvailable();
        } else {
            Team selectedTeam = ioPrompter.displayTeams(availableTeams, leagueMap);
            Set players = leagueMap.get(selectedTeam);
            players.remove(playerToRemove);
            leagueMap.put(selectedTeam, players);
        }
        makeMenuChoice();
    }


    private void listAllPlayers() {
        //List all players with stats
        int i = 0;
        ioPrompter.displayPlayerStatHeader();
        for (Player player : playerSet
                ) {
            i++;
            ioPrompter.displayPlayerStats(i, player);
        }
    }

    private void listAllTeams() {
        ioPrompter.displayTeams(leagueMap);
    }

    private void leagueBalanceReport() {
        //Show all teams and how many experienced / inexperienced players they have
        ioPrompter.getBalanceReport(leagueMap);
        makeMenuChoice();
    }


    public Player getPlayer() {
        int playerNumber = ioPrompter.getPlayerSelection();

        int j = 0;
        for (Player player : playerSet) {
            if (j == playerNumber) {
                return player;
            }
            j++;
        }

        ioPrompter.printError();
        makeMenuChoice();
        return null;
    }

    public Team getTeam() {
        if (leagueMap.keySet().size() > 0) {
            int playerNumber = ioPrompter.getTeamSelection();

            int j = 0;
            for (Team team : leagueMap.keySet()) {
                if (j == playerNumber) {
                    return team;
                }
                j++;
            }
        } else {
            ioPrompter.noTeamsAvailable();
            makeMenuChoice();
        }


        return null;
    }
}
