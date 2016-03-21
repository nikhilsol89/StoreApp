package com.example.nikhil.storeapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nikhil on 3/20/2016.
 */
public class ItemDetailsModel implements Parcelable {

    String itemName, itemCategory, itemDescription;
    byte[] imageByteArray;
    int itemPrice, itemId;
    int quantity;

    public ItemDetailsModel() {

    }

    private ItemDetailsModel(Parcel in) {
        itemName = in.readString();
        itemCategory = in.readString();
        itemDescription = in.readString();
        itemPrice = in.readInt();
        itemId = in.readInt();
        imageByteArray = in.createByteArray();
        quantity = in.readInt();
    }

    public byte[] getImageByteArray() {
        return imageByteArray;
    }

    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemName);
        parcel.writeString(itemCategory);
        parcel.writeString(itemDescription);
        parcel.writeInt(itemPrice);
        parcel.writeInt(itemId);
        parcel.writeByteArray(imageByteArray);
        parcel.writeInt(quantity);
    }

    public static final Creator<ItemDetailsModel> CREATOR = new Creator<ItemDetailsModel>() {
        @Override
        public ItemDetailsModel createFromParcel(Parcel in) {
            return new ItemDetailsModel(in);
        }

        @Override
        public ItemDetailsModel[] newArray(int size) {
            return new ItemDetailsModel[size];
        }
    };
}
