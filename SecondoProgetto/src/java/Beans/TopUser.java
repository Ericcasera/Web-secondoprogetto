/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author Daniel
 */
public class TopUser {
    
    private String username , email;
    private int auctions_number;
    private float total_price;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAuctions_number() {
        return auctions_number;
    }

    public void setAuctions_number(int auctions_number) {
        this.auctions_number = auctions_number;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

}
