package ContainerClasses;

import java.util.Random;
import java.util.stream.IntStream;

public class Sale extends Container {

	public Sale(String saleID, String date, String paymentMethod, float totalPrice, String memberID) {
		super();
		this.saleID = saleID;
		this.date = date;
		this.paymentMethod = paymentMethod;
		this.totalPrice = totalPrice;
		this.memberID = memberID;
	}

	String saleID;
	String date;
	String paymentMethod;
	float totalPrice;
	String memberID;

	public Sale() {

	}

	// returns a random sale
	public Container GetNewRandomSale(String member, float totalPrice) {

		Sale newEmp = new Sale(getRandID(), GetRandomSaleDate(), GetRandomPay(), totalPrice, member);

		// if its a duplicate dont increment id
		return newEmp;
	}

	private String GetRandomPay() {
		Random rand = new Random();
		String gen = "";
		if (rand.nextInt(2) == 0) {
			gen = "Credit";
		} else
			gen = "Cash";
		return gen;
	}
	
	
	
	
	
	

	@Override
	public Container GetNewRandom() {
		// TODO Auto-generated method stub
		return null;
	}
	

	public String getSaleID() {
		return saleID;
	}

	public void setSaleID(String saleID) {
		this.saleID = saleID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	
	
	
	

}
