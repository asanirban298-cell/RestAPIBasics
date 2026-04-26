package POJO;

import jakarta.xml.bind.annotation.XmlElement;

public class Premium {
private int amount;
    
    @XmlElement(name = "Amount", namespace = "http://majesco.com/policy")
    public void setAmount(int amount) { this.amount = amount; }
    public int getAmount() { return amount; }
}
