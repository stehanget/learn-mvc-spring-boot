package com.stehanget.mvc.web;

import com.stehanget.mvc.domain.Item;
import com.stehanget.mvc.dto.ItemRequestDTO;
import com.stehanget.mvc.dto.ItemResponseDTO;
import com.stehanget.mvc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemResource {

    @Autowired
    private ItemService itemService;

    @GetMapping("/item")
    public ResponseEntity<List<Item>> index() {
        List<Item> items = itemService.findMany();

        return ResponseEntity.ok(items);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<ItemResponseDTO> show(@PathVariable("itemId") Long itemId) {
        ItemResponseDTO item = itemService.findOne(itemId);

        return ResponseEntity.ok(item);
    }

    @PostMapping("/item")
    public ResponseEntity<Item> store(@RequestBody ItemRequestDTO dto) throws URISyntaxException {
        Item item = itemService.store(dto);

        return ResponseEntity.created(new URI("/api/item")).body(item);
    }

    @PutMapping("/item/{itemId}")
    public ResponseEntity<Item> update(@PathVariable("itemId") Long itemId, @RequestBody ItemRequestDTO dto) {
        Item item = itemService.update(itemId, dto);

        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void> destroy(@PathVariable("itemId") Long itemId) {
        itemService.destroy(itemId);

        return ResponseEntity.ok().build();
    }
}
