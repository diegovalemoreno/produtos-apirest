package com.produtos.apirest.services;

import com.produtos.apirest.models.ProdutoModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ProdutoService {
    List<ProdutoModel> findAll();
    ProdutoModel findById(long id);
    ProdutoModel save(ProdutoModel produtoModel);
    void deleteById(ProdutoModel produtoModel);
}
