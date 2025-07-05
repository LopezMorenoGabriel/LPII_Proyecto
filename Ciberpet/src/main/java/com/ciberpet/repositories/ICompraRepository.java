package com.ciberpet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ciberpet.models.Compra;

@Repository
public interface ICompraRepository extends JpaRepository<Compra,Integer>{

}
