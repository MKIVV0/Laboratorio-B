package user;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PannelloCerca extends JPanel {

    private JLabel labelCercaBrano;
    private JTextField fieldCercaBrano;
    private JLabel labelYear;
    private JTextField fieldYear;
    private JLabel labelCercaPer;
    private JRadioButton radioCercaPerTitolo;
    private JRadioButton radioCercaPerAutore;
    private ButtonGroup gruppoRadioCercaPer;
    private JButton bottoneCerca;
    private CercaListener cercaListener;
    private Border bordoInterno, bordoEsterno, bordoFinale;

    PannelloCerca() {
        setLayout(new GridBagLayout());

        // Bordi
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

        bottoneCerca = new JButton("Find!");
        bottoneCerca.setFocusable(false);

        bottoneCerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoRicerca = gruppoRadioCercaPer.getSelection().getActionCommand();
                String testo = fieldCercaBrano.getText();
                CercaEvent cercaEvent;
                if (radioCercaPerAutore.isSelected()) {
                    try {
                        int year = Integer.parseInt(fieldYear.getText());
                        cercaEvent = new CercaEvent(this, testo, year, tipoRicerca);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please insert a valid year");
                        return;
                    }
                } else {
                    cercaEvent = new CercaEvent(this, testo, -1, tipoRicerca);
                }
                if (cercaListener != null) {
                    cercaListener.cercaEventListener(cercaEvent);
                }
            }
        });

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();

        // RIGA 0
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 0.01;
        gbc.weighty = 0.03;

        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.insets = new Insets(0, 0, 0, 5);

        add(labelCercaBrano, gbc);

        // RIGA 0
        gbc.gridx = 1;
        gbc.gridy = 0;

        gbc.weightx = 0.01;
        gbc.weighty = 0.03;

        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.insets = new Insets(0, 0, 0, 0);

        add(fieldCercaBrano, gbc);

        // RIGA 1
        gbc.gridx = 0;
        gbc.gridy = 1;

        gbc.weightx = 0.01;
        gbc.weighty = 0.03;

        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.insets = new Insets(0, 0, 0, 5);

        add(labelYear, gbc);

        // RIGA 1
        gbc.gridx = 1;
        gbc.gridy = 1;

        gbc.weightx = 0.01;
        gbc.weighty = 0.03;

        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.insets = new Insets(0, 0, 0, 0);

        add(fieldYear, gbc);

        // RIGA 2
        gbc.gridx = 0;
        gbc.gridy = 2;

        gbc.weightx = 0.01;
        gbc.weighty = 0.03;

        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.insets = new Insets(0, 0, 0, 5);

        add(labelCercaPer, gbc);

        // RIGA 2
        gbc.gridx = 1;
        gbc.gridy = 2;

        gbc.weightx = 0.01;
        gbc.weighty = 0.03;

        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.insets = new Insets(0, 0, 0, 0);

        add(radioCercaPerTitolo, gbc);

        // RIGA 3
        gbc.gridx = 1;
        gbc.gridy = 3;

        gbc.weightx = 0.01;
        gbc.weighty = 0.03;

        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.insets = new Insets(0, 0, 0, 5);

        add(radioCercaPerAutore, gbc);

        // RIGA 4
        gbc.gridx = 0;
        gbc.gridy = 4;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.gridwidth = 2;
        gbc.gridheight = 1;

        gbc.anchor = GridBagConstraints.PAGE_START;

        gbc.insets = new Insets(0, 0, 0, 0);

        add(bottoneCerca, gbc);

        setColor(Frame.backDark, Frame.compBackDark, Frame.compForeDark);
    }

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

    public void setCercaListener(CercaListener cercaListener) {
        this.cercaListener = cercaListener;
    }

}