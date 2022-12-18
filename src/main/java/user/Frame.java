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
    ResourceManagerInterface resourceManager;
    AbstractUser user;

    public Frame() throws RemoteException, NotBoundException {

        super("Emotional Songs");

        setLayout(new BorderLayout());

        Registry r = LocateRegistry.getRegistry(11000);
        resourceManager = (ResourceManagerInterface) r.lookup("Gestore");
        user = new NotLoggedUser();

        objectAreaPanel = new ObjectAreaPanel(this);
        controlPanel = new ControlPanel(this);
        barraStrumenti = new BarraStrumenti(this);

        add(objectAreaPanel, BorderLayout.CENTER);
        add(barraStrumenti, BorderLayout.PAGE_START);
        add(controlPanel, BorderLayout.LINE_START);

        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
