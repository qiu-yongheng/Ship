package com.kc.shiptransport.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kc.shiptransport.adapter.ExpandableItemAdapter;

/**
 * 根目录
 */
public class Level3Item extends AbstractExpandableItem<MultiItemEntity> implements MultiItemEntity {
    public String title;

    public Level3Item(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_3;
    }

    @Override
    public int getLevel() {
        return 3;
    }
}
