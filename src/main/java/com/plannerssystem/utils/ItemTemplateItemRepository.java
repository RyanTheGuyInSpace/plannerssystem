package com.plannerssystem.utils;

import com.plannerssystem.models.ItemTemplate;
import com.plannerssystem.models.ItemTemplateItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTemplateItemRepository extends JpaRepository<ItemTemplateItem, Long> {
}
