package user;

import common.Song;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Vector;

public class ObjectAreaPanel extends JPanel {

    private JList songResultSet;
//    private JComboBox<Song> songResultSet;

    public ObjectAreaPanel(){

        songResultSet = new JList<Song>();
//        songResultSet = new JComboBox<>();

        songResultSet.setBorder(BorderFactory.createEtchedBorder());

        setLayout(new BorderLayout());

        add(new JScrollPane(songResultSet), BorderLayout.CENTER);

    }

    public void inserisciBrani(LinkedList<Song> songs) {

        DefaultListModel modelloSongs = new DefaultListModel<Song>();
//        int i = 0;
        for(Song s: songs) {
            modelloSongs.addElement(s);
        }

        songResultSet.setModel(modelloSongs);

    }

    public void pulisciArea() {

        songResultSet.setModel(new DefaultListModel());

    }
}

/*listaBagagliaio = new JList();
        listaBagagliaio.setPreferredSize(new Dimension(170, 55));
        listaBagagliaio.setBorder(BorderFactory.createEtchedBorder());

        DefaultListModel modelloBagagliaio = new DefaultListModel();
        modelloBagagliaio.addElement(new Bagagliaio(0, "Piccolo"));
        modelloBagagliaio.addElement(new Bagagliaio(1, "Medio"));
        modelloBagagliaio.addElement(new Bagagliaio(2, "Grande"));

        listaBagagliaio.setModel(modelloBagagliaio);*/
