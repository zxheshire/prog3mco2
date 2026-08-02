/* Manages the items stored in the inventory.
* @author Ysha Nacpil
* @version 1.0
* @since 2026
*/
package MCO2.model;

public class InventoryItem {

	// Each item is an ingredient from the Ingredients class
	private Ingredients ingredient;
	private int quantity;
	
	{
		System.out.println("Inventory updated.");
	}
	
	public InventoryItem() {
		// empty constructor; for object creation
	}
	
	public InventoryItem(Ingredients ingredient, int quantity) {
		this.ingredient = ingredient;
		this.quantity = quantity;
	}
	
	public Ingredients getIngredient() {
		return this.ingredient;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void addQuantity(int amount) {
		this.quantity+=amount;
	}
	
	public void deductQuantity(int amount) {
		this.quantity-=amount;
	}
	
}
