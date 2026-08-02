package MCO1;

import java.util.Random;

public class Ingredients {
	
	public String ingredientName;
    private int buyingPrice;
	private int sellingPrice;

	
	private static Ingredients ingList[] = new Ingredients[14];
	
	public Ingredients() {
		// empty constructor; allows for class usability
	}
	
	public Ingredients(String name, int buy, int sell) {
		this.ingredientName = name;
		this.buyingPrice = buy;
		this.sellingPrice = sell;
	}
	
	public String getIngredientName() {
		return this.ingredientName;
	}
	
	public int getBuyingPrice() {
		return this.buyingPrice;
	}
	
	public int getSellingPrice() {
		return this.sellingPrice;
	}
	
	public static void initializeIngredients() {
		// mixture ingredients
		ingList[0] = new Ingredients("Strawberry", 125, 25);
		ingList[1] = new Ingredients("Orange", 80, 40);
		ingList[2] = new Ingredients("Lemon" , 50, 25);
		ingList[3] = new Ingredients("Banana", 75, 50);
		ingList[4] = new Ingredients("Mango", 90, 30);
		ingList[5] = new Ingredients("Pineapple", 240, 120);
		ingList[6] = new Ingredients("Kiwi", 200, 80);			
		ingList[7] = new Ingredients("Blueberry", 120, 20);
		ingList[8] = new Ingredients("Coconut", 180, 90);
						
		// mixture bases
		ingList[9] = new Ingredients("Syrup Base", 50, 10);
		ingList[10] = new Ingredients("Bubble Base", 80, 20);
		ingList[11] = new Ingredients("Perfume Base", 250, 50);
		ingList[12] = new Ingredients("Milk Base", 60, 15);
		ingList[13] = new Ingredients("Lotion Base", 150, 25);
		
	}
	
	public static Ingredients getRandomIngredient() {
		Random rnd = new Random();
		
		String[] options = {"STRAWBERRY", "ORANGE", "LEMON", "BANANA", "MANGO", "PINEAPPLE","KIWI","BLUEBERRY", "COCONUT", "SYRUP BASE", "BUBBLE BASE", "PERFUME BASE", "MILK BASE", "LOTION BASE"};
		int index = rnd.nextInt(options.length);
		
		return new Ingredients(options[index], 100, 30);
	}
}
