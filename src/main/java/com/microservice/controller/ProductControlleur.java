package com.microservice.controller;

import com.microservice.dao.ProductDao;
import com.microservice.exceptions.ProduitGratuitException;
import com.microservice.exceptions.ProduitIntrouvableException;
import com.microservice.model.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sidibe0091 on 08/11/2018.
 */
@Api(description="API pour es opérations CRUD sur les produits.")
@RestController
public class ProductControlleur {

    @Autowired
    private ProductDao productDao;

    //produits
    @ApiOperation(value = "renvoie la liste de tous les produits")
    @GetMapping(value = "produits")
    public List<Product> listeProduits(){
        return productDao.findAll();
    }

    //produits/{id}
    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
    @GetMapping(value = "produits/{id}")
    public Product afficherUnProduit(@PathVariable int id){
        Product produit = productDao.findById(id);
        if(produit==null) throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");
        return produit;
    }
    @ApiOperation(value = "ajoute un nouveau produit dans la base")
    @PostMapping(value = "produits")
    public ResponseEntity<Void> ajouterProduit(@RequestBody Product product){
        if(product.getPrix()<=0) throw  new ProduitGratuitException("le prix est doit être superieur à O");

       Product product1= productDao.save(product);
       if(product1==null) return ResponseEntity.noContent().build();
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product1.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "test/produits/{prixLimit}")
    public List<Product> listeProduitPrix(@PathVariable int prixLimit){
       // return productDao.findByPrixGreaterThan(prixLimit);
        return productDao.chercherUnProduitCher(prixLimit);
    }
@ApiOperation(value = "Supprime un prodtuit dont ID est donné en paramètre")
    @DeleteMapping(value = "produits/{id}")
    public void deleteProduit(@PathVariable Integer id){
        productDao.deleteById(id);
    }
@ApiOperation(value = "met à jour un produit passé en paramaètre")
    @PutMapping(value = "produits")
    public void update(@RequestBody Product product){
  productDao.save(product);
    }

    @GetMapping(value = "produitsParNom")
 public List<Product> ProduitIntrouvableException(){
       return productDao.trierProduitsParOrdre();
 }

    @GetMapping(value = "AdminProduits")
    public List calculeMargeProduit(){
        List<Product> products= productDao.findAll();
        List res=new ArrayList();
        products.forEach(product -> res.add(product +":"+ (product.getPrix()-product.getPrixAchat())));
        return res;
    }

}
