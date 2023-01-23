/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This class represents the GUI main window.
 */
public class Frame extends JFrame {

    /**
     * The user's toolbar.
     */
    private ToolBar toolBar;
    /**
     * JPanel which contains both searchPanel and playlistPanel.
     */
    private JPanel panel;
    /**
     * The SearchPanel.
     */
    private SearchPanel searchPanel;
    /**
     * The PlaylistPanel.
     */
    private PlaylistPanel playlistPanel;
    /**
     * The ObjectAreaPanel.
     */
    private ObjectAreaPanel objectAreaPanel;
    /**
     * The resource manager for the client-server comunication.
     */
    private ResourceManagerInterface resourceManager;
    /**
     * This is the object which contains the user's data. Null if not logged.
     */
    private LoggedUser user;
    /**
     * True if logged in, else false.
     */
    private boolean logged;
    /**
     * The GUI background color.
     */
    static Color backDark = new Color(51,51,51);
    /**
     * The component's background color.
     */
    static Color compBackDark = new Color(70,70,70);
    /**
     * The component's foreground color.
     */
    static Color compForeDark = new Color(245,245,245);

    /**
     * Frame constructor.
     * @throws RemoteException
     * @throws NotBoundException
     */
    public Frame() throws RemoteException, NotBoundException {
        super("Emotional Songs");
        setLayout(new BorderLayout());

        Registry r = LocateRegistry.getRegistry(11000);
        resourceManager = (ResourceManagerInterface) r.lookup("Gestore");
        user = null;
        logged = false;

        toolBar = new ToolBar();
        toolBar.setLogListener(new LogListener() {
            @Override
            public void credenzialiFornite(LogEvent le) throws UserException, SQLException, RemoteException {
                String username = le.getUsername();
                String password = le.getPassword();
                user = resourceManager.login(user, username, password);
                logged = user != null;
                toolBar.logged(logged);
                objectAreaPanel.setLogged(logged);
                playlistPanel.logged(user);
            }
            @Override
            public void logout() throws UserException, RemoteException {
                user = resourceManager.logout(user);
                logged = user != null;
                toolBar.logged(logged);
                objectAreaPanel.setSongOfPlaylist(false);
                objectAreaPanel.setLogged(logged);
                objectAreaPanel.pulisciArea();
                playlistPanel.logged(user);
            }
        });
        toolBar.setRegistrazioneListener(new RegistrationListener() {
            @Override
            public void datiForniti(RegistrationEvent re) throws UserException, RemoteException {
                resourceManager.registerUser(re.fn, re.ln, re.FC, re.addr, re.em, re.uid, re.pw);
            }
        });
        toolBar.setSettingsListener(new SettingsListener() {
            @Override
            public void modifyUsername(String nuovo) throws SQLException, UserException, RemoteException {
                resourceManager.modifyUserParam(user, "user_id", nuovo);
                user.setUserID(nuovo);
            }
            @Override
            public void modifyPassword(String vecchia, String nuova) throws SQLException, UserException, RemoteException {
                if(!vecchia.equals(user.getPassword()))
                    throw new UserException("Old password missmatch");
                resourceManager.modifyUserParam(user, "password", nuova);
                user.setPassword(nuova);
            }
        });

        objectAreaPanel = new ObjectAreaPanel();
        objectAreaPanel.setSongListener(new SongListener() {
            @Override
            public Feedback guardaFeedback(Song song) throws NoFeedbackException, SQLException, RemoteException {
                return resourceManager.getFeedback(song);
            }
            @Override
            public void addSong(Song song) throws SQLException, playlistException, RemoteException {
                LinkedList<Playlist> userPlaylists = user.getPlaylistList();
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
                int scelta = JOptionPane.showConfirmDialog(java.awt.Frame.getFrames()[0], panel, "seleziona playlist", JOptionPane.OK_CANCEL_OPTION);
                if(scelta == 0 && listaPlaylists.getSelectedIndex() != -1){
                    String plName = (String)listaPlaylists.getSelectedItem();
                    Playlist p = user.getPlaylist(plName);
                    resourceManager.addSongToPlaylist(plName, song, user);
                    p.addSong(song);
                    playlistPanel.logged(user);
                }
            }
            @Override
            public void removeSong(Song song) throws SQLException, playlistException, RemoteException {
                Playlist playlist = playlistPanel.getPlaylist();
                resourceManager.removeSongFromPlaylist(playlist.getPlaylistName(), song, user);
                playlist.removeSong(song);
                playlistPanel.logged(user);
                objectAreaPanel.pulisciArea();
                objectAreaPanel.inserisciBrani(playlist.getSongList());
                objectAreaPanel.setSongOfPlaylist(true);
            }
            @Override
            public void valutaSong(FeedbackForm ff) throws UserException, SQLException, AlreadyValuedException, RemoteException {
                resourceManager.evaluateSong(ff.emotions, user, ff.song, String.valueOf(ff.score), ff.notes);
            }
        });

        searchPanel = new SearchPanel();
        searchPanel.setCercaListener(new SearchListener() {
            @Override
            public void cercaEventListener(SearchEvent ce) {
                String testo = ce.getTesto();
                String tipoRicerca = ce.getTipoRicerca();
                LinkedList<Song> tmp;
                if (tipoRicerca.equals("title")) {
                    try {
                        tmp = resourceManager.findSong(testo);
                    } catch (RemoteException e) {
                        return;
                    }
                } else {
                    try {
                        tmp = resourceManager.findSong(testo, ce.getYear());
                    } catch (RemoteException e) {
                        return;
                    }
                }
                objectAreaPanel.pulisciArea();
                objectAreaPanel.inserisciBrani(tmp);
                objectAreaPanel.setSongOfPlaylist(false);
                objectAreaPanel.setLogged(logged);
            }
        });

        playlistPanel = new PlaylistPanel();
        playlistPanel.setPlaylistListener(new PlaylistListener() {
            @Override
            public void creaPlaylist(String plName) throws SQLException, playlistException, RemoteException {
                Playlist nuova = resourceManager.createPlaylist(plName, user);
                user.addPlaylist(nuova);
                playlistPanel.logged(user);
            }
            @Override
            public void eliminaPlaylist(String plName) throws SQLException, playlistException, RemoteException {
                resourceManager.deletePlaylist(plName, user);
                user.deletePlaylist(plName);
                objectAreaPanel.pulisciArea();
                playlistPanel.logged(user);
            }
            @Override
            public void apriPlaylist(String plName) {
                Playlist playlist = user.getPlaylist(plName);
                LinkedList<Song> tmp = playlist.getSongList();
                playlistPanel.setPlaylist(playlist);
                objectAreaPanel.pulisciArea();
                objectAreaPanel.inserisciBrani(tmp);
                objectAreaPanel.setSongOfPlaylist(true);
            }
            @Override
            public void rinominaPlaylist(String vecchioNome, String nuovoNome) throws SQLException, playlistException, RemoteException {
                resourceManager.renamePlaylist(vecchioNome, nuovoNome, user);
                user.getPlaylist(vecchioNome).setPlaylistName(nuovoNome);
                playlistPanel.logged(user);
            }
        });

        panel = new JPanel(new BorderLayout());
        panel.add(searchPanel, BorderLayout.PAGE_START);
        panel.add(playlistPanel, BorderLayout.CENTER);

        add(objectAreaPanel, BorderLayout.CENTER);
        add(toolBar, BorderLayout.PAGE_START);
        add(panel, BorderLayout.LINE_START);

        setBackground(backDark);
        panel.setBackground(backDark);
        setSize(800, 500);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int scelta = JOptionPane.showConfirmDialog(java.awt.Frame.getFrames()[0], "Continuare?",
                        "Chiusura applicazione", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (scelta == 0) {
                    if (user instanceof LoggedUser)
                        try {
                            user = resourceManager.logout(user);
                        } catch (UserException ex) {
                        } catch (RemoteException ex) {
                        }
                    System.exit(0);
                }
            }
        });
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

}
