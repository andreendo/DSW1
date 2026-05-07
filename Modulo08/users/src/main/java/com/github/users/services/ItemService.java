package com.github.users.services;

import com.github.users.domain.dtos.ItemAddForm;
import com.github.users.domain.dtos.ItemForm;
import com.github.users.domain.entities.Item;
import com.github.users.repositories.ItemRepository;
import com.github.users.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public List<ItemForm> findAll() {
        var retItems = new ArrayList<ItemForm>();
        itemRepository.findAll().forEach(item -> {
            var itemForm = new ItemForm();
            itemForm.setId(item.getId());
            itemForm.setName(item.getName());
            itemForm.setDescription(item.getDescription());
            itemForm.setOwner(item.getUser().getUsername());
            retItems.add(itemForm);
        });
        return retItems;
    }

    public List<ItemForm> findAllByOwner(String owner) {
        var retItems = new ArrayList<ItemForm>();
        itemRepository.findByUserUsername(owner).forEach(item -> {
            var itemForm = new ItemForm();
            itemForm.setId(item.getId());
            itemForm.setName(item.getName());
            itemForm.setDescription(item.getDescription());
            itemForm.setOwner(item.getUser().getUsername());
            retItems.add(itemForm);
        });
        return retItems;
    }

    @Transactional
    public void deleteItem(Long id, String currentUsername, Collection<? extends GrantedAuthority> authorities) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        // Owner always deletes
        if (item.getUser().getUsername().equals(currentUsername)) {
            itemRepository.delete(item);
            return;
        }

        // Admin can delete for all users
        boolean isAdmin = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwnerUser = item.getUser().getRole().equals("USER");
        if (isAdmin && isOwnerUser) {
            itemRepository.delete(item);
            return;
        }

        throw new AccessDeniedException("You are not allowed to delete this item");
    }

    public void saveItem(ItemAddForm itemAddForm, String currentUsername) {
        var user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        var item = new Item();
        item.setName(itemAddForm.getName());
        item.setDescription(itemAddForm.getDescription());
        item.setUser(user);
        itemRepository.save(item);
    }

}
