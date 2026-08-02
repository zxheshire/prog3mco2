/* Manages the Sell Ingredients interface
* @author Ysha Nacpil
* @version 1.0
* @since 2026
*/

package MCO2.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import MCO2.controller.*;
import MCO2.model.*;

public class SellView extends JPanel {

    private JTable table;
    private JButton btnSell;
    private JButton btnBack;
    private JLabel lblBalance;

    private Market market;
    private Player player;

/*
* Displays the overall Sell Ingredients Panel
*/
    public SellView(Market market) {

        this.market = market;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Market",
                SwingConstants.CENTER);
        title.setFont(new Font("Lucida Grande", Font.BOLD, 20));

        add(title, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{ "Slot", "Ingredients", "Owned", "Selling Price", "Quantity", "Index" }, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;   // Only the Quantity Cell is editable
            }

          
        };

        table = new JTable(model);

        table.getColumnModel().getColumn(0).setPreferredWidth(20);  // Slot
        table.getColumnModel().getColumn(1).setPreferredWidth(180); // Ingredients
        table.getColumnModel().getColumn(2).setPreferredWidth(40);  // Ingredients Owned
        table.getColumnModel().getColumn(3).setPreferredWidth(40); // Sell Price
        table.getColumnModel().getColumn(4).setPreferredWidth(40); // Quantity
        
        //to hide the Item Index
        table.getColumnModel().getColumn(5).setMinWidth(0);
        table.getColumnModel().getColumn(5).setMaxWidth(0);
        table.getColumnModel().getColumn(5).setPreferredWidth(0);
        
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        table.getTableHeader().setReorderingAllowed(false);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();

        lblBalance = new JLabel("Balance: 0");

        btnSell = new JButton("Sell");

        btnBack = new JButton("Back");

        bottom.add(lblBalance);
        bottom.add(btnSell);
        bottom.add(btnBack);

        add(bottom, BorderLayout.SOUTH);

       

        btnSell.addActionListener(e -> SellSelected());
    }

/*
* Loads the player's available ingredients that can be sold
*/
    public void loadInventory() {

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        InventoryItem[] items = player.getInventory().items;

        for (int i = 0; i < items.length; i++) {

            InventoryItem item = items[i];

            if (item == null || item.getQuantity() <= 0) {
                continue;
            }

            model.addRow(new Object[]{
                i + 1,
                item.getIngredient().getIngredientName(),
                item.getQuantity(),
                item.getIngredient().getSellingPrice(),
                0,
                i
            });
        }
    }

/*
* Sells the Selected or Entered quantity of the Ingredient
* Checks if entered quantity is valid or not
* Updates the player's balance once sold
*/
    private void SellSelected() {

        boolean SoldSomething = false;

        for (int row = 0; row < table.getRowCount(); row++) {

        	try {
        	    int qty = Integer.parseInt(table.getValueAt(row, 4).toString());

        	    if (qty > 0) {
        	    	int inventoryIndex = (Integer) table.getValueAt(row, 5);

        	    	if (market.sellSlot(player, inventoryIndex, qty)) {
        	    	    SoldSomething = true;
        	    	}
        	    }

        	} catch (NumberFormatException e) {
        	    JOptionPane.showMessageDialog(this,
        	            "Please enter a valid quantity.");
        	    return;
        	}
        }

        if (SoldSomething) {
        	player.getInventory().compactInventory();
            JOptionPane.showMessageDialog(this, "Item Sold successful!");
        } else {
            JOptionPane.showMessageDialog(this, "No valid items were entered.");
        }

        lblBalance.setText("Balance: " + player.getCrystals());
        loadInventory();
    }

    // Updates player's balance and inventory
    public void setPlayer(Player player) {
        this.player = player;

        lblBalance.setText("Balance: " + player.getCrystals());

        loadInventory();
    }

    //back
    public JButton getBtnBack() {
        return btnBack;
    }
    
    
}
