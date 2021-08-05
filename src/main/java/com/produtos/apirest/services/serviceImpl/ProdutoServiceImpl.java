package com.produtos.apirest.services.serviceImpl;

import com.produtos.apirest.models.ProdutoModel;
import com.produtos.apirest.repository.ProdutoRepository;
import com.produtos.apirest.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Override
    public List<ProdutoModel> findAll() {
        return produtoRepository.findAll();
    }

    @Override
    public ProdutoModel findById(long id) {
        Optional<ProdutoModel> produtoModel = produtoRepository.findById(id);
        if (produtoModel.isEmpty()){
            return null;
        }else{
            return  produtoModel.get();
        }
    }

    @Override
    public ProdutoModel save(ProdutoModel produtoModel) {
        return produtoRepository.save(produtoModel);
    }

    @Override
    public void deleteById(ProdutoModel produtoModel) {
        produtoRepository.delete(produtoModel);
    }


}
