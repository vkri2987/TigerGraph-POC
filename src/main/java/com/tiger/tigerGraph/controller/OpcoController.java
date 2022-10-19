package com.tiger.tigerGraph.controller;

import com.tiger.tigerGraph.GraphEntity.Edge;
import com.tiger.tigerGraph.GraphEntity.Node;
import com.tiger.tigerGraph.GraphService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@CrossOrigin(origins = "*")
public class OpcoController {

    @Autowired
    private GraphService graphService;

    @Operation(summary = "Get Opcos", description = "List of Opcos")
    @GetMapping(path = "/opcos")
    public @ResponseBody ResponseEntity<List<String>> listAllOpcos(
    ) {
        List<String> products = graphService.getAllNodes(Node.Opco).get("id");
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Create Opco", description = "Create Opco")
    @PostMapping(path = "/opcos/{id}")
    public @ResponseBody ResponseEntity<String> createOpco(
            @Parameter(description="opcoId", required=true)
            @PathVariable("id") List<String> id
    ) {
        graphService.addNode(Node.Opco,id);
        return new ResponseEntity<>("Successfully created", HttpStatus.OK);
    }

    @Operation(summary = "Delete Opcos", description = "Delete Opcos")
    @DeleteMapping(path = "/opcos/{id}")
    public @ResponseBody ResponseEntity<String> deleteOpco(
            @Parameter(description="opcoId", required=true)
            @PathVariable("id") String id
    ) {
        String result="Successfully Deleted Opco %s";
        try{
            graphService.deleteNode(Node.Opco,id);
        }catch (Exception ex){
            result="Failed to delete Opco %s";
        }
        return new ResponseEntity<>(String.format(result,id), HttpStatus.OK);
    }

    @Operation(summary = "Get Customers from opco", description = "Get Customer from Opco")
    @GetMapping(path = "/opcos/{id}/customer")
    public @ResponseBody ResponseEntity<List<String>> getAllCustomersFromOpco(
            @Parameter(description="opcoId", required=true)
            @PathVariable("id") String id
    ) {
        List<String> customers=new ArrayList<>();
        try {
            customers = graphService.getAssociations(id,Node.Opco, Edge.owns,Node.Customer).get(Node.Customer.toString());
        }catch (Exception ignored){

        }

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @Operation(summary = "Get opco Catalog", description = "get Opco Catalog")
    @GetMapping(path = "/opcos/{id}/product")
    public @ResponseBody ResponseEntity<List<String>> getAllProductsFromOpco(
            @Parameter(description="opcoId", required=true)
            @PathVariable("id") String id
    ) {
        List<String> products=new ArrayList<>();
        try {
            products = graphService.getAssociations(id,Node.Opco, Edge.sales,Node.Product).get(Node.Product.toString());
        }catch (Exception ignored){

        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Add opco catalog", description = "Add opco catalog")
    @PostMapping(path = "/opcos/{opcoId}/product/{productId}")
    public @ResponseBody ResponseEntity<String> addOpcoCatalog(
            @Parameter(description="opcoId", required=true)
            @PathVariable("opcoId") String opcoId,
            @Parameter(description="productId", required=true)
            @PathVariable("productId") String productId
    ) {
        List<String> products=new ArrayList<>();
        try {
           graphService.addEdge(Edge.sales,Node.Opco,Node.Product,opcoId,productId);
        }catch (Exception ignored){

        }

        return new ResponseEntity<>("Successfully Added the association", HttpStatus.OK);
    }

    @Operation(summary = "Delete opco catalog", description = "Delete opco catalog")
    @DeleteMapping(path = "/opcos/{opcoId}/product/{productId}")
    public @ResponseBody ResponseEntity<String> deleteOpcoCatalog(
            @Parameter(description="opcoId", required=true)
            @PathVariable("opcoId") String opcoId,
            @Parameter(description="productId", required=true)
            @PathVariable("productId") String productId
    ) {
        try {
            graphService.deleteEdge(Edge.sales,Node.Opco,Node.Product,opcoId,productId);
        }catch (Exception ignored){

        }

        return new ResponseEntity<>("Successfully Added the association", HttpStatus.OK);
    }

    @Operation(summary = "Add opco customer", description = "Add opco customer")
    @PostMapping(path = "/opcos/{opcoId}/customer/{customerId}")
    public @ResponseBody ResponseEntity<String> addOpcoCustomer(
            @Parameter(description="opcoId", required=true)
            @PathVariable("opcoId") String opcoId,
            @Parameter(description="customerId", required=true)
            @PathVariable("customerId") String customerId
    ) {
        try {
            graphService.addEdge(Edge.owns,Node.Opco,Node.Customer,opcoId,customerId);
        }catch (Exception ignored){

        }

        return new ResponseEntity<>("Successfully Added the association", HttpStatus.OK);
    }

    @Operation(summary = "Delete opco customer", description = "Delete opco customer")
    @DeleteMapping(path = "/opcos/{opcoId}/customer/{customerId}")
    public @ResponseBody ResponseEntity<String> deleteOpcoCustomer(
            @Parameter(description="opcoId", required=true)
            @PathVariable("opcoId") String opcoId,
            @Parameter(description="customerId", required=true)
            @PathVariable("customerId") String customerId
    ) {
        try {
            graphService.deleteEdge(Edge.owns,Node.Opco,Node.Customer,opcoId,customerId);
        }catch (Exception ignored){

        }

        return new ResponseEntity<>("Successfully Deleted the association", HttpStatus.OK);
    }

}
