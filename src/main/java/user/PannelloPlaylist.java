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

public class PannelloPlaylist extends JPanel {

    private JList listaPlaylist;
    private JPanel tasti;
    private JButton bottoneApri, bottoneCrea, bottoneElimina, bottoneRinomina;
    private PlaylistListener playlistListener;
    private Playlist playlist;
    private Border bordoInterno, bordoEsterno, bordoFinale;

    PannelloPlaylist() {
        setLayout(new BorderLayout());
        setVisible(false);

        Border b = BorderFactory.createMatteBorder(1,0,0,0,Color.PINK);
        bordoInterno = BorderFactory.createTitledBorder(b,"Le tue playlist",0, 2,
                new Font("Geneva", Font.BOLD, 12),Frame.compForeDark);
        bordoEsterno = BorderFactory.createEmptyBorder(0, 5, 5, 5);
        bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);

        setBorder(bordoFinale);

        listaPlaylist = new JList<Playlist>();
        listaPlaylist.setBorder(BorderFactory.createEtchedBorder());

        tasti = new JPanel(new FlowLayout(FlowLayout.LEFT));

        bottoneApri = new JButton("Apri");
        bottoneApri.setVisible(false);
        bottoneCrea = new JButton("Crea");
        bottoneElimina = new JButton("Elimina");
        bottoneElimina.setVisible(false);
        bottoneRinomina = new JButton("Rinomina");
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

        bottoneRinomina.addActionListener(new ActionListener() {
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
                int scelta = JOptionPane.showConfirmDialog(Frame.getFrames()[0], panel, "rinomina playlist", JOptionPane.OK_CANCEL_OPTION);
                if (scelta == 0) {
                    String nuovoNome = playlistName.getText();
                    String vecchioNome = (String)listaPlaylist.getSelectedValue();
                    if (nuovoNome.equals("")) {
                        JOptionPane.showMessageDialog(null, "Please insert Playlist name");
                    } else if (playlistListener != null)
                        try {
                            playlistListener.rinominaPlaylist(vecchioNome, nuovoNome);
                            JOptionPane.showMessageDialog(null, "Playlist rinominata");
                        } catch (RemoteException ex) {
                            JOptionPane.showMessageDialog(null, "remote");
                        } catch (playlistException ex) {
                            JOptionPane.showMessageDialog(null, "Playlist ex");
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "sql");
                        }
                }
            }
        });

        tasti.add(bottoneCrea);
        tasti.add(bottoneApri);
        tasti.add(bottoneRinomina);
        tasti.add(bottoneElimina);

        add(new JScrollPane(listaPlaylist), BorderLayout.CENTER);
        add(tasti, BorderLayout.SOUTH);

        setColor(Frame.backDark, Frame.compBackDark, Frame.compForeDark);

    }

    public void logged(LoggedUser user) {
        if (user != null) {
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

    public void setPlaylistListener(PlaylistListener playlistListener) {
        this.playlistListener = playlistListener;
    }

}
