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
public class CustomerController {

    @Autowired
    private GraphService graphService;

    @Operation(summary = "Get Customers", description = "List of Customers")
    @GetMapping(path = "/customers")
    public @ResponseBody ResponseEntity<List<String>> listAllOpcos(
    ) {
        List<String> customers = graphService.getAllNodes(Node.Customer).get("id");
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @Operation(summary = "Create Customer", description = "Create Customer")
    @PostMapping(path = "/customers/{id}")
    public @ResponseBody ResponseEntity<String> createCustomer(
            @Parameter(description="customerId", required=true)
            @PathVariable("id") List<String> id
    ) {
        graphService.addNode(Node.Customer,id);
        return new ResponseEntity<>("Successfully created", HttpStatus.OK);
    }

    @Operation(summary = "Delete Customers", description = "Delete Customers")
    @DeleteMapping(path = "/customers/{id}")
    public @ResponseBody ResponseEntity<String> deleteCustomer(
            @Parameter(description="customerId", required=true)
            @PathVariable("id") String id
    ) {
        String result="Successfully Deleted Customer %s";
        try{
            graphService.deleteNode(Node.Customer,id);
        }catch (Exception ex){
            result="Failed to delete Customer %s";
        }
        return new ResponseEntity<>(String.format(result,id), HttpStatus.OK);
    }

    @Operation(summary = "Get opcos from customer", description = "Get Opcos from customer")
    @GetMapping(path = "/customers/{id}/opco")
    public @ResponseBody ResponseEntity<List<String>> getAllOpcoFromCustomer(
            @Parameter(description="customerId", required=true)
            @PathVariable("id") String id
    ) {
        List<String> opcos=new ArrayList<>();
        try {
            opcos = graphService.getAssociations(id,Node.Customer, Edge.reverse_owns,Node.Opco).get(Node.Opco.toString());
        }catch (Exception ignored){

        }

        return new ResponseEntity<>(opcos, HttpStatus.OK);
    }

    @Operation(summary = "Get CanBuy", description = "get Customers canbuy List")
    @GetMapping(path = "/customers/{id}/canbuy")
    public @ResponseBody ResponseEntity<List<String>> getAllCustomerFromProduct(
            @Parameter(description="customerId", required=true)
            @PathVariable("id") String id
    ) {
        List<String> products=new ArrayList<>();
        try {
            products = graphService.getAssociations(id,Node.Customer, Edge.can_buy,Node.Product).get(Node.Product.toString());
        }catch (Exception ignored){

        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Add canbuy", description = "Add canbuy")
    @PostMapping(path = "/customers/{customerId}/product/{productId}")
    public @ResponseBody ResponseEntity<String> addCanBuy(
            @Parameter(description="customerId", required=true)
            @PathVariable("customerId") String customerId,
            @Parameter(description="productId", required=true)
            @PathVariable("productId") String productId
    ) {
        try {
            graphService.addEdge(Edge.can_buy,Node.Customer,Node.Product,customerId,productId);
        }catch (Exception ignored){

        }

        return new ResponseEntity<>("Successfully Added the association", HttpStatus.OK);
    }


    @Operation(summary = "Delete canbuy", description = "Delete canbuy")
    @DeleteMapping(path = "/customers/{customerId}/product/{productId}")
    public @ResponseBody ResponseEntity<String> deleteCanBuy(
            @Parameter(description="customerId", required=true)
            @PathVariable("customerId") String customerId,
            @Parameter(description="productId", required=true)
            @PathVariable("productId") String productId
    ) {
        try {
            graphService.deleteEdge(Edge.can_buy,Node.Customer,Node.Product,customerId,productId);
        }catch (Exception ignored){

        }

        return new ResponseEntity<>("Successfully Deleted the association", HttpStatus.OK);
    }

}
