/* Manages the blessing of cauldrons (turning unusuable cauldrons to usable ones at a cost)
* @author Ysha Nacpil
* @version 1.0
* @since 2026
*/

package MCO2.controller;

import java.util.*;
import MCO2.model.*;

public class BlessCauldron {
	
	private int numUnusable;
	private int numUsable;

/*
* This code manages the menu for this class.
* It's the code where the broken cauldron gets blessed.
*/
	public void visitMenu(Player p) {
		boolean blessing = true;
		Scanner scn = new Scanner(System.in);
		numUnusable = p.getInventory().getUnusableCauldronsCount();
		
		System.out.println("\nWelcome to the Cauldron Clinic!");
		System.out.println("If you are in need of blessing your cauldrons. That, I shall assist.");
		
		while (blessing) {
			numUnusable = p.getInventory().getUnusableCauldronsCount();
			
			if(numUnusable == 0) {
				System.out.println("All cauldrons are fully functional! Exiting Bless Cauldron...");
				blessing = false;
				break;
			}
			
			System.out.println("\n----------- Available Damaged Cauldrons -----------");
			System.out.printf("%-3s %-20s | %5s | %10s%n","#", "Item", "Qty", "Bless Price");
			System.out.println("---------------------------------------------------");

			System.out.printf("%-3d %-20s | %5d | %10d%n", 1, "Damaged Cauldron",
					   numUnusable,
					   Cauldron.blessPrice
			           );

			System.out.println("---------------------------------------------------");
			 
			System.out.print("How many cauldrons are you blessing?: ");
			int blessCount = scn.nextInt();
			scn.nextLine();
			int blessed = 0;
			int totalCost = 0;

			
			if(blessCount == 0) {
				System.out.println("Exiting Cauldron Clinic....");
				blessing = false;
				break;
			}
			
			if(blessCount < 0 || blessCount > numUnusable) {
				System.out.println("Invalid Quantity. You cannot bless that amount");
				continue;
			}
			
			totalCost = blessCount * Cauldron.blessPrice;
			
			if(p.getCrystals() < totalCost) {
				System.out.println("Transaction Denied: You don't have enough funds to bless the cauldron/s");
				continue;
			}
			 
			for (int i = 0; i < p.getInventory().cauldrons.length && blessed < blessCount; i++) {
				if (p.getInventory().cauldrons[i] != null && !p.getInventory().cauldrons[i].isUsable()) {
					p.getInventory().cauldrons[i].setUsable(true);
				    blessed++;
				    totalCost = blessed * Cauldron.blessPrice;
				}
			}
			 
			p.deductCrystals(totalCost);
			
			System.out.println("\n" + blessed + " cauldrons blessed for " + totalCost + " crystals.");
			System.out.println("Remaining balance: " + p.getCrystals());
			
			numUsable = p.getInventory().getUsableCauldronsCount();
			System.out.println("You now have " + numUsable + " usable cauldrons.");
		}
		
		 
	}

/*
* This code is for the Bless Cauldron Interface
* Searches the inventory for a broken cauldron then blesses them according to the inputted quantity.
*/
	  public boolean blessCauldrons(Player p, int qty) {

	        int broken = p.getInventory().getUnusableCauldronsCount();

	        if (qty <= 0 || qty > broken) {
	            return false;
	        }

	        int totalCost = qty * Cauldron.blessPrice;

	        if (p.getCrystals() < totalCost) {
	            return false;
	        }

	        int blessed = 0;

	        for (int i = 0; i < p.getInventory().cauldrons.length && blessed < qty; i++) {
	            if (p.getInventory().cauldrons[i] != null &&
	                !p.getInventory().cauldrons[i].isUsable()) {

	                p.getInventory().cauldrons[i].setUsable(true);
	                blessed++;
	            }
	        }

	        p.deductCrystals(totalCost);
	        return true;
	    }

}
