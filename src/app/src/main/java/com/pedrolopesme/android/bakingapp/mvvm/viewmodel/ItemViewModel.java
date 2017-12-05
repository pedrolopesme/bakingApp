package com.pedrolopesme.android.bakingapp.mvvm.viewmodel;

/**
 * Item View Model
 */
public abstract class ItemViewModel<ITEM_T> extends ViewModel {

    protected ItemViewModel() {
        super(null);
    }

    public abstract void setItem(ITEM_T item);
}
