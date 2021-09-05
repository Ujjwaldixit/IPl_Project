package com.Ipl.dao;

import com.Ipl.entities.Delivery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

public class deliveriesDao {
    private Connection connection = null;

    public deliveriesDao(Connection connection) {
        this.connection = connection;
    }

    public LinkedList<Delivery> getDeliveries() {
        LinkedList<Delivery> deliveries = new LinkedList<>();
        String query = "select * from ipl.deliveries";
        Delivery delivery = new Delivery();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                delivery.setMatch_id(resultSet.getInt("match_id"));
                delivery.setInning(resultSet.getInt("inning"));
                delivery.setBattingTeam(resultSet.getString("batting_team"));
                delivery.setBowlingTeam(resultSet.getString("bowling_team"));
                delivery.setOver(resultSet.getInt("over"));
                delivery.setBall(resultSet.getInt("ball"));
                delivery.setBatsman(resultSet.getString("batsman"));
                delivery.setNonStriker(resultSet.getString("non_striker"));
                delivery.setBowler(resultSet.getString("bowler"));
                delivery.setIsSuperOver(resultSet.getInt("is_super_over"));
                delivery.setWideRuns(resultSet.getInt("wide_runs"));
                delivery.setByeRuns(resultSet.getInt("bye_runs"));
                delivery.setNoBallRuns(resultSet.getInt("noball_runs"));
                delivery.setLegByeRuns(resultSet.getInt("legbye_runs"));
                delivery.setPenaltyRuns(resultSet.getInt("penalty_runs"));
                delivery.setBatsmanRuns(resultSet.getInt("batsman_runs"));
                delivery.setExtraRuns(resultSet.getInt("extra_runs"));
                delivery.setTotalRuns(resultSet.getInt("total_runs"));
                deliveries.add(delivery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveries;
    }
}
