package com.stehanget.mvc.service.impl;

import com.stehanget.mvc.domain.Company;
import com.stehanget.mvc.domain.Item;
import com.stehanget.mvc.dto.ItemRequestDTO;
import com.stehanget.mvc.dto.ItemResponseDTO;
import com.stehanget.mvc.repository.ItemRepository;
import com.stehanget.mvc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> findMany() {
        return itemRepository.findAll();
    }

    @Override
    public ItemResponseDTO findOne(Long itemId) {
        try {
            ItemResponseDTO dto = new ItemResponseDTO();
            Item res = itemRepository.getById(itemId);

            dto.setId(res.getId());
            dto.setName(res.getName());
            dto.setPrice(res.getPrice());
            dto.setStock(res.getStock());
            dto.setDescription(res.getDescription());
            dto.setCreatedAt(res.getCreatedAt());
            dto.setUpdatedAt(res.getUpdatedAt());

            return dto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Item store(ItemRequestDTO dto) {
        Item item = new Item();

        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        item.setStock(dto.getStock());
        item.setDescription(dto.getDescription());
        item.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").format(new Date()));
        item.setUpdatedAt(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").format(new Date()));

        return itemRepository.save(item);
    }

    @Override
    public Item update(Long itemId, ItemRequestDTO dto) {
        try {
            Item item = itemRepository.getById(itemId);

            item.setName(dto.getName());
            item.setPrice(dto.getPrice());
            item.setStock(dto.getStock());
            item.setDescription(dto.getDescription());
            item.setUpdatedAt(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").format(new Date()));

            return itemRepository.save(item);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void destroy(Long itemId) {
        try {
            itemRepository.deleteById(itemId);
        } catch (Exception ignored) {

        }
    }
}
