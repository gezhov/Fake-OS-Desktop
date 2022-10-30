package edu.psuti.pe.gui;

import edu.psuti.pe.gui.helper.ComponentMover;
import edu.psuti.pe.gui.helper.ComponentResizer;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.border.DropShadowBorder;

import javax.swing.*;
import java.awt.*;

// todo ? в дальнейшем сделать абстрактным
public class WindowPanel extends JXPanel {
    private ComponentMover componentMover;
    private ComponentResizer componentResizer = new ComponentResizer();

    private String appTitle;
    private int width;
    private int height;

    private JPanel titleBarPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            Dimension arcs = new Dimension(15, 15); // Upper corners arcs {width, height}
            int width = getWidth();
            int height = getHeight();
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, width, height, arcs.width, arcs.height); // рисуется закруглённый прямоугольник
            g2d.fillRect(0, 20, width, height / 2); // закрашивается его нижняя часть
        }
    };

    public WindowPanel(String appTitle, int width, int height) {
        this.width = width;
        this.height = height;
        this.appTitle = appTitle;

        setupWindow();
        setupTitleBar();
    }

    private void setupWindow() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setOpaque(true);
        setBackground(new Color(255, 176, 196));
        setBounds(100, 100, width, height);

        // SwingX functionality
        DropShadowBorder shadow = new DropShadowBorder();
        shadow.setShadowColor(Color.BLACK);
        shadow.setShowLeftShadow(true);
        shadow.setShowRightShadow(true);
        shadow.setShowBottomShadow(true);
        shadow.setShowTopShadow(true);
        this.setBorder(shadow);

        componentResizer.setSnapSize(new Dimension(10, 10));
        componentResizer.registerComponent(this);
    }

    private void setupTitleBar() {
        titleBarPanel.setLayout(new BoxLayout(titleBarPanel, BoxLayout.LINE_AXIS));
        titleBarPanel.setOpaque(true);
        titleBarPanel.setBackground(new Color(222, 224, 226));

        titleBarPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 29));
        titleBarPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 29));
        titleBarPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 29));

        // События перетаскивания на полосе зоголовка будут передаваться панели окна
        componentMover = new ComponentMover(this.getClass(), titleBarPanel);
        componentMover.setChangeCursor(true);
        componentMover.setEdgeInsets(new Insets(0, -500, 0, -500)); // можно выезжать за края
        componentMover.registerComponent(titleBarPanel);

        add(titleBarPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getBackground());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillRect(0, 29, getWidth(), getHeight()); // отрисовка нижней части окна (без полосы заголовка)
    }
}
