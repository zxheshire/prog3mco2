package MCO2.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import MCO2.controller.*;
import MCO2.model.*;

public class BuyView extends JPanel {

    private JTable table;
    private JButton btnBuy;
    private JButton btnBack;
    private JLabel lblBalance;

    private Market market;
    private Player player;

    public BuyView(Market market) {

        this.market = market;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Market",
                SwingConstants.CENTER);
        title.setFont(new Font("Lucida Grande", Font.BOLD, 20));

        add(title, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{ "Slot", "Items", "Available", "Price", "Buy" }, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;   // Only the Buy checkbox is editable
            }

            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 4) {
                    return Boolean.class; // Makes the last column display checkboxes
                }
                return Object.class;
            }
        };

        table = new JTable(model);

        table.getColumnModel().getColumn(0).setPreferredWidth(20);  // Slot
        table.getColumnModel().getColumn(1).setPreferredWidth(180); // Items
        table.getColumnModel().getColumn(2).setPreferredWidth(40);  // Available
        table.getColumnModel().getColumn(3).setPreferredWidth(80); // Price
        table.getColumnModel().getColumn(4).setPreferredWidth(40); // Buy 
        
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        table.getTableHeader().setReorderingAllowed(false);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();

        lblBalance = new JLabel("Balance: 0");

        btnBuy = new JButton("Buy");

        btnBack = new JButton("Back");

        bottom.add(lblBalance);
        bottom.add(btnBuy);
        bottom.add(btnBack);

        add(bottom, BorderLayout.SOUTH);

        loadMarket();

        btnBuy.addActionListener(e -> buySelected());
    }

    public void loadMarket() {

        DefaultTableModel model =
                (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        MarketItems[] slots = market.getSlots();

        for (int i = 0; i < slots.length; i++) {

            MarketItems item = slots[i];

            if (item.isSoldOut()) {
                model.addRow(new Object[]{ i + 1, "SOLD OUT", "-", "-", false 
                });
            } 
            else {
                model.addRow(new Object[]{ i + 1, item.getSlotName(), item.getSlotQuantity(), item.getMarketPrice(), false
                });
            }
        }
    }

    private void buySelected() {

        boolean boughtSomething = false;

        for (int row = 0; row < table.getRowCount(); row++) {

            Boolean checked = (Boolean) table.getValueAt(row, 4);

            if (checked != null && checked) {
                if (market.buySlot(player, row)) {
                    boughtSomething = true;
                }
            }
        }

        if (boughtSomething) {
            JOptionPane.showMessageDialog(this, "Purchase successful!");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid quantity or insufficient crystals.");
        }

        lblBalance.setText("Balance: " + player.getCrystals());
        loadMarket();
    }
    
    public void setPlayer(Player player) {
        this.player = player;

        lblBalance.setText(
            "Balance: " + player.getCrystals());
    }
    
    public JButton getBtnBack() {
        return btnBack;
    }
    
    
}