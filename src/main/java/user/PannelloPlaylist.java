package user;

import common.AbstractUser;
import common.LoggedUser;
import common.Playlist;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.LinkedList;

public class PannelloPlaylist extends JPanel {

    private JLabel labelPlaylist;
    private JList listaPlaylist;

    private JButton bottoneApri;

    PannelloPlaylist(Frame frame) {
        setPreferredSize(new Dimension(300, 50));//100
        setLayout(new GridBagLayout());

        // Bordi
        Border bordoInterno = BorderFactory.createTitledBorder("area personale");
        Border bordoEsterno = BorderFactory.createEmptyBorder(0, 5, 5, 5);
        Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);

        setBorder(bordoFinale);

        //label
        labelPlaylist = new JLabel("Playlist: ");

        //lista
        listaPlaylist = new JList<Playlist>();
        listaPlaylist.setBorder(BorderFactory.createEtchedBorder());
        DefaultListModel modelloPlaylists = new DefaultListModel<Playlist>();

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

        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.insets = new Insets(0, 0, 0, 5);

        add(labelPlaylist, gbc);

        // RIGA 0
        gbc.gridx = 1;
        gbc.gridy = 0;

        gbc.weightx = 0.01;
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

        DefaultListModel modelloPlaylists = new DefaultListModel<Playlist>();

        if(user instanceof LoggedUser) {
            LinkedList<Playlist> tmp = ((LoggedUser) user).getPlaylistList();
            for (Playlist p : tmp)
                modelloPlaylists.addElement(p);
        }

        listaPlaylist.setModel(modelloPlaylists);
    }

}
