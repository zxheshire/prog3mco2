/* Manages the Inventory of the player
* @author Ysha Nacpil
* @version 1.0
* @since 2026
*/
package MCO2.model;

public class Inventory {
// Player inventory is categorized into two: ingredients and cauldrons
	public InventoryItem items[] = new InventoryItem[99];
	public Cauldron cauldrons[] = new Cauldron[99];

// Upon player creation, adds 3 usable cauldrons via cauldrons[]
	public Inventory() {
		for (int i=0; i<3; i++) {
			cauldrons[i] = new Cauldron(true);	
		}
	}

// Method used to add a selected ingredient into the inventory
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

// Removes ingredient in the inventory
	public void removeIngredient(int i, int qty) {
			if(i >= 0 && i < items.length && items[i] != null) {
				items[i].deductQuantity(qty);
				
				if(items[i].getQuantity() <= 0) {
					items[i] = null;
				}
			}
	}

// Displays the Inventory. Differs while selling items or by checking normally
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
	
	// For changing the index numbers when an entire item is sold 
	// Sorts the items to the nearest empty slot
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
	
	// Gets the Ingredient quantity
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

	// adds Cauldron(s) to inventory
	public void addCauldron() {
		for (int i=0; i < cauldrons.length; i++) {
			if (cauldrons[i] == null) {
				cauldrons[i] = new Cauldron(true);
				System.out.println("Cauldron added to inventory.");
				break;
			}		
		}
	}

	// Gets the amount of USABLE cauldrons found in the inventory
	public int getUsableCauldronsCount() {
		int frequency = 0;
		
		for (int i=0; i < cauldrons.length; i++) {
			if (cauldrons[i] != null && cauldrons[i].isUsable() == true) {
				frequency++;
			}
		}
		
		return frequency;
	}

	// Gets the amount of UNUSABLE cauldrons found in the inventory
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

	// checks whether an inventory slot is empty. Used primarily for compacting the inventory.
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
