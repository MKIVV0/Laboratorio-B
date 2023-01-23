/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import common.LoggedUser;
import common.Playlist;
import common.playlistException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * This class provides the user's playlists management funtions
 */
public class PlaylistPanel extends JPanel {

    /**
     * This attribute represents the song list that has to be shown on the screen
     */
    private JList listaPlaylist;
    /**
     * this attribute contains the buttons of the class
     */
    private JPanel tasti;
    /**
     * this attribute represents the openPlaylist button
     */
    private JButton bottoneApri;
    /**
     * this attribute represents the createPlaylist button
     */
    private JButton bottoneCrea;
    /**
     * this attribute represents the deletePlaylist button
     */
    private JButton bottoneElimina;
    /**
     * this attribute represents the renamePlaylist button
     */
    private JButton bottoneRinomina;
    /**
     * this attribute represents an entity that waits for some user interaction with the class buttons
     */
    private PlaylistListener playlistListener;
    /**
     * this attribute represents the interested user's playlist
     */
    private Playlist playlist;
    /**
     * these attributes represent the JPanel's borders
     */
    private Border bordoInterno, bordoEsterno, bordoFinale;

    /**
     * Empty constructor, initializes its components.
     */
    PlaylistPanel() {
        setLayout(new BorderLayout());
        setVisible(false);

        Border b = BorderFactory.createMatteBorder(1,0,0,0,Color.PINK);
        bordoInterno = BorderFactory.createTitledBorder(b,"Your playlists",0, 2,
                new Font("Geneva", Font.BOLD, 12),Frame.compForeDark);
        bordoEsterno = BorderFactory.createEmptyBorder(0, 5, 5, 5);
        bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);

        setBorder(bordoFinale);

        listaPlaylist = new JList<Playlist>();
        listaPlaylist.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

        listaPlaylist.setSelectionForeground(Color.PINK);
        listaPlaylist.setSelectionBackground(Frame.backDark);
        listaPlaylist.setFocusable(false);

        JScrollPane scrollPane = new JScrollPane(listaPlaylist);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBar(new ScrollBar());

        tasti = new JPanel(new FlowLayout(FlowLayout.LEFT));

        bottoneApri = new JButton("Open");
        bottoneApri.setVisible(false);
        bottoneCrea = new JButton("New");
        bottoneElimina = new JButton("Delete");
        bottoneElimina.setVisible(false);
        bottoneRinomina = new JButton("Rename");
        bottoneRinomina.setVisible(false);

