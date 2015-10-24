package com.mooreds.quickbooks;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.intuit.ipp.data.PurchaseOrder;
 
@XmlRootElement(name = "QueryResponse")
@XmlAccessorType (XmlAccessType.FIELD)
public class PurchaseOrders
{
    @XmlElement(name = "PurchaseOrder")
    private List<PurchaseOrder> purchaseOrders = null;
 
    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }
 
    public void setEmployees(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
}