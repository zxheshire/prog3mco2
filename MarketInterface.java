package MCO2.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class MarketInterface extends JPanel {

    private static final long serialVersionUID = 1L;

    private JButton btnBuyIngredients;
    private JButton btnSellIngredients;
    private JButton btnExitMarket;
    

    /**
     * Market Interface
     */
    public MarketInterface() {
        setLayout(null);

        JLabel lblTitle = new JLabel("Welcome to the Market!");
        lblTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblTitle.setBounds(174, 70, 254, 33);
        add(lblTitle);

        JLabel lblHowCanIHelp = new JLabel("How can I help?");
        lblHowCanIHelp.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
        lblHowCanIHelp.setBounds(229, 101, 138, 33);
        add(lblHowCanIHelp);

        btnBuyIngredients = new JButton("Buy Ingredients");
        btnBuyIngredients.setBounds(216, 159, 138, 33);
        add(btnBuyIngredients);

        btnSellIngredients = new JButton("Sell Ingredients");
        btnSellIngredients.setBounds(216, 204, 138, 33);
        add(btnSellIngredients);

        btnExitMarket = new JButton("Exit Market");
        btnExitMarket.setBounds(216, 249, 138, 33);
        add(btnExitMarket);
    }

    public JButton getBtnBuyIngredients() {
        return btnBuyIngredients;
    }

    public JButton getBtnSellIngredients() {
        return btnSellIngredients;
    }

    public JButton getBtnExitMarket() {
        return btnExitMarket;
    }
}