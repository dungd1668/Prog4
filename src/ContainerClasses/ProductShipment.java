package ContainerClasses;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.IntStream;

public class ProductShipment extends Container {



	String incomingDate;
	float purchasePrice;
	int amount;

	public ProductShipment() {

	}

	
	
	




	public ProductShipment(String incomingDate, float purchasePrice, int amount) {
		super();
		this.incomingDate = incomingDate;
		this.purchasePrice = purchasePrice;
		this.amount = amount;
	}








	// returns a random sale
	public Container GetNewRandom() {

		Random r = new Random();
		float num = r.nextFloat() * 1000;
		BigDecimal bd = new BigDecimal(Float.toString(num));
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		ProductShipment newEmp = new ProductShipment(GetRandomSaleDate(),bd.floatValue(), r.nextInt(5000));

		// if its a duplicate dont increment id
		return newEmp;
	}











	public String getIncomingDate() {
		return incomingDate;
	}








	public void setIncomingDate(String incomingDate) {
		this.incomingDate = incomingDate;
	}








	public float getPurchasePrice() {
		return purchasePrice;
	}








	public void setPurchasePrice(float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}








	public int getAmount() {
		return amount;
	}








	public void setAmount(int amount) {
		this.amount = amount;
	}





	
	

}
