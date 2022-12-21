package user;

import common.*;

import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.LinkedList;

public class Frame extends JFrame {

    private BarraStrumenti barraStrumenti;
    private JPanel panel;
    private PannelloCerca pannelloCerca;
    private PannelloPlaylist pannelloPlaylist;
    private ObjectAreaPanel objectAreaPanel;
    private ResourceManagerInterface resourceManager;
    private AbstractUser user;
    private boolean logged;

    public Frame() throws RemoteException, NotBoundException {
        super("Emotional Songs");
        setLayout(new BorderLayout());

        Registry r = LocateRegistry.getRegistry(11000);
        resourceManager = (ResourceManagerInterface) r.lookup("Gestore");
        user = new NotLoggedUser();
        logged = false;

        // BARRA STRUMENTI
        barraStrumenti = new BarraStrumenti();
        barraStrumenti.setLogListener(new LogListener() {
            @Override
            public void credenzialiFornite(LogEvent le) throws AlreadyLoggedException, SQLException, RemoteException, WrongCredentialsException {
                String username = le.getUsername();
                String password = le.getPassword();
                user = resourceManager.login(user, username, password);
                logged = user instanceof LoggedUser;
                barraStrumenti.logged(logged);
                objectAreaPanel.logged(logged);
                pannelloPlaylist.logged(user);
            }
            @Override
            public void logout() throws NotLoggedException, RemoteException {
                user = resourceManager.logout(user);
                logged = user instanceof LoggedUser;
                barraStrumenti.logged(logged);
                objectAreaPanel.logged(logged);
                pannelloPlaylist.logged(user);
            }
        });
        barraStrumenti.setRegistrazioneListener(new RegistrazioneListener() {
            @Override
            public void datiForniti(RegistrazioneEvent re) throws AlreadyRegisteredException, RemoteException {
                resourceManager.registerUser(re.fn, re.ln, re.FC, re.addr, re.em, re.uid, re.pw);
            }
        });




        // OBJECT AREA PANEL
        objectAreaPanel = new ObjectAreaPanel();
        objectAreaPanel.setFeedbackListener(new FeedbackListener() {
            @Override
            public LinkedList<String> guardaFeedback(Song song) throws NoFeedbackException, SQLException, RemoteException {
                return resourceManager.getFeedback(song);
            }
        });




        // PANNELLO CERCA
        pannelloCerca = new PannelloCerca();
        pannelloCerca.setCercaListener(new CercaListener() {
            @Override
            public void cercaEventListener(CercaEvent ce) {
                String testo = ce.getTesto();
                String tipoRicerca = ce.getTipoRicerca();
                try {
                    LinkedList<Song> tmp = resourceManager.findSong(testo);
                    objectAreaPanel.pulisciArea();
                    objectAreaPanel.inserisciBrani(tmp);
                } catch (RemoteException e) {
                }
            }
        });

        // PANNELLO PLAYLIST
        pannelloPlaylist = new PannelloPlaylist();
        pannelloPlaylist.setCreaPlaylistListener(new CreaPlaylistListener() {
            @Override
            public void creaPlaylist(String plName) throws SQLException, playlistException, RemoteException {
                Playlist nuova = resourceManager.createPlaylist(plName, user);
                ((LoggedUser)user).addPlaylist(nuova);
                pannelloPlaylist.logged(user);
            }
        });

        // PANNELLO
        panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, 100));

        panel.add(pannelloCerca, BorderLayout.PAGE_START);
        panel.add(pannelloPlaylist, BorderLayout.CENTER);

        add(objectAreaPanel, BorderLayout.CENTER);
        add(barraStrumenti, BorderLayout.PAGE_START);
        add(panel, BorderLayout.LINE_START);

        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
