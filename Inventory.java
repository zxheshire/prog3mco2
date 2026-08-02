package MCO2.model;

public class Inventory {
	public InventoryItem items[] = new InventoryItem[99];
	public Cauldron cauldrons[] = new Cauldron[99];
	
	public Inventory() {
		for (int i=0; i<3; i++) {
			cauldrons[i] = new Cauldron(true);	
		}
	}
	
	public void addIngredient(Ingredients item, int qty) {
		for (int i=0; i < items.length; i++) {
			if (items[i] == null) {
				items[i] = new InventoryItem(item, qty);
				System.out.println("Ingredient added to inventory.");
				break;
			}
			 
			else if (item.getIngredientName().equals(items[i].getIngredient().ingredientName)) {
				items[i].addQuantity(qty);
				break;
			}
			
		}
	}
	
	public void removeIngredient(int i, int qty) {
			if(i >= 0 && i < items.length && items[i] != null) {
				items[i].deductQuantity(qty);
				
				if(items[i].getQuantity() <= 0) {
					items[i] = null;
				}
			}
	}
	
	public void viewInventory(boolean isSelling) {
		int noneCheck = 0;
		boolean selling = isSelling;
		
		if (!selling) {
			System.out.println("\n---------- Current Inventory ----------");
			System.out.printf("%-3s %-13s | %5s%n", "#", "Item", "Quantity");
			System.out.println("---------------------------------------");
		}
		
		else {
			System.out.println("\n---------- Current Inventory ----------");
			System.out.printf("%-3s %-13s | %5s | %10s%n", "#", "Item", "Qty", "Sell Price");
			System.out.println("---------------------------------------");
		}
		

		// Print cauldrons
		if (!selling) {
			 System.out.printf("%-3s %-13s | %5d%n", "-", "Cauldron", getUsableCauldronsCount());
		}

		// Print damaged cauldrons
		if (!selling && getUnusableCauldronsCount() > 0) {
	        System.out.printf("%-3s %-13s | %5d%n", "-", "Damaged Cauldron", getUnusableCauldronsCount());
	    }
		
		// Print ingredients
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
		    	 noneCheck++;
		    } 
		    	
		    else {
		    	if (selling) {
		    		System.out.printf("%-3d %-13s | %5d | %10d%n", i + 1,
		    		items[i].getIngredient().getIngredientName(),
		    		items[i].getQuantity(),
		    		items[i].getIngredient().getSellingPrice());
		    	}
		    	
		    	else {
		    		System.out.printf("%-3d %-13s | %5d%n", i + 1,
		    		items[i].getIngredient().getIngredientName(),
		    		items[i].getQuantity());
		    	}
		    }
		}

		if (noneCheck == items.length && getUsableCauldronsCount() == 0 && getUnusableCauldronsCount() == 0) {
			System.out.println("\nYour inventory is empty!");
		}
    }
	
	/*
	 * For changing the index numbers when an entire item is sold 
	 */
	public void compactInventory() {

	    int index = 0;
	    
	    for (int i = 0; i < items.length; i++) {

	        if (items[i] != null && items[i].getQuantity() > 0) {

	            items[index] = items[i];
	            index++;

	        }
	    }
	    // to remove null spaces
	    while (index < items.length) {

	        items[index] = null;
	        index++;

	    }
	}
	
	
	public int getIngredientQty(String name) {
		if(name == null) return 0;
		
		for (int i=0; i < items.length; i++) {
			if(items[i] != null && items[i].getIngredient() != null) {
			if (name.equalsIgnoreCase(items[i].getIngredient().getIngredientName())) {
				return items[i].getQuantity();
			}
			
		}
		}
		
		return 0;
	}
	
	public void addCauldron() {
		for (int i=0; i < cauldrons.length; i++) {
			if (cauldrons[i] == null) {
				cauldrons[i] = new Cauldron(true);
				System.out.println("Cauldron added to inventory.");
				break;
			}		
		}
	}
	
	public int getUsableCauldronsCount() {
		int frequency = 0;
		
		for (int i=0; i < cauldrons.length; i++) {
			if (cauldrons[i] != null && cauldrons[i].isUsable() == true) {
				frequency++;
			}
		}
		
		return frequency;
	}
	
	public int getUnusableCauldronsCount() {
		int frequency = 0;
		
		for (int i=0; i < cauldrons.length; i++) {
			if (cauldrons[i] != null && cauldrons[i].isUsable() == false) {
				frequency++;
			}
		}
		
		return frequency;
	}
	
	public Cauldron[] getCauldronArray() {
		return this.cauldrons;
	}
	
	public boolean isEmpty() {
		for(int i = 0; i < items.length; i++) {
			if(items[i] != null && items[i].getQuantity() > 0) {
				return false;
			}
		}
		return true;
	}

	public InventoryItem[] getItems(){
		return items;
	}

	public void setItems(InventoryItem items[]){
		this.items = items;
	}

	public Cauldron[] getCauldrons(){
		return cauldrons;
	}

	public void setCauldrons(Cauldron cauldrons[]){
		this.cauldrons = cauldrons;
	}
}
