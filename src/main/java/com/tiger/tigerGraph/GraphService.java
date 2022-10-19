package com.tiger.tigerGraph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
public class GraphService {
    @Autowired
    Graph graph;

    /*
     * Opco->owns->Customer
     * Customer->can_buy->Product
     * Opco->sales->Product
     * */

    public void addNode(GraphEntity.Node node, List<String> ids) {
        graph.setQuery(String.format("INSERT INTO VERTEX %s(id,id) VALUES(?,?)", node));
        graph.createBatch(ids);
        graph.executeBatch();
    }

    public Map<String,List<String>> getAllNodes(GraphEntity.Node node) {
        graph.setQuery(String.format("GET VERTEX(%s)",node));
        return graph.execute();
    }

    public Map<String,List<String>> deleteNode(GraphEntity.Node node, String id) throws SQLException {
        PreparedStatement preparedStatement=graph.setQuery(String.format("DELETE VERTEX(%s, ?)", node));
        preparedStatement.setString(1, id);
        return graph.execute();
    }

    public void addEdge(GraphEntity.Edge edge, GraphEntity.Node source, GraphEntity.Node target, String sourceId, String targetId) throws SQLException {
        PreparedStatement preparedStatement=graph.setQuery(String.format("INSERT INTO EDGE %1$s(%2$s, %3$s) VALUES(?, ?)", edge,source,target));
        preparedStatement.setString(1, sourceId);
        preparedStatement.setString(2, targetId);
        preparedStatement.addBatch();
        graph.executeBatch();
    }

    public Map deleteEdge(GraphEntity.Edge edge, GraphEntity.Node source, GraphEntity.Node target, String sourceId, String targetId) throws SQLException {
        PreparedStatement preparedStatement=graph.setQuery(String.format("DELETE EDGE(%1$s, ?, %2$s, %3$s, ?)", source, edge,target));
        preparedStatement.setString(1, sourceId);
        preparedStatement.setString(2, targetId);
        return graph.execute();
    }


    public Map<String,List<String>> getAssociations(String sourceId, GraphEntity.Node source, GraphEntity.Edge edge, GraphEntity.Node target) throws SQLException {
        PreparedStatement preparedStatement=graph.setQuery(String.format("GET EDGE(%s, ?, %s, %s)",source,edge,target));
        preparedStatement.setString(1,sourceId);
        return graph.execute();
    }


}
