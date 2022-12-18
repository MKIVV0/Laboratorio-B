package user;

import common.AbstractUser;
import common.NotLoggedUser;
import common.ResourceManagerInterface;

import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Frame extends JFrame {

    private BarraStrumenti barraStrumenti;
    ObjectAreaPanel objectAreaPanel;
    ControlPanel controlPanel;
//    private PannelloForm pannelloForm;
    ResourceManagerInterface resourceManager;
    AbstractUser user;

    public Frame() throws RemoteException, NotBoundException {

        super("Emotional Songs");

        setLayout(new BorderLayout());

        Registry r = LocateRegistry.getRegistry(11000);
        resourceManager = (ResourceManagerInterface) r.lookup("Gestore");
        user = new NotLoggedUser();

        objectAreaPanel = new ObjectAreaPanel(this);
//        pannelloForm = new PannelloForm();
        controlPanel = new ControlPanel(this);
        barraStrumenti = new BarraStrumenti(this);


        /*pannelloForm.setFormListener(new FormListener() {
            @Override
            public void formEventListener(FormEvent fe) {
                String testo = fe.getTesto();
                String tipoRicerca = fe.getTipoRicerca();
                try {
                    LinkedList<Song> tmp = resourceManager.findSong(testo);
                    objectAreaPanel.pulisciArea();
                    objectAreaPanel.inserisciBrani(tmp);
                } catch (RemoteException e) {
                }
            }
        });*/

        add(objectAreaPanel, BorderLayout.CENTER);
        add(barraStrumenti, BorderLayout.PAGE_START);
//        add(pannelloForm, BorderLayout.LINE_START);
        add(controlPanel, BorderLayout.LINE_START);

        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
