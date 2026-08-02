/* Manages the cauldron object
* Sets the attributes of the cauldron upon object creation
* @aurthor Ysha Nacpil
* @version 1.0
* @since 2026
*/

package MCO2.model;

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
