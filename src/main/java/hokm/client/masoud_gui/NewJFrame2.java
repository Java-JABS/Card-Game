package hokm.client.masoud_gui;
import hokm.Card;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class NewJFrame2 extends JPanel {

    private int cardWidth = 80;
    private JPanel right =new JPanel();
    private JPanel left = new JPanel();
    private JPanel up = new JPanel();
    private JPanel down = new JPanel();
    private JPanel center = new JPanel();
    private JPanel profile = new JPanel();
    private JPanel cardDeck = new JPanel();
    private JLabel[] profiles = {new JLabel(),new JLabel(),new JLabel(),new JLabel()};
    private JLabel[] names = {new JLabel(),new JLabel(),new JLabel(),new JLabel()};
    private JLabel[] cards = {new JLabel(),new JLabel(),new JLabel(),new JLabel()};
    public NewJFrame2() {
        setLayout(new BorderLayout());
        initComponents();
    }

    public ImageIcon getCardIcon(Card card){
        return new ImageIcon(new ImageIcon("pictures/"+card.suit()+"/"+card.value()).getImage().getScaledInstance(80, -1,Image.SCALE_SMOOTH ));
    }
    public void setCard(ArrayList<Card> list, int index){
        for(int i = 0;i < list.size();i++){
            cards[(i + (list.size() - index))%4].setIcon(getCardIcon(list.get(i)));
        }
    }
    public void setName(ArrayList<String> list, int index){
        for(int i = 0;i < list.size();i++){
            names[(i + (list.size() - index))%4].setText(list.get(i));
        }
    }
    public void setPic_toProfile(){
        for(JLabel label : profiles){
            label.setIcon(new ImageIcon(new ImageIcon("pictures/Person/person.png").getImage().getScaledInstance(80, -1, Image.SCALE_SMOOTH)));
        }
    }


    private void initComponents() {
        ArrayList<String> l = new ArrayList<>();
        l.add("Javid");
        l.add("Abbasi");
        l.add("Boveyri");
        l.add("Masoud");

        GridBagConstraints gridBagConstraints;

        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        right.setLayout(new GridBagLayout());

        names[1].setText("Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        right.add(names[1], gridBagConstraints);

        profiles[1].setPreferredSize(new Dimension(80, 80));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        right.add(profiles[1], gridBagConstraints);

        cards[1].setPreferredSize(new Dimension(80, 120));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        right.add(cards[1], gridBagConstraints);

        add(right, BorderLayout.LINE_END);

        left.setLayout(new GridBagLayout());

        names[3].setText("name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        left.add(names[3], gridBagConstraints);

        cards[3].setPreferredSize(new Dimension(80, 120));
        cards[3].setAlignmentY(1.5F);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        left.add(cards[3], gridBagConstraints);

        profiles[3].setPreferredSize(new Dimension(80, 80));
        profiles[3].setAlignmentY(1.5F);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        left.add(profiles[3], gridBagConstraints);

        add(left, BorderLayout.LINE_START);

        up.setLayout(new GridBagLayout());

        profiles[2].setPreferredSize(new Dimension(80, 80));

        up.add(profiles[2], new GridBagConstraints());

        names[2].setText("Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        up.add(names[2], gridBagConstraints);

        cards[2].setPreferredSize(new Dimension(80, 120));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 2;
        up.add(cards[2], gridBagConstraints);


        add(up, BorderLayout.PAGE_START);

        down.setLayout(new GridBagLayout());

        profile.setLayout(new GridBagLayout());

        cards[0].setPreferredSize(new Dimension(80, 120));
        profile.add(cards[0], new GridBagConstraints());

        names[0].setText("Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        profile.add(names[0], gridBagConstraints);

        profiles[0].setPreferredSize(new Dimension(80, 80));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        profile.add(profiles[0], gridBagConstraints);

        down.add(profile, new GridBagConstraints());

        cardDeck.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        down.add(cardDeck, gridBagConstraints);

        add(down, BorderLayout.PAGE_END);

        center.setLayout(new BorderLayout());
        add(center, BorderLayout.CENTER);
        setPic_toProfile();
        setName(l, 3);
        //pack();
    }
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame2().setVisible(true);
            }
        });
    }

}