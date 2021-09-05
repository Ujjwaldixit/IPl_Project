package com.Ipl.dao;

import com.Ipl.entities.Match;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class matchesDao {
    Connection connection = null;

    public matchesDao(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Match> getMatchesData()
    {
        ArrayList<Match> matches=new ArrayList<>();
        String query="select * from ipl.matches";
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet=statement.executeQuery(query);
            Match match=new Match();
            while(resultSet.next())
            {
                match.setId(resultSet.getInt("id"));
                match.setSeason(resultSet.getInt("season"));
                match.setCity(resultSet.getString("city"));
                match.setDate(resultSet.getString("date"));
                match.setTeam1(resultSet.getString("team1"));
                match.setTeam2(resultSet.getString("team2"));
                match.setTossWinner(resultSet.getString("toss_winner"));
                match.setTossDecision(resultSet.getString("toss_decision"));
                match.setResult(resultSet.getString("result"));
                match.setDlApplied(resultSet.getInt("dl_applied"));
                match.setWinner(resultSet.getString("winner"));
                match.setWinByRuns(resultSet.getInt("win_by_runs"));
                match.setWinByWickets(resultSet.getString("win_by_wickets"));
                match.setPlayerOfMatch(resultSet.getString("player_of_match"));
                match.setVenue(resultSet.getString("venue"));
                match.setUmpire1(resultSet.getString("umpire1"));
                match.setUmpire2(resultSet.getString("umpire2"));
                matches.add(match);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return matches;
    }
}
