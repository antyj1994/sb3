package com.aarci.sb3.controller;

import com.aarci.sb3.command.CreateItemCommand;
import com.aarci.sb3.dto.ItemDTO;
import com.aarci.sb3.entity.Item;
import com.aarci.sb3.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemController {
    private final ItemService itemService;
    public ItemController(ItemService itemService){this.itemService= itemService;}

    @PostMapping(path = "/item")
    public ItemDTO createItem(@RequestBody CreateItemCommand command){return this.itemService.createItem(command);}
}
