/* Manages the inventory interface
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

public class InventoryInterface extends JPanel {

	private JTable inventoryTable;
	private JButton btnBack;
	private JLabel lblBalance;
	private Player player;

// Displays the overall design of the Inventory Interface panel
    public InventoryInterface() {


        setLayout(new BorderLayout());

        JLabel title = new JLabel("Inventory",
                SwingConstants.CENTER);
        title.setFont(new Font("Lucida Grande", Font.BOLD, 20));

        add(title, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Slot", "Item", "Status", "Owned"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        inventoryTable = new JTable(model);

        inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(20); // slot
        inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(180); // items
        inventoryTable.getColumnModel().getColumn(2).setPreferredWidth(70); // Status = broken, usable
        inventoryTable.getColumnModel().getColumn(3).setPreferredWidth(40); // Items owned

        inventoryTable.setRowSelectionAllowed(false);
        inventoryTable.setCellSelectionEnabled(false);
        
        inventoryTable.getTableHeader().setReorderingAllowed(false);
        
        add(new JScrollPane(inventoryTable), BorderLayout.CENTER);

        JPanel bottom = new JPanel();

        lblBalance = new JLabel("Balance: 0");

        btnBack = new JButton("Back");

        bottom.add(lblBalance);
        bottom.add(btnBack);

        add(bottom, BorderLayout.SOUTH);

    }
    
/*
* Loads in the Inventory of the Player
* Includes the List of Ingredients and Cauldrons (usable and unusable) a player has
*/
    public void loadInventory() {

    	DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();

        model.setRowCount(0);

        InventoryItem[] items = player.getInventory().items;

        for (int i = 0; i < items.length; i++) {

            InventoryItem item = items[i];

            if (item == null) {
                continue;
            }

            model.addRow(new Object[]{
                i + 1,
                item.getIngredient().getIngredientName(),
                "-",
                item.getQuantity(),
            });
        }
        
        Inventory cauldronInventory = player.getInventory();

        int usable = cauldronInventory.getUsableCauldronsCount();
        int broken = cauldronInventory.getUnusableCauldronsCount();
        
        int slot = model.getRowCount() + 1;
        
        model.addRow(new Object[]{
        	    slot++,
        	    "Cauldron",
        	    "Usable",
        	    usable
        	});

        	model.addRow(new Object[]{
        	    slot++,
        	    "Cauldron",
        	    "Broken",
        	    broken
        	});
        	
    }

// Updates the Players Balance and Inventory
    public void setPlayer(Player player) {
        this.player = player;

        lblBalance.setText("Balance: " + player.getCrystals());

        loadInventory();
    }

// back
    public JButton getBtnBack() {
        return btnBack;
    }
    
    
}
