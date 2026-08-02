package MCO2.model;

public class MarketItems {
	
	// characteristics for each slot
	public Ingredients item;
	public Cauldron cauldron;
	private int qty;
	private boolean soldOut;
	
	public MarketItems() {
		// empty constructor for object creation
	}
	
	public MarketItems(Cauldron item, int qty) {
		this.qty = qty;
		this.cauldron = item;
		this.soldOut = false;
	}
	
	public MarketItems(Ingredients item, int qty) {
		this.item = item; 
		this.qty = qty;
		this.soldOut = false;
	}
	
	public String getSlotName() {
		if (this.cauldron != null) {
	        return "Cauldron";
	    }
		
		if(this.item == null) {
			return "Unknown Slot Item";
		}
		
		return this.item.getIngredientName();
	}
	
	
	public int getSlotQuantity() {
		return this.qty;
	}
	
	public void setSoldOut(boolean soldOut) {
		this.soldOut = soldOut;
	}
	
	public boolean isSoldOut() {
		return this.soldOut;
	}
	
	public int getMarketPrice() {
	    if (cauldron != null) {
	        return cauldron.getBuyingPrice();
	    }
	    
	    if(this.item == null) {
	    	return 100;
	    }

	    return item.getBuyingPrice();
	}
	
	public int getSellingPrice() {
		if(this.item == null) {
			return 25;
		}
		return item.getSellingPrice();
	}
	
	public boolean hasCauldron() {
		return cauldron != null;
	}
	
	public Ingredients getIngredient() {
		return item;
	}
	
	public Cauldron getCauldron() {
		return cauldron;
	}
	
}
