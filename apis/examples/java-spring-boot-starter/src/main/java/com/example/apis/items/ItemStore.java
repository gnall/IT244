package com.example.apis.items;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class ItemStore {
  private final AtomicLong nextId = new AtomicLong(1);
  private final Map<Long, Item> items = new LinkedHashMap<>();

  public Item create(String name, String value) {
    long id = nextId.getAndIncrement();
    Item item = new Item(id, name, value);
    items.put(id, item);
    return item;
  }

  public List<Item> list() {
    return new ArrayList<>(items.values());
  }

  public Optional<Item> get(long id) {
    return Optional.ofNullable(items.get(id));
  }

  public Optional<Item> delete(long id) {
    return Optional.ofNullable(items.remove(id));
  }

  public Optional<Item> update(long id, String name, String value) {
    if (!items.containsKey(id)) return Optional.empty();
    Item updated = new Item(id, name, value);
    items.put(id, updated);
    return Optional.of(updated);
  }
}

