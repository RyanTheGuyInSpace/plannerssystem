package com.plannerssystem.utils;

import com.plannerssystem.models.ItemTemplate;
import com.plannerssystem.models.ItemTemplateItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemTemplateItemRepository extends JpaRepository<ItemTemplateItem, Long> {
    @Query("SELECT t FROM ItemTemplateItem t WHERE id = ?1")
    public ItemTemplateItem getItemTemplateItemByID(long id);
}
