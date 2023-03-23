package com.aarci.sb3.service;

import com.aarci.sb3.command.CreateItemCommand;
import com.aarci.sb3.dto.ItemDTO;
import com.aarci.sb3.entity.Item;
import com.aarci.sb3.repository.ItemRepository;
import com.aarci.sb3.utility.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;


    public ItemDTO createItem(CreateItemCommand command){
        Item item=new Item();
        item.setNome(command.getNome());
        item.setDescription(command.getDescription());
        item.setEnabled(command.isEnabled());
        item.setPrice(command.getPrice());
        this.itemRepository.save(item);
        return DTOConverter.convertToDTO(item);
    }

}
