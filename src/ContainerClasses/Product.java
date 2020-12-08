package ContainerClasses;

import java.util.Random;
import java.util.stream.IntStream;

public class Product extends Container {
	
	public Product(String productID, String name, float retailPrice, String category, int membershipDiscount,
			int stockInfo) {
		super();
		this.productID = productID;
		this.name = name;
		this.retailPrice = retailPrice;
		this.category = category;
		this.membershipDiscount = membershipDiscount;
		this.stockInfo = stockInfo;
	}

	String productID;
	String name;
	float retailPrice;
	String category;
	int membershipDiscount;
	int stockInfo;
	

	public Product() {
		
	}
	
	
	
	//returns a random employee
	public Container GetNewRandom() {
		//random gender
		Random rand = new Random();
		
		float retail = rand.nextFloat() * 100 + 1;
		float price = 1 + rand.nextFloat() * (1000 - 1);
		price = (float) (Math.round(price * 100.0)/100.0);
		
		int discount = rand.nextInt((int)(price/3) + 1) + 1;
		
		int stock = rand.nextInt(1000);
		
		//make the new employee
		Product newEmp = new Product(getRandID(),
				GetRandomNoun(),
				price,
				GetRandomCategory(),
				discount,
				stock
				);
		
		//if its a duplicate dont increment id
		return newEmp;
	}
	
	public String GetRandomCategory() {
		String[] categories = {"Household","Cleaning","Food","Tech","Gaming","Clothes"};
		Random rand = new Random();
		return categories[rand.nextInt(categories.length - 1)];
	}


	
	public String getProductID() {
		return productID;
	}



	public void setProductID(String productID) {
		this.productID = productID;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public float getRetailPrice() {
		return retailPrice;
	}



	public void setRetailPrice(float retailPrice) {
		this.retailPrice = retailPrice;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public int getMembershipDiscount() {
		return membershipDiscount;
	}



	public void setMembershipDiscount(int membershipDiscount) {
		this.membershipDiscount = membershipDiscount;
	}



	public int getStockInfo() {
		return stockInfo;
	}



	public void setStockInfo(int stockInfo) {
		this.stockInfo = stockInfo;
	}

	




	
	
}
