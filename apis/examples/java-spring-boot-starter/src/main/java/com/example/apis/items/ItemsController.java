package com.example.apis.items;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemsController {
  private final ItemStore store;

  public ItemsController(ItemStore store) {
    this.store = store;
  }

  // TODO: POST /items (return 201 Created)
  // Hint:
  // - call store.create(req.name(), req.value())
  // - return ResponseEntity.status(HttpStatus.CREATED).body(createdItem)

  // TODO: GET /items (return list)

  // TODO: GET /items/{id} (return 200 or 404)

  // TODO: PUT /items/{id} (return 200 or 404)

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    boolean removed = store.delete(id).isPresent();
    return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }
}

