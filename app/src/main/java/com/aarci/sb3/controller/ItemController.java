package com.aarci.sb3.controller;

import com.aarci.sb3.command.CreateItemCommand;
import com.aarci.sb3.command.UpdateItemCommand;
import com.aarci.sb3.command.UpdatePermessoCommand;
import com.aarci.sb3.dto.BaseDTO;
import com.aarci.sb3.dto.ItemDTO;
import com.aarci.sb3.dto.PermessoDTO;
import com.aarci.sb3.dto.ResponseWrapperDTO;
import com.aarci.sb3.entity.Item;
import com.aarci.sb3.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {
    private final ItemService itemService;
    public ItemController(ItemService itemService){this.itemService= itemService;}

    @PostMapping(path = "/item")
    public ResponseWrapperDTO<ItemDTO> createItem(@RequestBody CreateItemCommand command){
        ItemDTO itemDTO =this.itemService.createItem(command);
        return new ResponseWrapperDTO<>(itemDTO);
    }

    @GetMapping(path = "/item/{name}")
    public ResponseWrapperDTO<ItemDTO> getItem(@PathVariable("name") String name){
        ItemDTO itemDTO=this.itemService.getItem(name);
        return new ResponseWrapperDTO<>(itemDTO);
    }

    @GetMapping(path = "/item")
    public List<ResponseWrapperDTO> getAll(){
        List<ResponseWrapperDTO> itemsDTO = this.itemService.getAll();
        return itemsDTO;
    }

    @PutMapping(path = "/item")
    public ResponseWrapperDTO<ItemDTO> updateItem(@RequestBody UpdateItemCommand command){
        ItemDTO itemDTO = this.itemService.updateItem(command);
        return new ResponseWrapperDTO<>(itemDTO);
    }

    @DeleteMapping(path = "/item/{name}")
    public ResponseWrapperDTO<ItemDTO> deleteItem(@PathVariable("name") String name){
        ItemDTO itemDTO = this.itemService.delete(name);
        return new ResponseWrapperDTO<>(itemDTO);
    }

    @ExceptionHandler({ RuntimeException.class })
    public ResponseWrapperDTO<BaseDTO> handleException(Exception ex) {
        ResponseWrapperDTO<BaseDTO> responseWrapperDTO = new ResponseWrapperDTO<>();
        responseWrapperDTO.setCode(HttpStatus.BAD_REQUEST.value());
        responseWrapperDTO.setStatus("Error");
        responseWrapperDTO.setMessage(ex.getMessage());
        return responseWrapperDTO;
    }
}
