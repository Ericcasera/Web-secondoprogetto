/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author Daniel
 */
public class Sale {
    
    private int auction_id , buyer_id , seller_id;
    private boolean cancelled = false , retreat = false , sold = false;
    private float   retreat_commissions , commissions , price ;
    private long    sale_date;

    public int getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(int auction_id) {
        this.auction_id = auction_id;
    }

    public int getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(int buyer_id) {
        this.buyer_id = buyer_id;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isRetreat() {
        return retreat;
    }

    public void setRetreat(boolean retreat) {
        this.retreat = retreat;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public float getRetreat_commissions() {
        return retreat_commissions;
    }

    public void setRetreat_commissions(float retreat_commissions) {
        this.retreat_commissions = retreat_commissions;
    }

    public float getCommissions() {
        return commissions;
    }

    public void setCommissions(float commissions) {
        this.commissions = commissions;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getSale_date() {
        return sale_date;
    }

    public void setSale_date(long sale_date) {
        this.sale_date = sale_date;
    }
    
    
    
}
