package com.aarci.sb3.utility;

import com.aarci.sb3.dto.ItemDTO;
import com.aarci.sb3.dto.PermessoDTO;
import com.aarci.sb3.dto.UtenteDTO;
import com.aarci.sb3.entity.Item;
import com.aarci.sb3.entity.Permesso;
import com.aarci.sb3.entity.Utente;

public class DTOConverter {
    public static UtenteDTO convertToDTO(Utente utente){
        UtenteDTO utenteDTO = new UtenteDTO();
        utenteDTO.setEmail(utente.getEmail());
        utenteDTO.setUsername(utente.getUsername());
        utenteDTO.setPassword(utente.getPassword());
        utenteDTO.setPermessi(utente.getPermessi());
        return utenteDTO;
    }

    public static PermessoDTO convertToDTO(Permesso permesso){
        PermessoDTO permessoDTO = new PermessoDTO();
        permessoDTO.setNome(permesso.getNome());
        permessoDTO.setDescrizione(permesso.getDescrizione());
        //permessoDTO.setUtenti(permesso.getUtenti());
        return permessoDTO;
    }

    public static ItemDTO convertToDTO(Item item){
        ItemDTO itemDTO=new ItemDTO();
        itemDTO.setName(item.getName());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setEnabled(item.isEnabled());
        itemDTO.setPrice(item.getPrice());
        return itemDTO;
    }
}
