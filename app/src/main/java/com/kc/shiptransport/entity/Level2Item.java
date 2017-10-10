package com.kc.shiptransport.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kc.shiptransport.adapter.ExpandableItemAdapter;

/**
 * 根目录
 */
public class Level2Item extends AbstractExpandableItem<MultiItemEntity> implements MultiItemEntity {
    public String title;

    public Level2Item(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_2;
    }

    @Override
    public int getLevel() {
        return 2;
    }
}
