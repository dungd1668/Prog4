package ContainerClasses;

import java.util.Random;
import java.util.stream.IntStream;

public class SubSale extends Container {



	String saleID;
	String subSaleID;
	String productID;
	float price;
	int amount;

	public SubSale() {

	}

	
	
	
	
	
	public SubSale(String saleID, String subSaleID, String productID, float price, int amount) {
		super();
		this.saleID = saleID;
		this.subSaleID = subSaleID;
		this.productID = productID;
		this.price = price;
		this.amount = amount;
	}



	// returns a random sale
	public Container GetNewRandomSubSale(String saleID, String productID, float price) {
		Random rand = new Random();
		int amount1 = rand.nextInt(3) + 1;
		SubSale newEmp = new SubSale(saleID, getRandID(), productID, price * amount1,amount1 );

		return newEmp;
	}






	public String getSaleID() {
		return saleID;
	}






	public void setSaleID(String saleID) {
		this.saleID = saleID;
	}






	public String getSubSaleID() {
		return subSaleID;
	}






	public void setSubSaleID(String subSaleID) {
		this.subSaleID = subSaleID;
	}






	public String getProductID() {
		return productID;
	}






	public void setProductID(String productID) {
		this.productID = productID;
	}






	public float getPrice() {
		return price;
	}






	public void setPrice(float price) {
		this.price = price;
	}






	public int getAmount() {
		return amount;
	}






	public void setAmount(int amount) {
		this.amount = amount;
	}






	@Override
	public Container GetNewRandom() {
		// TODO Auto-generated method stub
		return null;
	}


		

	
}
