package com.tiger.tigerGraph;

import com.tiger.tigerGraph.Config.DbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Graph {

    @Autowired
    private DbConfig dbConfig;

    String query;

    private PreparedStatement preparedStatement;

    public Map execute(){
        System.out.println(String.format("Running %s...",query));
        Map results = null;
        try{
            try (ResultSet rs = preparedStatement.executeQuery()) {
                printResults(rs);
                results=getResults(rs);
            }
        } catch (Exception e) {
            System.out.println( "Failed to createStatement: " + e);
        }finally {
            dbConfig.getDataSource().close();
        }
        return results;
    }

    public void executeBatch(){
        System.out.println(String.format("Running %s...",query));
        try{
            int[] count = preparedStatement.executeBatch();
            System.out.println(count);
        } catch (Exception e) {
            System.out.println( "Failed to createStatement: " + e);
        }
    }

    private Map getResults(ResultSet rs) throws SQLException{
        Map<String, List<String>> results=new HashMap<>();
        do {
            ResultSetMetaData metaData = rs.getMetaData();
            for(int i=1;i<=metaData.getColumnCount();i++){
                results.put(metaData.getColumnName(i),new ArrayList<>());
            }
            while (rs.next()) {
               results.keySet().stream().forEach(key->{
                   List<String> ids= results.get(key);
                   try {
                       ids.add(rs.getString(key));
                   } catch (SQLException e) {
                       throw new RuntimeException(e);
                   }
                   results.put(key,ids);
               });
            }
        } while (!rs.isLast());
        return results;
    }

    private void printResults(ResultSet rs) throws SQLException {

        do {
            ResultSetMetaData metaData = rs.getMetaData();
            System.out.println("Table: " + metaData.getCatalogName(1));
            System.out.print(metaData.getColumnName(1));
            for (int i = 2; i <= metaData.getColumnCount(); ++i) {
                System.out.print("\t" + metaData.getColumnName(i));
            }
            System.out.println("");
            while (rs.next()) {
                System.out.print(rs.getObject(1));
                for (int i = 2; i <= metaData.getColumnCount(); ++i) {
                    Object obj = rs.getObject(i);
                    System.out.print("\t" + String.valueOf(obj));
                }
                System.out.println("");
            }
        } while (!rs.isLast());
    }

    public PreparedStatement setQuery(String query) {
        try{
            this.query=query;
            preparedStatement = dbConfig.getDataSource().getConnection().prepareStatement(query);
        }catch (Exception ex){
            System.out.println("Exception occured");
        }
        return preparedStatement;
    }

    public void createBatch(List<String> ids){
        ids.forEach(id->{
            try {
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, id);
                preparedStatement.addBatch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }




}
