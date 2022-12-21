package user;

import common.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.LinkedList;

public class PannelloPlaylist extends JPanel {

    private JList listaPlaylist;
//    private LinkedList<Playlist> playlists;
    private JPanel tasti;
    private JButton bottoneApri, bottoneCrea, bottoneElimina;
    private PlaylistListener playlistListener;

    PannelloPlaylist() {
        setPreferredSize(new Dimension(300, 50));//100
        setLayout(new BorderLayout());
        setVisible(false);

        // Bordi
        Border bordoInterno = BorderFactory.createTitledBorder("le tue playlist");
        Border bordoEsterno = BorderFactory.createEmptyBorder(0, 5, 5, 5);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);

        setBorder(bordoFinale);

//        playlists = new LinkedList<>();

        listaPlaylist = new JList<Playlist>();
        listaPlaylist.setBorder(BorderFactory.createEtchedBorder());

        tasti = new JPanel(new FlowLayout(FlowLayout.LEFT));

        bottoneApri = new JButton("Apri");
        bottoneCrea = new JButton("Crea");
        bottoneElimina = new JButton("Elimina");

        bottoneCrea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new BorderLayout(5, 5));
                JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                label.add(new JLabel("Nome", SwingConstants.RIGHT));
                panel.add(label, BorderLayout.WEST);
                JPanel controls = new JPanel(new GridLayout());
                JTextField playlistName = new JTextField();
                controls.add(playlistName);
                panel.add(controls, BorderLayout.CENTER);
                int scelta = JOptionPane.showConfirmDialog(Frame.getFrames()[0], panel, "nuova playlist", JOptionPane.OK_CANCEL_OPTION);
                if (scelta == 0) {
                    String plName = playlistName.getText();
                    if (plName.equals("")) {
                        JOptionPane.showMessageDialog(null, "Please insert Playlist name");
                    } else
                        if (playlistListener != null)
                            try {
                                playlistListener.creaPlaylist(plName);
                                JOptionPane.showMessageDialog(null, "Playlist " + plName + " creata");
                            } catch (RemoteException ex) {
                                JOptionPane.showMessageDialog(null, "remote");
                            } catch (playlistException ex){
                                JOptionPane.showMessageDialog(null, "Playlist esistente");
                            } catch (SQLException ex){
                                JOptionPane.showMessageDialog(null, "sql");
                            }
                }
            }
        });

        bottoneElimina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!listaPlaylist.isSelectionEmpty()){
                    String nomePlaylist = (String)listaPlaylist.getSelectedValue();
                    String domanda = "Eliminare " + nomePlaylist + " ?";
                    int scelta = JOptionPane.showConfirmDialog(Frame.getFrames()[0], domanda, "elimina playlist", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (scelta == 0) {
                        if(playlistListener != null)
                            try{
                                playlistListener.eliminaPlaylist(nomePlaylist);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "sql");
                            } catch (playlistException ex) {
                                JOptionPane.showMessageDialog(null, "pl");
                            } catch (RemoteException ex) {
                                JOptionPane.showMessageDialog(null, "remote");
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

        tasti.add(bottoneApri);
        tasti.add(bottoneCrea);
        tasti.add(bottoneElimina);

        add(new JScrollPane(listaPlaylist), BorderLayout.CENTER);
        add(tasti, BorderLayout.SOUTH);

    }

    public void logged(AbstractUser user) {
        if (user instanceof LoggedUser) {
//            playlists = ((LoggedUser) user).getPlaylistList();
            DefaultListModel modelloPlaylists = new DefaultListModel();
            for (Playlist p : ((LoggedUser) user).getPlaylistList())
                modelloPlaylists.addElement(p.getPlaylistName());
            listaPlaylist.setModel(modelloPlaylists);
            setVisible(true);
        } else {
//            playlists = new LinkedList<>();
            listaPlaylist.setModel(new DefaultListModel());
            setVisible(false);
        }
    }

    public void setPlaylistListener(PlaylistListener playlistListener) {

        this.playlistListener = playlistListener;

    }

}
