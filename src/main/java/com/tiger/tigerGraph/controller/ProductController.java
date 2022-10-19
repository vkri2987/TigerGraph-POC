package com.tiger.tigerGraph.controller;

import com.tiger.tigerGraph.GraphEntity.Node;
import com.tiger.tigerGraph.GraphEntity.Edge;
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
public class ProductController {

    @Autowired
    private GraphService graphService;

    @Operation(summary = "Get Products", description = "List of Products")
    @GetMapping(path = "/products")
    public @ResponseBody ResponseEntity<List<String>> listAllProducts(
    ) {
        List<String> products = graphService.getAllNodes(Node.Product).get("id");
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Create Products", description = "Create Products")
    @PostMapping(path = "/products/{id}")
    public @ResponseBody ResponseEntity<String> createProduct(
            @Parameter(description="productId", required=true)
            @PathVariable("id") List<String> id
    ) {
        graphService.addNode(Node.Product,id);
        return new ResponseEntity<>("Successfully created", HttpStatus.OK);
    }

    @Operation(summary = "Delete Products", description = "Delete Products")
    @DeleteMapping(path = "/products/{id}")
    public @ResponseBody ResponseEntity<String> deleteProduct(
            @Parameter(description="productId", required=true)
            @PathVariable("id") String id
    ) {
        String result="Successfully Deleted Product %s";
        try{
            graphService.deleteNode(Node.Product,id);
        }catch (Exception ex){
            result="Failed to delete Product %s";
        }
        return new ResponseEntity<>(String.format(result,id), HttpStatus.OK);
    }

    @Operation(summary = "Get opcos from Product", description = "Get opcos from product")
    @GetMapping(path = "/products/{id}/opco")
    public @ResponseBody ResponseEntity<List<String>> getAllOpcosFromProduct(
            @Parameter(description="productId", required=true)
            @PathVariable("id") String id
    ) {
        List<String> opcos=new ArrayList<>();
        try {
            opcos = graphService.getAssociations(id,Node.Product, Edge.reverse_sales,Node.Opco).get(Node.Opco.toString());
        }catch (Exception ignored){

        }

        return new ResponseEntity<>(opcos, HttpStatus.OK);
    }

    @Operation(summary = "Get customers from Product", description = "Get customers from product")
    @GetMapping(path = "/products/{id}/customer")
    public @ResponseBody ResponseEntity<List<String>> getAllCustomerFromProduct(
            @Parameter(description="productId", required=true)
            @PathVariable("id") String id
    ) {
        List<String> customer=new ArrayList<>();
        try {
            customer = graphService.getAssociations(id,Node.Product, Edge.reverse_can_buy,Node.Customer).get(Node.Customer.toString());
        }catch (Exception ignored){

        }

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}
