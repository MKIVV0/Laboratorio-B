package user;

import common.AbstractUser;
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

public class PannelloPlaylist extends JPanel {

    private JList listaPlaylist;
    private JPanel tasti;
    private JButton bottoneApri, bottoneCrea, bottoneElimina;
    private PlaylistListener playlistListener;
    private Playlist playlist;

    PannelloPlaylist() {
        setPreferredSize(new Dimension(300, 50));//100
        setLayout(new BorderLayout());
        setVisible(false);

        // Bordi
        Border bordoInterno = BorderFactory.createTitledBorder("le tue playlist");
        Border bordoEsterno = BorderFactory.createEmptyBorder(0, 5, 5, 5);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);

        setBorder(bordoFinale);

        listaPlaylist = new JList<Playlist>();
        listaPlaylist.setBorder(BorderFactory.createEtchedBorder());

        tasti = new JPanel(new FlowLayout(FlowLayout.LEFT));

        bottoneApri = new JButton("Apri");
        bottoneApri.setVisible(false);
        bottoneCrea = new JButton("Crea");
        bottoneElimina = new JButton("Elimina");
        bottoneElimina.setVisible(false);

        listaPlaylist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(listaPlaylist.isSelectionEmpty()){
                    bottoneApri.setVisible(false);
                    bottoneElimina.setVisible(false);
                }
                else {
                    bottoneApri.setVisible(true);
                    bottoneElimina.setVisible(true);
                }
            }
        });

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
            DefaultListModel modelloPlaylists = new DefaultListModel();
            for (Playlist p : ((LoggedUser) user).getPlaylistList())
                modelloPlaylists.addElement(p.getPlaylistName());
            listaPlaylist.setModel(modelloPlaylists);
            setVisible(true);
        } else {
            listaPlaylist.setModel(new DefaultListModel());
            setVisible(false);
        }
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylistListener(PlaylistListener playlistListener) {

        this.playlistListener = playlistListener;

    }

}
