package ui;

import gameengine.GameEngine;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import models.Brick;
import models.Coin;
import models.DeadPlayer;
import models.LifePack;
import models.MapObject;
import models.Player;
import models.Stone;
import models.Water;

/**
 *
 * @author AMILA
 */

public class GameMapUI extends JFrame implements Observer {

    private JPanel squaresPanel;
    private MapObject[][] map;
    private Player[] players;
    private JTable table;

    public GameMapUI() throws IOException 
    {
        map = new MapObject[GameEngine.SIZE][GameEngine.SIZE];
        players = null;
        initComponents();
    }

    private void createSquares() throws IOException 
    {
        LayoutManager layout = new GridLayout(GameEngine.SIZE, GameEngine.SIZE);
        squaresPanel = new JPanel();
        squaresPanel.setBorder(new EmptyBorder(2, 2, 2, 0));
        squaresPanel.setLayout(layout);
        for (int j = 0; j < GameEngine.SIZE; j++) 
        {
            for (int i = 0; i < GameEngine.SIZE; i++) 
            {
                JPanel squarePanel = getPanel(i, j);
                squaresPanel.add(squarePanel);
            }
        }
    }

    private void createTable() 
    {
        String[] playerColours = {"Blue", "Red", "Green", "Purple", "Yello"};
        if (players == null) 
        {
            String columnNames[] = {"ID", "Points $", "Coins $", "Life %"};
            Object rowData[][] = {{"Player ID", "Points $", "Coins $", "Life %"},
                {"P0", "0", "0", "0"},
                {"P1", "0", "0", "0"},
                {"P2", "0", "0", "0"},
                {"P3", "0", "0", "0"},
                {"P4", "0", "0", "0"}};
            table = new NonEditableTable(rowData, columnNames);
        } else 
        {
            for (int i = 0; i < 5; i++) 
            {
                if (players[i] != null) 
                {
                    Player p = players[i];
                    if (p.isOpponent()) 
                    {
                        table.setValueAt("P" + i + " [" + playerColours[i] + "]", i + 1, 0);
                    } else 
                    {
                        table.setValueAt("P" + i + " [User - " + playerColours[i] + "]", i + 1, 0);
                    }

                    table.setValueAt(p.getPoints(), i + 1, 1);
                    table.setValueAt(p.getCoins(), i + 1, 2);
                    table.setValueAt(p.getHealth(), i + 1, 3);
                }
            }
        }
    }

