package com.tiger.tigerGraph;

public class GraphEntity {
    public enum Node {
        Opco,
        Customer,
        Product
    }

    public enum Edge {
        owns,
        sales,
        can_buy,
        reverse_owns,
        reverse_sales,
        reverse_can_buy
    }
}
