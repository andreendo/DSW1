package com.github.userrest.services;

import com.github.userrest.domain.dtos.ItemAddForm;
import com.github.userrest.domain.dtos.ItemListForm;
import com.github.userrest.domain.entities.Item;
import com.github.userrest.repositories.ItemRepository;
import com.github.userrest.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<ItemListForm> listItems(String username, Collection<? extends GrantedAuthority> authorities) {
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        // Improvement: mover esta lógica para o repositório
        List<Item> authorizedItems = isAdmin
                ? itemRepository.findAll()
                : itemRepository.findByUserUsername(username);

        return authorizedItems.stream()
                .map(item -> {
                    var itemForm = new ItemListForm();
                    itemForm.setId(item.getId());
                    itemForm.setName(item.getName());
                    itemForm.setDescription(item.getDescription());
                    itemForm.setOwner(item.getUser().getUsername());
                    return itemForm;
                }).toList();
    }

    public ItemListForm save(ItemAddForm itemAddForm, String owner) {
        var user = userRepository.findByUsername(owner)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        var item = new Item();
        item.setName(itemAddForm.getName());
        item.setDescription(itemAddForm.getDescription());
        item.setUser(user);
        item = itemRepository.save(item);

        var itemForm = new ItemListForm();
        itemForm.setId(item.getId());
        itemForm.setName(item.getName());
        itemForm.setDescription(itemAddForm.getDescription());
        itemForm.setOwner(item.getUser().getUsername());
        return itemForm;
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
}
