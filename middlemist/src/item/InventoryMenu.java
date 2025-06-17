package item;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

import entity.ItemEntity;
import entity.Player;
import main.GamePanel;

public class InventoryMenu extends JPanel {

    private static final long serialVersionUID = 1L;
    Player player;
    public static final int inventoryMax = 64;
    public static InventoryMenu im;
    final int inW = 612, inH = 256;
    ArrayList<JMenuItem> selectedItems, allItems;
    ArrayList<Item> items;
    JMenuBar menuBar;
    JLayeredPane displayPane;
    ImageIcon itemIcon;

    public InventoryMenu() {
        innitMenus();
        selectedItems = new ArrayList<>();
        allItems = new ArrayList<>();
        items = new ArrayList<>();
        displayPane = new JLayeredPane();
        displayPane.setBounds(new Rectangle(-4, 20, inW, inH - 20));
        displayPane.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 10));
        this.setFocusable(false);
        this.setLayout(null);
        this.add(displayPane);
        player = GamePanel.player;
        this.setPreferredSize(new Dimension(inW, inH));
        this.setBackground(Color.gray);
        this.setDoubleBuffered(true);
    }

    private void innitMenus() {
        menuBar = new JMenuBar();
        menuBar.setBounds(new Rectangle(0, 0, inW, 20));
        menuBar.setBackground(Color.white);
        menuBar.setOpaque(true);
        menuBar.setVisible(true);
        menuBar.setDoubleBuffered(true);
        MouseListener menuListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (e.getSource() instanceof JMenu) {
                        JMenu menu = (JMenu) e.getSource();
                        switchMenu(menu.getText());
                        menu.setEnabled(false);
                        for (Component comp : menuBar.getComponents()) {
							if (comp instanceof JMenu && comp != menu) {
								((JMenu) comp).setEnabled(true);
							}
						}
                    }
                }
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        };
        JMenu inventory = new JMenu("Inventory");
        inventory.setOpaque(true);
        inventory.addMouseListener(menuListener);
        JMenu character = new JMenu("Character");
        character.setOpaque(true);
        character.addMouseListener(menuListener);
        JMenu skills = new JMenu("Skills");
        skills.setOpaque(true);
        skills.addMouseListener(menuListener);
        menuBar.add(inventory);
        menuBar.add(character);
        menuBar.add(skills);
        this.add(menuBar);
    }

    public void updateInventory() {
        itemIcon = null;
        selectedItems.clear();
        items.clear();
        allItems.clear();
        displayPane.removeAll();

        for (Item item : player.inventory) {
            items.add(item);
            itemIcon = new ImageIcon(item.icon);
            itemIcon.setImage(item.icon.getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH));
            JMenuItem itemDisplay = new JMenuItem(item.name, itemIcon);
            allItems.add(itemDisplay);
            itemDisplay.setPreferredSize(new Dimension(itemIcon.getIconWidth(), itemIcon.getIconHeight()));
            itemDisplay.addActionListener(e -> {
                JMenuItem itemD = (JMenuItem) e.getSource();
                int idx = allItems.indexOf(itemD);
                if (idx >= 0) {
                    Item actionItem = items.get(idx);
                    if (itemD.isSelected()) {
                        selectedItems.remove(itemD);
                        itemD.setSelected(false);
                    } else {
                        selectedItems.add(itemD);
                        itemD.setSelected(true);
                    }
                    System.out.println(actionItem.name + " selected: " + itemD.isSelected());
                }
            });

            JPopupMenu itemPopup = new JPopupMenu();
            JMenuItem itemUse = new JMenuItem("Use/Select");
            itemUse.addActionListener(e -> {
                JMenuItem itemD = (JMenuItem) itemDisplay;
                int idx = allItems.indexOf(itemD);
                if (idx >= 0) {
                    Item actionItem = items.get(idx);
                    if (!actionItem.hasUse) {
                        if (itemD.isSelected()) {
                            selectedItems.remove(itemD);
                            itemD.setSelected(false);
                        } else {
                            selectedItems.add(itemD);
                            itemD.setSelected(true);
                        }
                        System.out.println(actionItem.name + " selected: " + itemD.isSelected());
                    }
                }
            });
            itemPopup.add(itemUse);

            JMenuItem itemInfo = new JMenuItem("Info");
            itemInfo.addActionListener(e -> {
                JMenuItem itemD = (JMenuItem) itemDisplay;
                int idx = allItems.indexOf(itemD);
                if (idx >= 0) {
                    Item actionItem = items.get(idx);
                    System.out.println("Item Name: " + actionItem.name);
                    System.out.println("Item ID: " + actionItem.id);
                }
            });
            itemPopup.add(itemInfo);

            JMenuItem itemConsume = new JMenuItem("Consume");
            itemConsume.addActionListener(e -> {
                JMenuItem itemD = (JMenuItem) itemDisplay;
                int idx = allItems.indexOf(itemD);
                if (idx >= 0) {
                    Item actionItem = items.get(idx);
                    if (actionItem.isConsumable) {
                        actionItem.consume();
                        player.inventory.remove(actionItem);
                        items.remove(actionItem);
                        allItems.remove(itemD);
                        updateInventory();
                    } else {
                        System.out.println("This item cannot be used.");
                    }
                }
            });
            itemPopup.add(itemConsume);

            JMenuItem itemDrop = new JMenuItem("Drop");
            itemDrop.addActionListener(e -> {
                JMenuItem itemD = (JMenuItem) itemDisplay;
                int idx = allItems.indexOf(itemD);
                if (idx >= 0) {
                    Item actionItem = items.get(idx);
                    System.out.println("Dropping item: " + actionItem.name);
                    System.out.println("Item ID: " + idx);
                    ItemEntity droppedItem = new ItemEntity(actionItem.name, player.x, player.y + GamePanel.player.height + 4, actionItem);
                    if (!(GamePanel.world.checkWorldCollision(droppedItem.entCollide))){
						GamePanel.entLoader.loadEntity(droppedItem);
						player.inventory.remove(actionItem);
						items.remove(actionItem);
						allItems.remove(itemD);
						updateInventory();
					} else {
						GamePanel.entLoader.removeEntity(droppedItem);
						System.out.println("Cannot drop item here, world collision detected.");
					}
                }
            });
            itemPopup.add(itemDrop);

            itemDisplay.setComponentPopupMenu(itemPopup);
            itemDisplay.setOpaque(true);
            itemDisplay.setBackground(Color.LIGHT_GRAY);

            if (selectedItems.contains(itemDisplay)) {
                itemDisplay.setOpaque(false);
                itemDisplay.setBackground(Color.BLUE);
            }

            displayPane.add(itemDisplay);
        }
        displayPane.revalidate();
        displayPane.repaint();
    }

    private void innitInv() {
        updateInventory();
    }

    private void innitCharMenu() {
        displayPane.removeAll();
        BufferedImage charImage = null;
        try {
            charImage = ImageIO.read(getClass().getResourceAsStream(GamePanel.player.updateCharacter()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon charIcon = new ImageIcon(charImage);
        JLabel charLabel = new JLabel(charIcon, JLabel.CENTER);
        charLabel.setPreferredSize(new Dimension(charLabel.getIcon().getIconWidth(), charLabel.getIcon().getIconHeight()));
        charLabel.setVerticalAlignment(JLabel.CENTER);
        displayPane.add(charLabel);
        JTextArea charStats = new JTextArea();
        charStats.setPreferredSize(new Dimension(200, 150));
        charStats.setAlignmentX(RIGHT_ALIGNMENT);
        charStats.setAlignmentY(CENTER_ALIGNMENT);
        charStats.setBackground(displayPane.getBackground());
        charStats.setEditable(false);
        charStats.setText("Name: " + GamePanel.player.entName + "\n" +
						  "Health: " + GamePanel.player.health + "/" + GamePanel.player.maxHealth + "\n" +
						  "Level: " + GamePanel.player.level + "\n" +
						  "Experience: " + GamePanel.player.xp + "\n" +
						  "Inventory Size: " + GamePanel.player.inventory.size() + "/" + InventoryMenu.inventoryMax);
        try {
			charStats.setFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/res/fonts/super-pixel-font/SuperPixel-m2L8j.ttf")).deriveFont(14f));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        displayPane.add(charStats);
        JButton changeNameButton = new JButton("Change Name");
        changeNameButton.setPreferredSize(new Dimension(150, 30));
        changeNameButton.setAlignmentX(RIGHT_ALIGNMENT);
        changeNameButton.setAlignmentY(CENTER_ALIGNMENT);
        changeNameButton.setBackground(Color.LIGHT_GRAY);
        try {
			changeNameButton.setFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/res/fonts/super-pixel-font/SuperPixel-m2L8j.ttf")).deriveFont(10f));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        changeNameButton.addActionListener(e -> {
			String newName = JOptionPane.showInputDialog("Enter new name:");
			if (newName != null && !newName.trim().isEmpty()) {
				GamePanel.player.entName = newName.trim();
				charStats.setText("Name: " + GamePanel.player.entName + "\n" +
								  "Health: " + GamePanel.player.health + "/" + GamePanel.player.maxHealth + "\n" +
								  "Level: " + GamePanel.player.level + "\n" +
								  "Experience: " + GamePanel.player.xp + "\n" +
								  "Inventory Size: " + GamePanel.player.inventory.size() + "/" + InventoryMenu.inventoryMax);
			}
			GamePanel.gamePanel.requestFocus(); // Ensure focus returns to the game panel after input
		});
        displayPane.add(changeNameButton);
        displayPane.revalidate();
        displayPane.repaint();
    }

    private void innitSkillsMenu() {
        JLabel skillsLabel = new JLabel("Skills Menu (Not Implemented)");
        skillsLabel.setPreferredSize(new Dimension(200, 50));
        skillsLabel.setHorizontalAlignment(JLabel.CENTER);
        skillsLabel.setVerticalAlignment(JLabel.CENTER);
        InventoryMenu.im.displayPane.add(skillsLabel);
        InventoryMenu.im.displayPane.revalidate();
        InventoryMenu.im.displayPane.repaint();
    }

    public static void setInv(InventoryMenu im) {
        InventoryMenu.im = im;
    }

    public static void toggleVisibility() {
        InventoryMenu.switchMenu("Inventory");
        im.setVisible(!im.isVisible());
        GamePanel.gamePanel.togglePause();
        GamePanel.gamePanel.enableInputMethods(!im.isVisible());
        GamePanel.gamePanel.requestFocus(); 
    }

    public static void switchMenu(String menu) {
        for (Component comp : InventoryMenu.im.displayPane.getComponents()) {
            comp.setVisible(false);
            comp = null;
        }
        System.gc();
        InventoryMenu.im.displayPane.removeAll();
        switch (menu) {
            case "Inventory":
                InventoryMenu.im.innitInv();
                break;
            case "Character":
                InventoryMenu.im.innitCharMenu();
                break;
            case "Skills":
                InventoryMenu.im.innitSkillsMenu();
                break;
        }
    }
}

