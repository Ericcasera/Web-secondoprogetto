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
public class Offer {
    
    int user_id;
    String username;
    float price;
    long date;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
    
    public String getFormatDate(){
        Calendar result = Calendar.getInstance();
        result.setTimeInMillis(date);
        return "Il " + result.get(Calendar.DAY_OF_MONTH) + "/" + result.get(Calendar.MONTH) + "/" +result.get(Calendar.YEAR) + " alle ore " + result.get(Calendar.HOUR_OF_DAY) + ":" + result.get(Calendar.MINUTE) + ":" + result.get(Calendar.SECOND);
        
    }
}
