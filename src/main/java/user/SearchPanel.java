/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Provides the search functions
 */
public class SearchPanel extends JPanel {

    /**
     * song or author label
     */
    private JLabel labelCercaBrano;
    /**
     * text field for user's input
     */
    private JTextField fieldCercaBrano;
    /**
     * year label
     */
    private JLabel labelYear;
    /**
     * text field for user's input
     */
    private JTextField fieldYear;
    /**
     * "search by" label
     */
    private JLabel labelCercaPer;
    /**
     * search by title radio button
     */
    private JRadioButton radioCercaPerTitolo;
    /**
     * search by author radio button
     */
    private JRadioButton radioCercaPerAutore;
    /**
     * binds the JRadioButton
     */
    private ButtonGroup gruppoRadioCercaPer;
    /**
     * this attribute represents the find button
     */
    private JButton bottoneFind;
    /**
     * this attribute represents an entity that waits for some user interaction with the find button
     */
    private SearchListener searchListener;
    /**
     * these attributes represent the JPanel's borders
     */
    private Border bordoInterno, bordoEsterno, bordoFinale;

    /**
     * Empty constructor, initializes its components.
     */
    SearchPanel() {
        setLayout(new GridBagLayout());
        Border b = BorderFactory.createMatteBorder(1,0,0,0,Color.PINK);
        bordoInterno = BorderFactory.createTitledBorder(b,"Home",TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP,
                new Font("Geneva", Font.BOLD, 12),Frame.compForeDark);
        bordoEsterno = BorderFactory.createEmptyBorder(0, 5, 5, 5);
        bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
        setBorder(bordoFinale);

        labelCercaBrano = new JLabel("Song: ");
        fieldCercaBrano = new JTextField(15);
        fieldCercaBrano.setCaretColor(Frame.compForeDark);

        labelYear = new JLabel("Year: ");
        fieldYear = new JTextField(15);
        fieldYear.setCaretColor(Frame.compForeDark);
        labelYear.setEnabled(false);
        fieldYear.setEnabled(false);

        labelCercaPer = new JLabel("Search by: ");
        radioCercaPerTitolo = new JRadioButton("Title");
        radioCercaPerTitolo.setActionCommand("title");
        radioCercaPerTitolo.setSelected(true);
        radioCercaPerTitolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radioCercaPerTitolo.isSelected()){
                    labelCercaBrano.setText("Song: ");
                    labelYear.setEnabled(false);
                    fieldYear.setEnabled(false);
                }
            }
        });
        radioCercaPerAutore = new JRadioButton("Author");
        radioCercaPerAutore.setActionCommand("author");
        radioCercaPerAutore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radioCercaPerAutore.isSelected()){
                    labelCercaBrano.setText("Author: ");
                    labelYear.setEnabled(true);
                    fieldYear.setEnabled(true);
                }
            }
        });
        gruppoRadioCercaPer = new ButtonGroup();
        gruppoRadioCercaPer.add(radioCercaPerTitolo);
        gruppoRadioCercaPer.add(radioCercaPerAutore);

        bottoneFind = new JButton("Find!");
        bottoneFind.setFocusable(false);
        bottoneFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoRicerca = gruppoRadioCercaPer.getSelection().getActionCommand();
                String testo = fieldCercaBrano.getText();
                SearchEvent searchEvent;
                if (radioCercaPerAutore.isSelected()) {
                    try {
                        int year = Integer.parseInt(fieldYear.getText());
                        searchEvent = new SearchEvent(this, testo, year, tipoRicerca);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please insert a valid year");
                        return;
                    }
                } else {
                    searchEvent = new SearchEvent(this, testo, -1, tipoRicerca);
                }
                if (searchListener != null) {
                    searchListener.cercaEventListener(searchEvent);
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.01;
        gbc.weighty = 0.03;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(labelCercaBrano, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.01;
        gbc.weighty = 0.03;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(fieldCercaBrano, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.01;
        gbc.weighty = 0.03;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(labelYear, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.01;
        gbc.weighty = 0.03;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(fieldYear, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.01;
        gbc.weighty = 0.03;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(labelCercaPer, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.01;
        gbc.weighty = 0.03;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(radioCercaPerTitolo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.01;
        gbc.weighty = 0.03;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(radioCercaPerAutore, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(bottoneFind, gbc);

        setColor(Frame.backDark, Frame.compBackDark, Frame.compForeDark);
    }

    /**
     * Set the background color and the component's background and foreground.
     * @param back
     * @param compBack
     * @param compFore
     */
    public void setColor(Color back, Color compBack, Color compFore){
        for(Component c: getComponents())
            if(c instanceof JRadioButton) {
                c.setBackground(back);
                c.setForeground(compFore);
                c.setFocusable(false);
            } else {
                c.setBackground(compBack);
                c.setForeground(compFore);
            }
        setBackground(back);
    }

    /**
     * searchListener setter.
     * @param searchListener
     */
    public void setCercaListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

}