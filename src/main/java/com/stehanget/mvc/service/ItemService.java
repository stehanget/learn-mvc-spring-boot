package com.stehanget.mvc.service;

import com.stehanget.mvc.domain.Item;
import com.stehanget.mvc.dto.ItemRequestDTO;
import com.stehanget.mvc.dto.ItemResponseDTO;

import java.util.List;

public interface ItemService {
    List<Item> findMany();

    ItemResponseDTO findOne(Long itemId);

    Item store(ItemRequestDTO dto);

    Item update(Long itemId, ItemRequestDTO dto);

    void destroy(Long itemId);
}
