package MCO2.controller;

import java.util.*;
import MCO2.model.*;

public class Market {
	
	private MarketItems[] slots;
	private int potionsBrewedCount;
	private int concoctionsBrewedSinceLastVisit;
	
	public Market() {
		this.slots = new MarketItems[8];
		this.potionsBrewedCount = 0;
		refreshMarket();
	}
	
	public void refreshMarket() {
		Random rnd = new Random();
		boolean cauldronAppeared = false;

	    for (int i = 0; i < slots.length; i++) {
	    	int randomQty = rnd.nextInt(5) + 1;
	    	int randomCauldron = rnd.nextInt(5)+1;

	    	if (randomCauldron == 5 && !cauldronAppeared) {
	    		slots[i] = new MarketItems(new Cauldron(true), 1);
	    		cauldronAppeared = true;
	    	}
	    	
	    	else {
	    		
	    		slots[i] = new MarketItems(Ingredients.getRandomIngredient(), randomQty);
	    	}

	    }
	}

	public void buyIngredients(Player p) {
		Scanner scn = new Scanner(System.in);
		boolean buying = true;
		int buyTotal = 0;
		
		while (buying) {
			System.out.println("\n------ In stock ------");
			for (int i = 0; i < slots.length; i++) {
				if (slots[i].isSoldOut()) {
					System.out.printf("%d. %-13s | SOLD OUT%n", i + 1, slots[i].getSlotName());
				} else {
					System.out.printf("%d. %-13s | Available: %2d | Price: %4d%n",
				               i + 1, slots[i].getSlotName(), slots[i].getSlotQuantity(), slots[i].getMarketPrice());
				}
			}
		
			System.out.println("\nCurrent balance: " + p.getCrystals());
			System.out.print("Enter item numbers to purchase (e.g. 1,3,5) or 0 to exit: ");
			String input = scn.nextLine().trim();
			buyTotal = 0;
			
			if (input.equals("0")) {
				buying = false;
				break;
			}
			
			String buyChoices[] = input.split(",");
		
			for (String choice : buyChoices) {
				if (choice.trim().isEmpty()) continue;
				
				try {
					int index = Integer.parseInt(choice.trim()) - 1;
					
					if (index < 0 || index >= slots.length) {
						System.out.println(choice.trim() + "' is not a valid item number.");
						continue;
					}
					
					MarketItems chosen = slots[index];
					
					if (chosen.isSoldOut()) {
						System.out.println("That item has already been sold.");
						continue;
					}
					
					int cost = chosen.getMarketPrice() * chosen.getSlotQuantity();
					
					if (p.getCrystals() < cost) {
						System.out.println("Not enough crystals for " + chosen.getSlotName());
						continue;
					}
				
					p.deductCrystals(cost);
					
					if (chosen.hasCauldron()) {
						p.getInventory().addCauldron();
					} else {
						p.getInventory().addIngredient(chosen.item, chosen.getSlotQuantity());
					}
					
					chosen.setSoldOut(true); 
					buyTotal += cost;
					
				} catch (NumberFormatException e) {
					System.out.println(choice.trim() + "' is not a recognizable row slot.");
				}
			}
			
			if (buyTotal > 0) {
				System.out.println("\nItems purchased for " + buyTotal + " crystals.");
				System.out.println("Remaining balance: " + p.getCrystals());
			}
		}
		visitMarket(p);
		
	}
			