        listaPlaylist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(listaPlaylist.isSelectionEmpty()){
                    bottoneApri.setVisible(false);
                    bottoneElimina.setVisible(false);
                    bottoneRinomina.setVisible(false);
                }
                else {
                    bottoneApri.setVisible(true);
                    bottoneElimina.setVisible(true);
                    bottoneRinomina.setVisible(true);
                }
            }
        });

        bottoneCrea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new BorderLayout(5, 5));
                JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                label.add(new JLabel("Name", SwingConstants.RIGHT));
                panel.add(label, BorderLayout.WEST);
                JPanel controls = new JPanel(new GridLayout());
                JTextField playlistName = new JTextField();
                controls.add(playlistName);
                panel.add(controls, BorderLayout.CENTER);
                int scelta = JOptionPane.showConfirmDialog(Frame.getFrames()[0], panel, "new playlist", JOptionPane.OK_CANCEL_OPTION);
                if (scelta == 0) {
                    String plName = playlistName.getText();
                    if (plName.equals("")) {
                        JOptionPane.showMessageDialog(null, "Please insert Playlist name");
                    } else
                        if (playlistListener != null)
                            try {
                                playlistListener.creaPlaylist(plName);
                                JOptionPane.showMessageDialog(null, "Playlist " + plName + " created");
                            } catch (RemoteException ex) {
                            } catch (playlistException ex){
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                            } catch (SQLException ex){
                            }
                }
            }
        });

        bottoneElimina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!listaPlaylist.isSelectionEmpty()){
                    String nomePlaylist = (String)listaPlaylist.getSelectedValue();
                    String domanda = "Delete " + nomePlaylist + " ?";
                    int scelta = JOptionPane.showConfirmDialog(Frame.getFrames()[0], domanda, "delete playlist", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (scelta == 0) {
                        if(playlistListener != null)
                            try{
                                playlistListener.eliminaPlaylist(nomePlaylist);
                            } catch (SQLException ex) {
                            } catch (playlistException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                            } catch (RemoteException ex) {
                            }
                    }
                }
            }
        });

        bottoneApri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!listaPlaylist.isSelectionEmpty())
                    if(playlistListener != null)
                        playlistListener.apriPlaylist((String)listaPlaylist.getSelectedValue());
            }
        });

        bottoneRinomina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new BorderLayout(5, 5));
                JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                label.add(new JLabel("Name", SwingConstants.RIGHT));
                panel.add(label, BorderLayout.WEST);
                JPanel controls = new JPanel(new GridLayout());
                JTextField playlistName = new JTextField();
                controls.add(playlistName);
                panel.add(controls, BorderLayout.CENTER);
                int scelta = JOptionPane.showConfirmDialog(Frame.getFrames()[0], panel, "rename playlist", JOptionPane.OK_CANCEL_OPTION);
                if (scelta == 0) {
                    String nuovoNome = playlistName.getText();
                    String vecchioNome = (String)listaPlaylist.getSelectedValue();
                    if (nuovoNome.equals("")) {
                        JOptionPane.showMessageDialog(null, "Please insert Playlist name");
                    } else if (playlistListener != null)
                        try {
                            playlistListener.rinominaPlaylist(vecchioNome, nuovoNome);
                            JOptionPane.showMessageDialog(null, "Playlist renamed");
                        } catch (RemoteException ex) {
                        } catch (playlistException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        } catch (SQLException ex) {
                        }
                }
            }
        });

        tasti.add(bottoneApri);
        tasti.add(bottoneCrea);
        tasti.add(bottoneRinomina);
        tasti.add(bottoneElimina);

        add(scrollPane, BorderLayout.CENTER);
        add(tasti, BorderLayout.SOUTH);

        setColor(Frame.backDark, Frame.compBackDark, Frame.compForeDark);

    }

    /**
     * Set the visibility based on the parameter.
     * @param user
     */
    public void logged(LoggedUser user) {
        if (user != null) {
            DefaultListModel modelloPlaylists = new DefaultListModel();
            for (Playlist p : user.getPlaylistList())
                modelloPlaylists.addElement(p.getPlaylistName());
            listaPlaylist.setModel(modelloPlaylists);
            setVisible(true);
        } else {
            listaPlaylist.setModel(new DefaultListModel());
            setVisible(false);
        }
    }

    /**
     * playlist setter.
     * @param playlist
     */
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    /**
     * playlist getter.
     * @return  playlist
     */
    public Playlist getPlaylist() {
        return playlist;
    }

    /**
     * Set the background color and the component's background and foreground.
     * @param back
     * @param compBack
     * @param compFore
     */
    public void setColor(Color back, Color compBack, Color compFore){
        listaPlaylist.setBackground(compBack);
        listaPlaylist.setForeground(compFore);
        for(Component c: getComponents()){
            c.setBackground(compBack);
            c.setForeground(compFore);
        }
        for(Component c: tasti.getComponents()){
            c.setBackground(compBack);
            c.setForeground(compFore);
            c.setFocusable(false);
        }
        tasti.setBackground(back);
        setBackground(back);
    }

    /**
     * playlistListener setter.
     * @param playlistListener
     */
    public void setPlaylistListener(PlaylistListener playlistListener) {
        this.playlistListener = playlistListener;
    }

}
