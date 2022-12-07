package user;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PannelloForm extends JPanel {

    private JLabel labelCercaBrano;
    private JTextField fieldCercaBrano;

    private JLabel labelCercaPer;
    private JRadioButton radioCercaPerTitolo;
    private JRadioButton radioCercaPerAutore;
    private ButtonGroup gruppoRadioCercaPer;

    private JButton bottoneCerca;

    private FormListener formListener;

    PannelloForm() {
        setPreferredSize(new Dimension(300, 100));
        setLayout(new GridBagLayout());

        // Bordi
        Border bordoInterno = BorderFactory.createTitledBorder("Home");
        Border bordoEsterno = BorderFactory.createEmptyBorder(0, 5, 5, 5);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);

        setBorder(bordoFinale);

        labelCercaBrano = new JLabel("CercaBrano: ");
        fieldCercaBrano = new JTextField(15);

        labelCercaPer = new JLabel("CercaPer: ");

        radioCercaPerTitolo = new JRadioButton("Titolo");
        radioCercaPerTitolo.setActionCommand("titolo");
        radioCercaPerAutore = new JRadioButton("Autore");
        radioCercaPerAutore.setActionCommand("autore");

        gruppoRadioCercaPer = new ButtonGroup();
        gruppoRadioCercaPer.add(radioCercaPerTitolo);
        gruppoRadioCercaPer.add(radioCercaPerAutore);

        bottoneCerca = new JButton("Cerca!");

        bottoneCerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String testo = fieldCercaBrano.getText();
                String tipoRicerca = gruppoRadioCercaPer.getSelection().getActionCommand();

                FormEvent formEvent = new FormEvent(this, testo, tipoRicerca);

                if (formListener != null) {
                    formListener.formEventListener(formEvent);
                }
            }
        });

        // Layout
        GridBagConstraints gbc = new  GridBagConstraints();

        // RIGA 1
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 0.01;
        gbc.weighty = 0.03;

        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.insets = new Insets(0, 0, 0, 5);

        add(labelCercaBrano, gbc);

        // RIGA 1
        gbc.gridx = 1;
        gbc.gridy = 0;

        gbc.weightx = 0.01;
        gbc.weighty = 0.03;

        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.insets = new Insets(0, 0, 0, 0);

        add(fieldCercaBrano, gbc);

        // RIGA 2
        gbc.gridx = 0;
        gbc.gridy = 1;

        gbc.weightx = 0.01;
        gbc.weighty = 0.03;

        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.insets = new Insets(0, 0, 0, 5);

        add(labelCercaPer, gbc);

        // RIGA 2
        gbc.gridx = 1;
        gbc.gridy = 1;

        gbc.weightx = 0.01;
        gbc.weighty = 0.03;

        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.insets = new Insets(0, 0, 0, 0);

        add(radioCercaPerTitolo, gbc);

        // RIGA 3
        gbc.gridx = 1;
        gbc.gridy = 2;

        gbc.weightx = 0.01;
        gbc.weighty = 0.03;

        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.insets = new Insets(0, 0, 0, 5);

        add(radioCercaPerAutore, gbc);

        // RIGA 3
        gbc.gridx = 0;
        gbc.gridy = 3;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.gridwidth = 2;
        gbc.gridheight = 1;

        gbc.anchor = GridBagConstraints.PAGE_START;

        gbc.insets = new Insets(0, 0, 0, 0);

        add(bottoneCerca, gbc);



    }

    public void setFormListener(FormListener formListener) {
        this.formListener = formListener;
    }
}