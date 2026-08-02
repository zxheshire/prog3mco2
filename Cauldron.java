package MCO1;

public class Cauldron {

	private boolean isUsable;
	public static int price = 3000;
	public static int blessPrice = 1000;
	
	Cauldron(boolean usable) {
		this.isUsable = usable;
	}
	
	public boolean isUsable() {
		return this.isUsable;
	}
	
	public void setUsable(boolean usable) {
		this.isUsable = usable;
	}	
	
	public int getBuyingPrice() {
		return price;
	}
	
	public int getBlessingPrice() {
		return blessPrice;
	}
	
}
