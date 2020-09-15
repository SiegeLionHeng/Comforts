package com.outrank.global.base.adapter;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

public class PowerAdapter extends MultiTypeAdapter {

    public boolean hasLoadMoreItem;

    /**
     * 追加数据
     *
     * @param beans
     */
    public void addBeans(List beans) {
        List items = getItems();
        if (beans != null) {
            int newListSize = beans.size();
            for (int i = 0; i < newListSize; i++) {
                Object bean = beans.get(i);
                if (!items.contains(bean)) {
                    if (hasLoadMoreItem) {
                        items.add(items.size() - 1, bean);
                    } else {
                        items.add(bean);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    public void addBean(Object bean) {
        List items = getItems();
        if (bean != null && !items.contains(bean)) {
            if (hasLoadMoreItem) {
                items.add(items.size() - 1, bean);
            } else {
                items.add(bean);
            }
            notifyDataSetChanged();
        }
    }

    public void addBeanAtPosition(int position, Object bean) {
        try {
            List items = getItems();
            if (bean != null && !items.contains(bean)) {
                items.add(position, bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyItemInserted(position);
    }

    public void deleteBean(int index) {
        List items = getItems();
        items.remove(index);
        if (index == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRemoved(index);
        }
    }

    /**
     * 获取条目
     *
     * @param position
     * @return
     */
    public Object getItem(int position) {
        List<?> items = getItems();
        if (position < items.size()) {
            return items.get(position);
        }
        return null;
    }

    /**
     * 设置数据
     *
     * @param beans
     */
    public void setBeans(List beans) {
        if (beans == null) {
            beans = new ArrayList();
        }
        setItems(beans);
        notifyDataSetChanged();
    }

}
