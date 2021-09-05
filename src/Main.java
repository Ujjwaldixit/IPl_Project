import com.Ipl.connection.connection_provider;
import com.Ipl.dao.deliveriesDao;
import com.Ipl.dao.matchesDao;
import com.Ipl.entities.Delivery;
import com.Ipl.entities.Match;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Main {

    static Connection connection = null;

    public static void main(String[] args) {
        connection = connection_provider.getConnection();
        matchesDao matchesDao = new matchesDao(connection);
        List<Match> matches = matchesDao.getMatchesData();
        deliveriesDao deliveriesDao = new deliveriesDao(connection);
        List<Delivery> deliveries = deliveriesDao.getDeliveries();

        findMatchesPlayedPerYear(matches);
        findMatchesWonPerTeam(matches);
        findExtraRunsPerTeamsIn2016(matches, deliveries);
        findEconomicalBowlersPerRunGivenIn2015(matches, deliveries);
        findTotalMatchesPlayedPerCity(matches);
    }

    private static void findTotalMatchesPlayedPerCity(List<Match> matches) {
        HashMap<String, Integer> totalMatchesPlayedPerCity = new HashMap<>();
        String query = "select ipl.matches.city, count(ipl.matches.city) as total_matches from ipl.matches group by ipl.matches.city";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String city = resultSet.getString("city");
                Integer totalMatches = resultSet.getInt("total_matches");
                totalMatchesPlayedPerCity.put(city, totalMatches);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        totalMatchesPlayedPerCity.remove(""); //if totalMatchesPlayedPerCity contains "" key

        System.out.println("CITY => TOTAL_MATCHES");
        for (Map.Entry<String, Integer> entry : totalMatchesPlayedPerCity.entrySet())
            System.out.println(entry.getKey() +
                    " => " + entry.getValue());

    }


    private static void findEconomicalBowlersPerRunGivenIn2015(List<Match> matches, List<Delivery> deliveries) {
        LinkedHashMap<String, Integer> bowlerPerRunGiven = new LinkedHashMap<>();
        String query = """
                select ipl.deliveries.bowler,sum(ipl.deliveries.total_runs) as runs_given\s
                from ipl.deliveries\s
                inner join ipl.matches on ipl.matches.id=ipl.deliveries.match_id\s
                where ipl.matches.season='2015'
                group by ipl.deliveries.bowler\s
                order by sum(ipl.deliveries.total_runs);""";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String bowler = resultSet.getString("bowler");
                Integer runsGiven = resultSet.getInt("runs_given");
                bowlerPerRunGiven.put(bowler, runsGiven);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n\nBOWLERS => RUN GIVEN");
        for (Map.Entry<String, Integer> entry : bowlerPerRunGiven.entrySet())
            System.out.println(entry.getKey() +
                    " => " + entry.getValue());
        System.out.println("\n\n");
    }

    private static void findExtraRunsPerTeamsIn2016(List<Match> matches, List<Delivery> deliveries) {
        HashMap<String, Integer> teamPerExtraRuns = new HashMap<>();
        String query = """
                select ipl.deliveries.batting_team,sum(ipl.deliveries.extra_runs) as extra_runs\s
                from ipl.deliveries inner join ipl.matches
                on ipl.matches.id=ipl.deliveries.match_id\s
                where ipl.matches.season='2016'\s
                group by ipl.deliveries.batting_team""";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String teams = resultSet.getString("batting_team");
                Integer extraRuns = resultSet.getInt("extra_runs");
                teamPerExtraRuns.put(teams, extraRuns);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n\nTEAMS =>  EXTRA RUNS");
        for (Map.Entry<String, Integer> entry : teamPerExtraRuns.entrySet())
            System.out.println(entry.getKey() +
                    " => " + entry.getValue());

        System.out.println("\n\n");
    }

    private static void findMatchesWonPerTeam(List<Match> matches) {
        HashMap<String, Integer> matchesWonPerTeam = new HashMap<>();
        String query = "select ipl.matches.team1,count(ipl.matches.winner) as matches_won from ipl.matches group by ipl.matches.winner";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String teams = resultSet.getString("team1");
                Integer matchesWon = resultSet.getInt("matches_won");
                matchesWonPerTeam.put(teams, matchesWon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //to remove blank key if present
        matchesWonPerTeam.remove("");

        System.out.println("\n\nTEAM => NO OF MATCHES WON");
        for (Map.Entry<String, Integer> entry : matchesWonPerTeam.entrySet())
            System.out.println(entry.getKey() +
                    " => " + entry.getValue());
        System.out.println("\n\n");
    }

    private static void findMatchesPlayedPerYear(List<Match> matches) {
        LinkedHashMap<String, Integer> matchesPlayedPerYear = new LinkedHashMap<>();
        String query = "select ipl.matches.season, count(ipl.matches.season) as total_matches from ipl.matches group by ipl.matches.season order by ipl.matches.season";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String season = resultSet.getString("season");
                Integer totalMatches = resultSet.getInt("total_matches");
                matchesPlayedPerYear.put(season, totalMatches);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n\nSEASON => MATCHES PLAYED");
        for (Map.Entry<String, Integer> entry : matchesPlayedPerYear.entrySet())
            System.out.println(entry.getKey() +
                    " => " + entry.getValue());
        System.out.println("\n\n");
    }

}
