package user;

import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
                objectAreaPanel.isSongOfPlaylist(false);
                objectAreaPanel.logged(logged);
                objectAreaPanel.pulisciArea();
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
        objectAreaPanel.setSongListener(new SongListener() {
            @Override
            public LinkedList<String> guardaFeedback(Song song) throws NoFeedbackException, SQLException, RemoteException {
                return resourceManager.getFeedback(song);
            }

            @Override
            public void addSong(Song song) throws SQLException, playlistException, RemoteException {
                LinkedList<Playlist> userPlaylists = ((LoggedUser)user).getPlaylistList();
                String[] opzioni = new String[userPlaylists.size()];
                int i = 0;
                for(Playlist p: userPlaylists)
                    opzioni[i++] = p.getPlaylistName();
                JPanel panel = new JPanel(new BorderLayout(5, 5));
                JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                label.add(new JLabel("Playlist: ", SwingConstants.RIGHT));
                panel.add(label, BorderLayout.WEST);
                JPanel controls = new JPanel(new GridLayout());
                JComboBox listaPlaylists = new JComboBox(opzioni);
                controls.add(listaPlaylists);
                panel.add(controls, BorderLayout.CENTER);
                int scelta = JOptionPane.showConfirmDialog(Frame.getFrames()[0], panel, "seleziona playlist", JOptionPane.OK_CANCEL_OPTION);
                if(scelta == 0 && listaPlaylists.getSelectedIndex() != -1){
                    String plName = (String)listaPlaylists.getSelectedItem();
                    Playlist p = ((LoggedUser)user).getPlaylist(plName);
                    resourceManager.addSongToPlaylist(plName, song, user);
                    p.addSong(song);
                    pannelloPlaylist.logged(user);
                }
            }

            @Override
            public void removeSong(Song song) throws SQLException, playlistException, RemoteException {
                Playlist playlist = pannelloPlaylist.getPlaylist();
                resourceManager.removeSongFromPlaylist(playlist.getPlaylistName(), song, user);
                playlist.removeSong(song);
                pannelloPlaylist.logged(user);
                objectAreaPanel.pulisciArea();
                objectAreaPanel.inserisciBrani(playlist.getSongList());
                objectAreaPanel.isSongOfPlaylist(true);
            }

            @Override
            public void valutaSong(FeedbackForm ff) throws NotLoggedException, SQLException, AlreadyValuedException, RemoteException {
                resourceManager.evaluateSong(ff.emotions, user, ff.song, String.valueOf(ff.score), ff.notes);
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
                    objectAreaPanel.isSongOfPlaylist(false);
                    objectAreaPanel.logged(logged);
                } catch (RemoteException e) {
                }
            }
        });

        // PANNELLO PLAYLIST
        pannelloPlaylist = new PannelloPlaylist();
        pannelloPlaylist.setPlaylistListener(new PlaylistListener() {
            @Override
            public void creaPlaylist(String plName) throws SQLException, playlistException, RemoteException {
                Playlist nuova = resourceManager.createPlaylist(plName, user);
                ((LoggedUser)user).addPlaylist(nuova);
                pannelloPlaylist.logged(user);
            }

            @Override
            public void eliminaPlaylist(String plName) throws SQLException, playlistException, RemoteException {
                resourceManager.deletePlaylist(plName, user);
                ((LoggedUser)user).deletePlaylist(plName);
                objectAreaPanel.pulisciArea();
                pannelloPlaylist.logged(user);
            }

            @Override
            public void apriPlaylist(String plName) {
                Playlist playlist = ((LoggedUser) user).getPlaylist(plName);
                LinkedList<Song> tmp = playlist.getSongList();
                pannelloPlaylist.setPlaylist(playlist);
                objectAreaPanel.pulisciArea();
                objectAreaPanel.inserisciBrani(tmp);
                objectAreaPanel.isSongOfPlaylist(true);
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
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int scelta = JOptionPane.showConfirmDialog(Frame.getFrames()[0], "Continuare?",
                        "Chiusura applicazione", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (scelta == 0) {
                    if (user instanceof LoggedUser)
                        try {
                            user = resourceManager.logout(user);
                        } catch (NotLoggedException ex) {
                        } catch (RemoteException ex) {
                        }
                    System.exit(0);
                }
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
