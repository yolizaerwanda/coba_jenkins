package com.yoliza.produk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yoliza.produk.model.Produk;

@Repository
public interface ProdukRepository extends JpaRepository<Produk, Long> {
    
}
