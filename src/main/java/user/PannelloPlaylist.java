package user;

import common.AbstractUser;
import common.LoggedUser;
import common.Playlist;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.LinkedList;

public class PannelloPlaylist extends JPanel {

    private JList listaPlaylist;

    private JPanel tasti;
    private JButton bottoneApri;

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

        tasti.add(bottoneApri);

        add(new JScrollPane(listaPlaylist), BorderLayout.CENTER);
        add(tasti, BorderLayout.SOUTH);

    }

    public void logged(AbstractUser user) {
        if (user instanceof LoggedUser) {
            DefaultListModel modelloPlaylists = new DefaultListModel<Playlist>();
            LinkedList<Playlist> tmp = ((LoggedUser) user).getPlaylistList();
            for (Playlist p : tmp)
                modelloPlaylists.addElement(p);
            listaPlaylist.setModel(modelloPlaylists);
            setVisible(true);
        } else {
            listaPlaylist.setModel(new DefaultListModel());
            setVisible(false);
        }
    }
        /*setPreferredSize(new Dimension(300, 50));//100
        setLayout(new GridBagLayout());

        // Bordi
        Border bordoInterno = BorderFactory.createTitledBorder("area personale");
        Border bordoEsterno = BorderFactory.createEmptyBorder(0, 5, 5, 5);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);

        setBorder(bordoFinale);

        //label
        labelPlaylist = new JLabel("Playlist: ");
//password123
        //lista
        listaPlaylist = new JList<Playlist>();
//        listaPlaylist.setPreferredSize(new Dimension(170, 55));
        listaPlaylist.setBorder(BorderFactory.createEtchedBorder());
//        DefaultListModel modelloPlaylists = new DefaultListModel<Playlist>();

        if(frame.user instanceof LoggedUser) {
            LinkedList<Playlist> tmp = ((LoggedUser) frame.user).getPlaylistList();
            for (Playlist p : tmp)
                modelloPlaylists.addElement(p);
        }

        listaPlaylist.setModel(modelloPlaylists);

        //button
        bottoneApri = new JButton("Apri");

        //layout
        GridBagConstraints gbc = new GridBagConstraints();

        // RIGA 0
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 0.01;
        gbc.weighty = 0.03;

        gbc.anchor = GridBagConstraints.FIRST_LINE_END;

        gbc.insets = new Insets(5, 0, 0, 5);

        add(labelPlaylist, gbc);

        // RIGA 0
        gbc.gridx = 1;
        gbc.gridy = 0;

        gbc.weightx = 0.1;
        gbc.weighty = 0.03;

        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.insets = new Insets(0, 0, 0, 0);

        add(new JScrollPane(listaPlaylist), gbc);

        // RIGA 1
        gbc.gridx = 0;
        gbc.gridy = 1;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.gridwidth = 2;
        gbc.gridheight = 1;

        gbc.anchor = GridBagConstraints.PAGE_START;

        gbc.insets = new Insets(0, 0, 0, 0);

        add(bottoneApri, gbc);
    }

    public void refresh(AbstractUser user){
        if(user instanceof LoggedUser) {
            DefaultListModel modelloPlaylists = new DefaultListModel<Playlist>();
            LinkedList<Playlist> tmp = ((LoggedUser) user).getPlaylistList();
            if(tmp.isEmpty()){
                listaPlaylist.setModel(new DefaultListModel());
            }else {
                for (Playlist p : tmp)
                    modelloPlaylists.addElement(p);
                listaPlaylist.setModel(modelloPlaylists);
            }
        } else {
            listaPlaylist.setModel(new DefaultListModel());
        }
    }*/

}
