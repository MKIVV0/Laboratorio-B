package user;

import javax.swing.*;
import java.awt.*;

public class ScrollBar extends JScrollBar {

    public ScrollBar() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(10, 5));
        setBackground(new Color(70, 70, 70));
        setUnitIncrement(20);
    }
}
