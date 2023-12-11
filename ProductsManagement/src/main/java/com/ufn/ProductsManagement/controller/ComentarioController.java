package com.ufn.ProductsManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufn.ProductsManagement.models.Comentario;
import com.ufn.ProductsManagement.service.ComentarioService;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {
    
    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    public ResponseEntity<List<Comentario>> getAllComentarios() {
        List<Comentario> comentarios = comentarioService.findAll();
        return ResponseEntity.ok(comentarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comentario> getComentarioById(@PathVariable Long id) {
        Comentario comentario = comentarioService.findById(id);
        if (comentario != null) {
            return ResponseEntity.ok(comentario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{produtoId}")
    public ResponseEntity<Comentario> addComentario(@RequestBody Comentario comentario, @PathVariable Long produtoId) {
        Comentario novoComentario = comentarioService.save(comentario, produtoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoComentario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comentario> updateComentario(@PathVariable Long id, @RequestBody String novoTexto) {
        Comentario comentarioAtualizado = comentarioService.updateComentario(id, novoTexto);
        return ResponseEntity.ok(comentarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComentario(@PathVariable Long id) {
        comentarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
