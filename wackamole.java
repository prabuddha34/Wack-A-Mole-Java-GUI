import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class wackamole extends JFrame {
    JButton[] board = new JButton[9];
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    ImageIcon enemyCon, playerCon;
    JButton currMoleTile, currPlantTile;
    Timer plantTimer, playerTimer;
    int score = 0;
    Random random = new Random();

    wackamole() {

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 650);
        this.setTitle("Wack A Mole");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());


        Image plantImg = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        enemyCon = new ImageIcon(plantImg.getScaledInstance(150, 150, Image.SCALE_SMOOTH));

        Image moleImg = new ImageIcon(getClass().getResource("./monty.png")).getImage();
        playerCon = new ImageIcon(moleImg.getScaledInstance(150, 150, Image.SCALE_SMOOTH));


        textLabel.setText("Score : " + score);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel, BorderLayout.CENTER);
        this.add(textPanel, BorderLayout.NORTH);


        boardPanel.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            tile.setFocusable(false);
            boardPanel.add(tile);

            tile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clicked = (JButton) e.getSource();

                    if (clicked == currPlantTile) {
                        playerTimer.stop();
                        plantTimer.stop();
                        textLabel.setText("Game Over " + score);
                    } else if (clicked == currMoleTile) {
                        score += 10;
                        textLabel.setText("Score : " + score);
                        currMoleTile.setIcon(null);
                        currMoleTile = null;
                    }
                }
            });
        }


        playerTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currMoleTile != null) {
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }

                int num = random.nextInt(9);
                JButton tile = board[num];


                if (tile == currPlantTile) return;

                currMoleTile = tile;
                currMoleTile.setIcon(playerCon);
            }
        });

        plantTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currPlantTile != null) {
                    currPlantTile.setIcon(null);
                    currPlantTile = null;
                }

                int num = random.nextInt(9);
                JButton tile = board[num];


                if (tile == currMoleTile) return;

                currPlantTile = tile;
                currPlantTile.setIcon(enemyCon);
            }
        });

        playerTimer.start();
        plantTimer.start();

        this.add(boardPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new wackamole();
    }
}
