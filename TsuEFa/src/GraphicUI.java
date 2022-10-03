import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;


public class GraphicUI extends JFrame {
    //icon list
    private ImageIcon rockIcon, scissorIcon, paperIcon, pcIcon, userIcon;

    private final String ROCK_ICON = "images/rock.png";
    private final String SCISSORS_ICON = "images/scissors.png";
    private final String PAPER_ICON = "images/paper.png";
    private final String PC_ICON = "images/pc.png";
    private final String USER_ICON = "images/user.png";

    //text messages list
    private final String PLAYER = "Игрок";
    private final String PC = "Компьютер";
    private final String INITIAL_LABEL = "Нажмите кнопку";
    private final String INITIAL_TURN = "Ход номер %d";
    private final String WHAT_IS_YOUR_NAME = "Как вас зовут?";
    private final String PUSH_BUTTON = "Нажмите, чтобы сделать ход";
    private final String DRAW = "Ничья";
    private final String PLAYER_WON = "Выиграл ";
    private final String PC_WON = "Выиграл компьютер!";

    //list of the names that activate cheater mode
    private final String [] CHEATER_NAMES = {"читер", "читерок", "cheater", "читак"};

    private Party party;
    private String playerName;

    //GUI elements
    private JLabel leftLbl, rightLbl, bottomLbl, playerLbl, pcLbl, resultLbl;
    private JButton rockButton, scissorsButton, paperButton;
    private JToggleButton pcButton;


    public GraphicUI(String title) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container container = getContentPane();

        //GUI elements initializing
        //================================================================================
        leftLbl = new JLabel(PLAYER);
        rightLbl = new JLabel(PC);
        resultLbl = new JLabel(INITIAL_LABEL);
        bottomLbl = new JLabel(String.format(INITIAL_TURN, 0));

        Action action = new ButtonAction();

        rockIcon = new ImageIcon(ROCK_ICON);
        scissorIcon = new ImageIcon(SCISSORS_ICON);
        paperIcon = new ImageIcon(PAPER_ICON);
        pcIcon = new ImageIcon(PC_ICON);
        userIcon = new ImageIcon(USER_ICON);

        playerLbl = new JLabel();
        pcLbl = new JLabel();
        playerLbl.setIcon(userIcon);
        pcLbl.setIcon(pcIcon);

        playerLbl.setPreferredSize(new Dimension(200, 200));
        pcLbl.setPreferredSize(new Dimension(200, 200));


        rockButton = createButton(action, rockIcon);
        rockButton.setName("Rock");
        scissorsButton = createButton(action, scissorIcon);
        scissorsButton.setName("Scissors");
        paperButton = createButton(action, paperIcon);
        paperButton.setName("Paper");

        pcButton = new JToggleButton(pcIcon);
        pcButton.setPreferredSize(new Dimension(200, 200));
        pcButton.setSelected(true);
        pcButton.setEnabled(false);

        GroupLayout layout = new GroupLayout(container);
        container.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(rockButton)
                        .addComponent(leftLbl)
                        .addComponent(playerLbl)
                        .addComponent(resultLbl)
                        .addComponent(bottomLbl))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(scissorsButton)
                        .addComponent(rightLbl)
                        .addComponent(pcLbl))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(paperButton)));


        layout.linkSize(SwingConstants.HORIZONTAL, paperButton, scissorsButton, rockButton);
        layout.linkSize(SwingConstants.VERTICAL, paperButton, scissorsButton, rockButton);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(rockButton)
                        .addComponent(scissorsButton)
                        .addComponent(paperButton))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(rightLbl)
                        .addComponent(leftLbl))
                .addComponent(resultLbl)
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(playerLbl)
                        .addComponent(pcLbl))
                .addComponent(bottomLbl));
        //================================================================================

        //Get players name
        playerName = getName();
        if (playerName==null||playerName.length()==0) {
            playerName = PLAYER;
        }

        //Check for cheater
        boolean cheaterFlag = cheaterCheck(playerName);

        //start new party
        party = new Party(cheaterFlag);

        //renew GUI elements
        lblsRenew();

        pack();
        setSize(670, 500);
        setVisible(true);
    }

    //This method put fresh party data into the GUI forms
    private void lblsRenew() {
        int wins = party.getPlayersWins();
        int loses = party.getCompWins();
        int turn = party.getTurnNumber();
        leftLbl.setText(String.format("%s : %d", playerName, wins));
        rightLbl.setText(String.format("%s : %d", PC, loses));
        bottomLbl.setText(String.format(INITIAL_TURN, turn));
    }

    //Check for cheater
    private boolean cheaterCheck(String name) {
        boolean cheater = false;
        name = name.toLowerCase();
        for (int i = 0; i< CHEATER_NAMES.length; i++) {
            if (name.equals(CHEATER_NAMES[i])) {cheater = true;}
        }
        return cheater;
    }

    //Name input window
    public String getName() {
        String name = JOptionPane.showInputDialog(WHAT_IS_YOUR_NAME);
        return name;
    }

    //Set button method
    private JButton createButton(Action action,ImageIcon icon) {
        JButton button = new JButton(action);
        button.setIcon(icon);
        button.setFocusPainted(false);
        Dimension buttonDimension = new Dimension(200, 200);
        button.setPreferredSize(buttonDimension);
        return  button;
    }

    //Action inner class
    private class ButtonAction extends AbstractAction {
        ButtonAction() {
            putValue(SHORT_DESCRIPTION, PUSH_BUTTON);
        }
        public void actionPerformed (ActionEvent e) {

            //Sets player choice label according player's choice
            TsuEFa choice = TsuEFa.БУМАГА; //this is default set that can be changed later
            playerLbl.setIcon(paperIcon);

            JButton btn = (JButton) e.getSource();
            switch (btn.getName()) {
                case "Rock": {
                    choice = TsuEFa.КАМЕНЬ;
                    playerLbl.setIcon(rockIcon);
                    break;
                }
                case "Scissors": {
                    choice = TsuEFa.НОЖНИЦЫ;
                    playerLbl.setIcon(scissorIcon);
                    break;
                }
            }

            //put result into result label
            int result = party.turn(choice);
            String message;
            message = result == 0 ? DRAW :
                    result == 1 ? (PLAYER_WON + playerName + "!") : PC_WON;
            resultLbl.setText(message);

            //draws the icon depending of the computer choice
            TsuEFa computerChoice = party.getComputerChoice();
            switch (computerChoice) {
                case КАМЕНЬ: {
                    pcLbl.setIcon(rockIcon);
                    break;
                }
                case НОЖНИЦЫ: {
                    pcLbl.setIcon(scissorIcon);
                    break;
                }
                case БУМАГА: {
                    pcLbl.setIcon(paperIcon);
                }
            }

            //refresh labels
            lblsRenew();
        }
    }
}