    private JPanel getPanel(int x, int y) 
    {
        String freeSpace = "./src/resources/floor.png";
        String water = "./src/resources/water.png";
        String stone = "./src/resources/stone.png";
        String brick = "./src/resources/brick.png";
        String coinPile = "./src/resources/coins.png";
        String lifePack = "./src/resources/health.png";
        String p0_north = "./src/resources/tankb1_n.png";
        String p0_north_fire = "./src/resources/tankb1_nf.png";
        String p0_east = "./src/resources/tankb1_e.png";
        String p0_east_fire = "./src/resources/tankb1_ef.png";
        String p0_south = "./src/resources/tankb1_s.png";
        String p0_south_fire = "./src/resources/tankb1_sf.png";
        String p0_west = "./src/resources/tankb1_w.png";
        String p0_west_fire = "./src/resources/tankb1_wf.png";
        String p1_north = "./src/resources/tankf1_n.png";
        String p1_north_fire = "./src/resources/tankf1_nf.png";
        String p1_east = "./src/resources/tankf1_e.png";
        String p1_east_fire = "./src/resources/tankf1_ef.png";
        String p1_south = "./src/resources/tankf1_s.png";
        String p1_south_fire = "./src/resources/tankf1_sf.png";
        String p1_west = "./src/resources/tankf1_w.png";
        String p1_west_fire = "./src/resources/tankf1_wf.png";
        String p2_north = "./src/resources/tankg1_n.png";
        String p2_north_fire = "./src/resources/tankg1_nf.png";
        String p2_east = "./src/resources/tankg1_e.png";
        String p2_east_fire = "./src/resources/tankg1_ef.png";
        String p2_south = "./src/resources/tankg1_s.png";
        String p2_south_fire = "./src/resources/tankg1_sf.png";
        String p2_west = "./src/resources/tankg1_w.png";
        String p2_west_fire = "./src/resources/tankg1_wf.png";
        String p3_north = "./src/resources/tankp1_n.png";
        String p3_north_fire = "./src/resources/tankp1_nf.png";
        String p3_east = "./src/resources/tankp1_e.png";
        String p3_east_fire = "./src/resources/tankp1_ef.png";
        String p3_south = "./src/resources/tankp1_s.png";
        String p3_south_fire = "./src/resources/tankp1_sf.png";
        String p3_west = "./src/resources/tankp1_w.png";
        String p3_west_fire = "./src/resources/tankp1_wf.png";
        String p4_north = "./src/resources/tanky1_n.png";
        String p4_north_fire = "./src/resources/tanky1_nf.png";
        String p4_east = "./src/resources/tanky1_e.png";
        String p4_east_fire = "./src/resources/tanky1_ef.png";
        String p4_south = "./src/resources/tanky1_s.png";
        String p4_south_fire = "./src/resources/tanky1_sf.png";
        String p4_west = "./src/resources/tanky1_w.png";
        String p4_west_fire = "./src/resources/tanky1_w_f.png";


        ImagePanel panel;
        MapObject obj = map[x][y];
        if (obj == null) 
        {
            panel = new ImagePanel(freeSpace);
        } else if (obj instanceof Stone) 
        {
            panel = new ImagePanel(stone);
        } else if (obj instanceof Brick) 
        {
            panel = new ImagePanel(brick);
        } else if (obj instanceof Water) 
        {
            panel = new ImagePanel(water);
        } else if (obj instanceof Coin) 
        {
            panel = new ImagePanel(coinPile);
        } else if (obj instanceof LifePack) 
        {
            panel = new ImagePanel(lifePack);
        } else if (obj instanceof Player && !(obj instanceof DeadPlayer)) 
        {
            Player player = (Player) obj;
            if (player.getName().equals("P0")) 
            {
                if (player.getDirection() == 0) 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p0_north_fire);
                    } else 
                    {
                        panel = new ImagePanel(p0_north);
                    }

                } else if (player.getDirection() == 1) 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p0_east_fire);
                    } else 
                    {
                        panel = new ImagePanel(p0_east);
                    }
                } else if (player.getDirection() == 2) 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p0_south_fire);
                    } else 
                    {
                        panel = new ImagePanel(p0_south);
                    }
                } else 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p0_west_fire);
                    } else 
                    {
                        panel = new ImagePanel(p0_west);
                    }
                }
            } else if (player.getName().equals("P1")) 
            {
                if (player.getDirection() == 0) 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p1_north_fire);
                    } else 
                    {
                        panel = new ImagePanel(p1_north);
                    }
                } else if (player.getDirection() == 1) 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p1_east_fire);
                    } else 
                    {
                        panel = new ImagePanel(p1_east);
                    }
                } else if (player.getDirection() == 2) 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p1_south_fire);
                    } else 
                    {
                        panel = new ImagePanel(p1_south);
                    }
                } else 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p1_west_fire);
                    } else 
                    {
                        panel = new ImagePanel(p1_west);
                    }
                }
            } else if (player.getName().equals("P2")) 
            {
                if (((Player) obj).getDirection() == 0) 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p2_north_fire);
                    } else 
                    {
                        panel = new ImagePanel(p2_north);
                    }
                } else if (player.getDirection() == 1) 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p2_east_fire);
                    } else 
                    {
                        panel = new ImagePanel(p2_east);
                    }
                } else if (player.getDirection() == 2) 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p2_south_fire);
                    } else 
                    {
                        panel = new ImagePanel(p2_south);
                    }
                } else 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p2_west_fire);
                    } else 
                    {
                        panel = new ImagePanel(p2_west);
                    }
                }
            } else if (player.getName().equals("P3")) 
            {
                if (((Player) obj).getDirection() == 0) 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p3_north_fire);
                    } else 
                    {
                        panel = new ImagePanel(p3_north);
                    }
                } else if (player.getDirection() == 1) 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p3_east_fire);
                    } else 
                    {
                        panel = new ImagePanel(p3_east);
                    }
                } else if (player.getDirection() == 2) 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p3_south_fire);
                    } else 
                    {
                        panel = new ImagePanel(p3_south);
                    }
                } else 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p3_west_fire);
                    } else 
                    {
                        panel = new ImagePanel(p3_west);
                    }
                }
            } else 
            {
                if (player.getDirection() == 0) 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p4_north_fire);
                    } else 
                    {
                        panel = new ImagePanel(p4_north);
                    }
                } else if (player.getDirection() == 1) 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p4_east_fire);
                    } else 
                    {
                        panel = new ImagePanel(p4_east);
                    }
                } else if (player.getDirection() == 2) 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p4_south_fire);
                    } else 
                    {
                        panel = new ImagePanel(p4_south);
                    }
                } else 
                {
                    if (player.isShot()) 
                    {
                        panel = new ImagePanel(p4_west_fire);
                    } else 
                    {
                        panel = new ImagePanel(p4_west);
                    }
                }
            }
            
        }else 
        {
            panel = new ImagePanel(freeSpace);
        }
        return panel;
    }

    private void initComponents() throws IOException 
    {
        int boardSize = (int) ((int) ImagePanel.getImageSize() * GameEngine.SIZE * 1.1);

        createSquares();
        createTable();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GameMap");
        getContentPane().add(squaresPanel, BorderLayout.CENTER);
        getContentPane().add(table, BorderLayout.AFTER_LAST_LINE);
        setLocationByPlatform(true);
        setVisible(true);
        Dimension preferredSize = new Dimension();
        preferredSize.width = boardSize;
        
        preferredSize.height = boardSize + 100;
        setPreferredSize(preferredSize);
        setResizable(false);
        pack();
    }

    public void update(Observable o, Object arg) 
    {
        if (o instanceof GameEngine) 
        {
            map = GameEngine.getInstance().getMap();
            players = GameEngine.getInstance().getPlayers();
            try 
            {
                getContentPane().remove(squaresPanel);
                createSquares();
                createTable();
                getContentPane().add(squaresPanel, BorderLayout.CENTER);
                setVisible(true);
                repaint();
            } catch (IOException ex) 
            {
                Logger.getLogger(GameMapUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() 
            {
                try 
                {
                    GameMapUI map = new GameMapUI();
                } catch (IOException ex) 
                {
                    Logger.getLogger(GameMapUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
