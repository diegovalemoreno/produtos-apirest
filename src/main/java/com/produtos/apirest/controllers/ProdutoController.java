package com.produtos.apirest.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.produtos.apirest.models.ProdutoModel;
import com.produtos.apirest.repository.ProdutoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API REST Produtos")
public class ProdutoController {
    Logger logger = LoggerFactory.getLogger(ProdutoController.class);

    @Autowired
    ProdutoRepository produtoRepository;
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Retorna uma lista de Produtos")
    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoModel>> getAllProdutos() {
        List<ProdutoModel> produtosList = produtoRepository.findAll();
        if (produtosList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (ProdutoModel produto : produtosList) {
                long id = produto.getIdProduto();
                produto.add(linkTo(methodOn(ProdutoController.class).getOneProduto(id)).withSelfRel());
            }
            return new ResponseEntity<List<ProdutoModel>>(produtosList, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Retorna um produto unico")
    @GetMapping("/produtos/{id}")
    public ResponseEntity<ProdutoModel> getOneProduto(@PathVariable(value = "id") long id) {
        Optional<ProdutoModel> produtoO = produtoRepository.findById(id);
        if (!produtoO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            produtoO.get().add(linkTo(methodOn(ProdutoController.class).getAllProdutos()).withRel("Lista de Produtos"));
            return new ResponseEntity<ProdutoModel>(produtoO.get(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Salva um produto")
    @PostMapping("/produtos")
    public ResponseEntity<ProdutoModel> saveProduto(@RequestBody @Valid ProdutoModel produto) {
        return new ResponseEntity<ProdutoModel>(produtoRepository.save(produto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Deleta um produto")
    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<?> deleteProduto(@PathVariable(value = "id") long id) {
        Optional<ProdutoModel> produtoO = produtoRepository.findById(id);
        if (!produtoO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            produtoRepository.delete(produtoO.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Atualiza um produto")
    @PutMapping("/produtos/{id}")
    public ResponseEntity<ProdutoModel> updateProduto(@PathVariable(value="id") long id,
                                                      @RequestBody @Valid ProdutoModel produto) {
        Optional<ProdutoModel> produtoO = produtoRepository.findById(id);
        if(!produtoO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            produto.setIdProduto(produtoO.get().getIdProduto());
            return new ResponseEntity<ProdutoModel>(produtoRepository.save(produto), HttpStatus.OK);
        }
    }
}
