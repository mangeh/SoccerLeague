import java.util.Comparator;

/**
 * Created by Magnus on 2016-10-26.
 */
public class Team implements Comparable<Team> {

    private String coachName;
    private String teamName;


    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public int compareTo(Team otherTeam) {
        return this.getTeamName().compareTo(otherTeam.getTeamName());
    }
}
