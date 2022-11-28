package com.plannerssystem.utils;

import com.plannerssystem.models.ItemTemplate;
import com.plannerssystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ItemTemplateRepository extends JpaRepository<ItemTemplate, Long> {

    @Query("SELECT t FROM ItemTemplate t WHERE user = ?1 AND isDeleted = 0")
    public Set<ItemTemplate> getItemTemplatesByUser(User user);

    @Query("SELECT t FROM ItemTemplate t WHERE id = ?1")
    public ItemTemplate getItemTemplateByID(long id);
}
