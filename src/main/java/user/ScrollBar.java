/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import javax.swing.*;
import java.awt.*;

/**
 * This class is just a new kind of scroll bar
 */
public class ScrollBar extends JScrollBar {

    public ScrollBar() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(10, 5));
        setBackground(new Color(70, 70, 70));
        setUnitIncrement(20);
    }
}
