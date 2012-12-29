/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.util.Calendar;

/**
 *
 * @author Daniel
 */
public class Auction {
  
    private int product_id ,seller_id , category_id , current_offers;
    private float current_price , shipping_price,starting_price , increment_price , min_price;
    private String name , image_url , description;
    private boolean cancelled;
    private long expiration;

    public int getCurrent_offers() {
        return current_offers;
    }

    public void setCurrent_offers(int current_offers) {
        this.current_offers = current_offers;
    }
 
    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public float getStarting_price() {
        return starting_price;
    }

    public void setStarting_price(float starting_price) {
        this.starting_price = starting_price;
    }

    public float getIncrement_price() {
        return increment_price;
    }

    public void setIncrement_price(float increment_price) {
        this.increment_price = increment_price;
    }

    public float getMin_price() {
        return min_price;
    }

    public void setMin_price(float min_price) {
        this.min_price = min_price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(float current_price) {
        this.current_price = current_price;
    }

    public float getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(float shipping_price) {
        this.shipping_price = shipping_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public String getTimeToExpiration(){
        long result = (expiration - Calendar.getInstance().getTimeInMillis()) /1000; //sec totali
        int sec =  (int) result % 60; //sec
        int min =  (int) (result / 60) % 60; //min
        int hour = (int) (result / (60 * 60)) % 24; //hour
        int day  = (int) (result / (60 * 60 * 24)) % 30; //day
        return "" + day + "d " + hour + "h " + min + "m " + sec + "s ";     
    }
    
    public String getExpirationDate(){
        Calendar result = Calendar.getInstance();
        result.setTimeInMillis(expiration);
        return "il giorno " + result.get(Calendar.DAY_OF_MONTH) + "/" + result.get(Calendar.MONTH) + "/" +result.get(Calendar.YEAR) + " alle ore " + result.get(Calendar.HOUR_OF_DAY) + ":" + result.get(Calendar.MINUTE) + ":" + result.get(Calendar.SECOND);
        
    }
    
    
  
}
