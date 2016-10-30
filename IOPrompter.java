import com.teamtreehouse.model.Player;

import java.util.*;

/**
 * Created by Magnus on 2016-10-26.
 */
public class IOPrompter {
    private Scanner scanner;

    IOPrompter() {
        scanner = new Scanner(System.in);
    }

    void printMainMenu() {
        System.out.println("What role do you have?");
        System.out.println("1. Organizer");
        System.out.println("2. Coach");
        System.out.println("0. Exit");
    }

    void printCoachMenu() {
        System.out.println("What do you want to do?");
        System.out.println("1. Print roster");
        System.out.println("0. Back");
    }

    void printOrganizerMenu() {
        System.out.println("What do you want to do?");
        System.out.println("1. Add team");
        System.out.println("2. List players");
        System.out.println("3. List teams");
        System.out.println("4. Balance report");
        System.out.println("0. Back");
    }

    //Should logic be refactored away from the prompter class or is this ok?
    int getChoice() {
        Integer choice = 0;
        System.out.printf(("%nType the value that corresponds with the menu item and press enter:%n"));
        try {
            choice = Integer.parseInt(scanner.next());
        } catch (NumberFormatException ne) {
            System.out.println("That was not a number, try again");
            return getChoice();
        }
        return choice;
    }

    String getString(String inputType) {
        System.out.printf("Input %s: ", inputType);
        String input = scanner.next();
        return input;
    }

    void printError() {
        System.out.println("That is not a valid choice");
    }

    void noTeamsAvailable() {
        System.out.printf("%nNo teams are available%n%n");
    }

    public void displayPlayerStats(int playerNo, Player player) {

        System.out.printf("%d. %-15s %-15s %-15s %-15s %n", playerNo, player.getFirstName(), player.getLastName(), player.getHeightInInches(), player.isPreviousExperience());
    }

    public int getPlayerSelection() {
        System.out.println("Enter player number for further actions");
        return getChoice() - 1;
    }

    public int getTeamSelection() {
        System.out.println("Enter team number for further actions");
        return getChoice() - 1;
    }

    public void printPlayerMenu(Player player) {
        if (player != null) {
            System.out.printf("What do you want to do with %s %s?%n", player.getFirstName(), player.getLastName());
            System.out.println("1. Add player to team");
            System.out.println("2. Remove player from team");
        } else {
            printPlayerSetError();
        }
    }

    public void printOrganizerTeamMenu(Team t) {
        System.out.printf("What do you want to do with %s?%n", t.getTeamName());
        System.out.println("1. Get height report");
        System.out.println("2. View roster");
    }

    public void displayTeams(Map<Team, Set<Player>> allteams) {
        int i = 1;
        System.out.println("Teams: ");
        for (Map.Entry<Team, Set<Player>> team : allteams.entrySet()) {
            System.out.printf("%d. Team: %-10s Coach: %-10s %n", i, team.getKey().getTeamName(), team.getKey().getCoachName());
            i++;
        }
    }

    public void getBalanceReport(Map<Team, Set<Player>> allteams) {
        int i = 0;

        System.out.println("Teams: ");
        for (Map.Entry<Team, Set<Player>> team : allteams.entrySet()) {
            int experienced = 0;
            int unexperienced = 0;
            int total = 0;
            System.out.printf("%d. Team: %-10s Coach: %-10s ", i, team.getKey().getTeamName(), team.getKey().getCoachName());
            for (Player player : team.getValue()) {
                if (player.isPreviousExperience()) {
                    experienced++;
                } else {
                    unexperienced++;
                }
                total++;
            }
            System.out.printf("Experienced: %-10d Unexperienced: %-10d %n", experienced, unexperienced);

        }
        System.out.printf("%n%n");
    }

    public void printRoster(Set<Player> players) {
        int i = 1;
        if (players.size() > 0) {
            displayPlayerStatHeader();
            for (Player player : players
                    ) {


                displayPlayerStats(i, player);
                i++;
            }
        } else {
            printPlayerSetError();
        }


    }

    public void printPlayerSetError() {
        System.out.printf("%nSomething wrong with player roster%n");
    }


    public Team displayTeams(ArrayList<Team> teams, Map<Team, Set<Player>> allteams) {
        int i = 1;
        System.out.println("Teams: ");
        for (Team team : teams) {
            System.out.printf("%d. Team: %-10s Coach: %-10s %n", i, team.getTeamName(), team.getCoachName());
            i++;
        }
        try {
            return teams.get(getChoice() - 1);
        } catch (ArrayIndexOutOfBoundsException ae) {
            System.out.println("No team with that index available");
            return null;
        }
    }

    public void displayPlayerStatHeader() {
        System.out.printf("First Name:      Last Name:      Height:     Previous Experience: %n");
    }


    public void printHeightReport(Map<String, Set<Player>> heightMap) {
        for (Map.Entry<String, Set<Player>> heightClass : heightMap.entrySet()) {
            System.out.printf("%s :",heightClass.getKey());
            for (Player player : heightClass.getValue()) {
                System.out.printf("%n%s %s",player.getFirstName(),player.getLastName());
            }
            System.out.println("");

        }
    }

    public String getCoachNameInput() {
        System.out.println("What is your coach name?");
        return getString("name");
    }
    public String getTeamNameInput() {
        System.out.println("What is your team name?");
        return getString("name");
    }

}
