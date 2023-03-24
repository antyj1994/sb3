package com.aarci.sb3.service;

import com.aarci.sb3.command.CreateItemCommand;
import com.aarci.sb3.command.UpdateItemCommand;
import com.aarci.sb3.dto.ItemDTO;
import com.aarci.sb3.dto.ResponseWrapperDTO;
import com.aarci.sb3.entity.Item;
import com.aarci.sb3.repository.ItemRepository;
import com.aarci.sb3.utility.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;


    public ItemDTO createItem(CreateItemCommand command){
        Item item=new Item();
        item.setName(command.getName());
        item.setDescription(command.getDescription());
        item.setEnabled(command.isEnabled());
        item.setPrice(command.getPrice());
        this.itemRepository.save(item);
        return DTOConverter.convertToDTO(item);
    }

    public ItemDTO getItem(String name){
        Optional<Item> itemOptional=this.itemRepository.findByName(name);
        if (itemOptional.isEmpty()){
            throw new RuntimeException("Item doesn't exists");
        }
        return DTOConverter.convertToDTO(itemOptional.get());
    }

    public List<ResponseWrapperDTO> getAll(){
        List<Item> items = this.itemRepository.findAll();
        List<ResponseWrapperDTO> response= new ArrayList<>();
        for(Item item : items){
            ResponseWrapperDTO responseWrapperDTO = new ResponseWrapperDTO<>(DTOConverter.convertToDTO(item));
            response.add(responseWrapperDTO);
        }
        return response;
    }

    public ItemDTO updateItem(UpdateItemCommand command){
        Optional<Item> esistente = this.itemRepository.findByName(command.getOldName());
        if (esistente.isEmpty()){
            throw new RuntimeException("Item doesn't exists");
        }
        Optional<Item> nuovoNomeEsistente = this.itemRepository.findByName(command.getNewName());
        if (nuovoNomeEsistente.equals(esistente)){
            throw new RuntimeException("Item already exists");
        }
        Item item = esistente.get();
        item.setDescription(command.getDescription());
        item.setName(command.getNewName());
        item.setPrice(command.getPrice());
        item.setEnabled(command.isEnabled());
        this.itemRepository.save(item);
        return DTOConverter.convertToDTO(item);
    }

    public ItemDTO delete(String name){
        Optional<Item> eliminato= this.itemRepository.findByName(name);
        if(eliminato.isEmpty()){
            throw new RuntimeException("Item doesn't exists");
        }
        Item item= eliminato.get();
        this.itemRepository.delete(item);
        return DTOConverter.convertToDTO(item);
    }
}
