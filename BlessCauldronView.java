package MCO2.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import MCO2.controller.*;
import MCO2.model.*;

public class BlessCauldronView extends JPanel {

    private JTable table;
    private JButton btnBless;
    private JButton btnBack;
    private JLabel lblBalance;

    private Player player;

    public BlessCauldronView() {

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("Welcome to the Cauldron Clinic!");
        lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("If you are in need of blessing your cauldrons,");
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitle2 = new JLabel("that, I shall assist.");
        lblSubtitle2.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(lblTitle);
        topPanel.add(lblSubtitle);
        topPanel.add(lblSubtitle2);
        topPanel.add(Box.createVerticalStrut(10));

        add(topPanel, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{ "Slot", "Cauldrons", "Amount Broken", "Blessing Price", "Quantity" }, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;   // Only the Quantity Cell is editable
            }
        };

        table = new JTable(model);

        table.getColumnModel().getColumn(0).setPreferredWidth(20);  // Slot
        table.getColumnModel().getColumn(1).setPreferredWidth(180); // Cauldrons
        table.getColumnModel().getColumn(2).setPreferredWidth(80);  // Broken Cauldrons Owned
        table.getColumnModel().getColumn(3).setPreferredWidth(40); // Bless Price
        table.getColumnModel().getColumn(4).setPreferredWidth(40); // Quantity
        
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        
        table.getTableHeader().setReorderingAllowed(false);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();

        lblBalance = new JLabel("Balance: 0");

        btnBless = new JButton("Bless");

        btnBack = new JButton("Back");

        bottom.add(lblBalance);
        bottom.add(btnBless);
        bottom.add(btnBack);

        add(bottom, BorderLayout.SOUTH);
        
        btnBless.addActionListener(e -> BlessSelected());

    }

    public void loadInventory() {

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        int broken = player.getInventory().getUnusableCauldronsCount();

        model.addRow(new Object[]{
            1,
            "Damaged Cauldron",
            broken,
            Cauldron.blessPrice,
            0
        });
    }

    private void BlessSelected() {

        try {

            int qty = Integer.parseInt(table.getValueAt(0, 4).toString());

            BlessCauldron bless = new BlessCauldron();

            if (bless.blessCauldrons(player, qty)) {
                JOptionPane.showMessageDialog(this,
                        "Cauldron(s) blessed successfully!");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid quantity or insufficient crystals.");
            }

            lblBalance.setText("Balance: " + player.getCrystals());
            loadInventory();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid quantity.");
        }
    }
    
    public void setPlayer(Player player) {
        this.player = player;

        lblBalance.setText("Balance: " + player.getCrystals());

        loadInventory();
    }
    
    public JButton getBtnBack() {
        return btnBack;
    }
    
    
}