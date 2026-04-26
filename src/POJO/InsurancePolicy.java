package POJO;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

//The Request XML (ACORD Style)

@XmlRootElement(name = "PolicyRequest", namespace = "http://majesco.com/policy")
@XmlAccessorType(XmlAccessType.FIELD)
public class InsurancePolicy {

	@XmlElement(name = "Type", namespace = "http://majesco.com/policy")
	private String policyType;
	@XmlAttribute
	private String id;
	@XmlElement(name = "PremiumDetails", namespace = "http://majesco.com/policy")
	private Premium premium;

	@XmlAttribute // Maps to <PolicyRequest id="123">
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@XmlElement(name = "Type") // Maps to <Type>Auto</Type>
	public void setPolicyType(String type) {
		this.policyType = type;
	}

	public String getPolicyType() {
		return policyType;
	}

	@XmlElement(name = "PremiumDetails")
	public void setPremium(Premium premium) {
		this.premium = premium;
	}

	public Premium getPremium() {
		return premium;
	}
}
