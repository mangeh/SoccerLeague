import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class LeagueManager {

  private SortedMap<Team, Set<Player>> leagueMap;

  public static void main(String[] args) {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.length);
    //Get user (organizer / coach)
    Menu menu = new Menu(players);
    menu.mainMenu();
  }




}
