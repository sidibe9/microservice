package com.microservice.dao;

import com.microservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sidibe0091 on 08/11/2018.
 */
@Repository
public interface ProductDao extends JpaRepository<Product,Integer> {
 public Product findById(int id);
 public List<Product> findByPrixGreaterThan(int prix);

 @Query("select p from Product p where p.prix > :prixLimit")
 public List<Product> chercherUnProduitCher(@Param("prixLimit") int prix);

 @Query("select p from Product p order by p.nom asc ")
 public List<Product> trierProduitsParOrdre();

}