	public void sellIngredients(Player p) {
		Scanner scn = new Scanner(System.in);
		boolean selling = true;
		int toSellQty;
		int sellTotal = 0;
		
		while (selling) {
			
			p.getInventory().viewInventory(true); 
			System.out.print("\nEnter item numbers to sell (e.g. 1,3,5) or 0 to exit: ");
			String input = scn.nextLine().trim();
			sellTotal = 0;
			
			if (input.equals("0")) {
				selling = false;
				break;
			}
			
			String sellChoices[] = input.split(",");
			
			for (String choice : sellChoices) {
				if (choice.trim().isEmpty()) continue;
				
				try {
					int index = Integer.parseInt(choice.trim()) - 1;
					
					if (index < 0 || index >= p.getInventory().items.length) {
						System.out.println(choice.trim() + "' is not a valid inventory position.");
						continue;
					}
					
					InventoryItem item = p.getInventory().items[index];
					
					if (item == null) {
						System.out.println("here is no item in that slot.");
						continue;
					}
					
					
					String toSellName = item.getIngredient().getIngredientName();
					if (toSellName.toUpperCase().contains("CAULDRON")) {
						System.out.println("Safety Restriction: Cauldrons cannot be sold in the market!");
						continue;
					}
					
					while (true) {
						System.out.print("How many " + toSellName + " to sell: ");
						String qtyInput = scn.nextLine().trim();
						
						try {
							toSellQty = Integer.parseInt(qtyInput);
							if (toSellQty < 0) {
								System.out.println("Quantity must be at least 0."); 
							} else if (toSellQty > item.getQuantity()) {
								System.out.println("You only have " + item.getQuantity() + ".");
							} else {
								break;
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid numeric quantity entry.");
						}
					}
					
					int unitSellValue = item.getIngredient().getSellingPrice();
					sellTotal += toSellQty * unitSellValue;
					
					p.getInventory().removeIngredient(index, toSellQty);
					
				} catch (NumberFormatException e) {
					System.out.println(choice.trim() + "' is not a recognizable index format choice.");
				}
			}
			
			if (sellTotal > 0) {
				p.addCrystals(sellTotal);
				p.getInventory().compactInventory();
				System.out.println("\nItems sold successfully for " + sellTotal + " crystals.");
				System.out.println("New balance: " + p.getCrystals());
			}
		}
		visitMarket(p);
		
	}
	
	public void incrementBrewCount() {
		this.concoctionsBrewedSinceLastVisit++;
	}
	
	public void exitMarket() {
		System.out.println("Come again sometime!");
	}
	
	public void visitMarket(Player p) {
		Scanner scn = new Scanner(System.in);
		
		if(this.concoctionsBrewedSinceLastVisit >= 3) {
			refreshMarket();
			System.out.println("Notice: Market items have refreshed because you brewed"
					+ this.concoctionsBrewedSinceLastVisit + " concoctions since your last visit!");
		
			this.concoctionsBrewedSinceLastVisit = 0;
		}else {
			this.concoctionsBrewedSinceLastVisit = 0;
		}
		
		int marketChoice;
		
		System.out.println("\nWelcome to the market! How can I help?");
		System.out.println("1. Buy Ingredients");
		System.out.println("2. Sell Ingredients");
		System.out.println("3. Exit Market");
		System.out.print("Choose an option: ");
		
		try {
			marketChoice = Integer.parseInt(scn.nextLine().trim());
			
			switch (marketChoice) {
				case 1: buyIngredients(p); break;
				case 2: sellIngredients(p); break;
				case 3: exitMarket(); break;
				default: 
					System.out.println("Invalid choice configuration option row.");
					visitMarket(p);
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid number configuration choice entry.");
			visitMarket(p);
		}

	}
	
	public MarketItems[] getSlots() {
	    return slots;
	}
	
	// For buying itmes in the interface
	public boolean buySlot(Player p, int slotIndex) {

	    if (slotIndex < 0 || slotIndex >= slots.length) {
	        return false;
	    }

	    MarketItems chosen = slots[slotIndex];

	    if (chosen.isSoldOut()) {
	        return false;
	    }

	    int cost = chosen.getMarketPrice() * chosen.getSlotQuantity();

	    if (p.getCrystals() < cost) {
	        return false;
	    }

	    p.deductCrystals(cost);

	    if (chosen.hasCauldron()) {
	        p.getInventory().addCauldron();
	    } else {
	        p.getInventory().addIngredient(chosen.item, chosen.getSlotQuantity());
	    }

	    chosen.setSoldOut(true);

	    return true;
	}
	
	//For selling items in the interface
	public boolean sellSlot(Player p, int inventoryIndex, int qty) {
		
		if (inventoryIndex < 0 || inventoryIndex >= p.getInventory().items.length) {
		    return false;
		}

	    InventoryItem item = p.getInventory().items[inventoryIndex];

	    if (item == null) {
	        return false;
	    }

	    if (qty <= 0 || qty > item.getQuantity()) {
	        return false;
	    }

	    int money = qty * item.getIngredient().getSellingPrice();

	    p.getInventory().removeIngredient(inventoryIndex, qty);
	    p.addCrystals(money);

	    return true;
	}
}
