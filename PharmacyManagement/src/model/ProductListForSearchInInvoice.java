/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author admin
 */
public class ProductListForSearchInInvoice {
    private String code;
    private String pname;

    public ProductListForSearchInInvoice(String barcode, String productname) {
        this.code = barcode;
        this.pname = productname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
    
    
}
