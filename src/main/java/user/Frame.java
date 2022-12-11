package user;

import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.LinkedList;

public class Frame extends JFrame {

    private BarraStrumenti barraStrumenti;
    private TextAreaPanel textAreaPanel;
    private PannelloForm pannelloForm;
    ResourceManagerInterface resourceManager;
    AbstractUser user;

    public Frame() throws RemoteException, NotBoundException {

        super("Titolo finestra");

        setLayout(new BorderLayout());

        Registry r = LocateRegistry.getRegistry(11000);
        resourceManager = (ResourceManagerInterface) r.lookup("Gestore");
        user = new NotLoggedUser();

        textAreaPanel = new TextAreaPanel();
        pannelloForm = new PannelloForm();
        barraStrumenti = new BarraStrumenti(this);


        pannelloForm.setFormListener(new FormListener() {
            @Override
            public void formEventListener(FormEvent fe) {
                String testo = fe.getTesto();
                String tipoRicerca = fe.getTipoRicerca();

                try {
                    LinkedList<Song> tmp = resourceManager.findSong(testo);
                    for (Song s : tmp)
                        textAreaPanel.appendiTesto(s.toString());

                } catch (RemoteException e) {
                }
            }
        });

        add(textAreaPanel, BorderLayout.CENTER);
        add(barraStrumenti, BorderLayout.PAGE_START);
        add(pannelloForm, BorderLayout.LINE_START);

        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}