package user;

import common.LoggedUser;
import common.NoFeedbackException;
import common.Song;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.LinkedList;

public class ObjectAreaPanel extends JPanel {

    private JList songResultSet;
    private JPanel tasti;
    private JButton showFeedback, aggiungi, valuta;

    public ObjectAreaPanel(Frame frame){

        setLayout(new BorderLayout());

        songResultSet = new JList<Song>();
        songResultSet.setBorder(BorderFactory.createEtchedBorder());

        tasti = new JPanel(new FlowLayout(FlowLayout.LEFT));

        showFeedback = new JButton("Feedbacks");
        showFeedback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(songResultSet.getSelectedIndex() != -1){
                    Song song = (Song) songResultSet.getSelectedValue();
                    try{
                        LinkedList<String> feedbacks = frame.resourceManager.getFeedback(song);
                        String feedbackString = "";
                        for (String s: feedbacks){
                            feedbackString += s + "\n";
                        }
                        JOptionPane.showMessageDialog(null, feedbackString);
                    } catch (NoFeedbackException ex) {
                        JOptionPane.showMessageDialog(null, "No feedbacks for this song!");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        aggiungi = new JButton("Aggiungi");

        valuta = new JButton("Valuta");
        valuta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        tasti.add(showFeedback);
        tasti.add(aggiungi);
        tasti.add(valuta);

        logged(frame.user instanceof LoggedUser);

        add(new JScrollPane(songResultSet), BorderLayout.CENTER);
        add(tasti, BorderLayout.SOUTH);

    }

    public void inserisciBrani(LinkedList<Song> songs) {
        DefaultListModel modelloSongs = new DefaultListModel<Song>();
        for(Song s: songs)
            modelloSongs.addElement(s);
        songResultSet.setModel(modelloSongs);
    }

    public void pulisciArea() {

        songResultSet.setModel(new DefaultListModel());

    }

    public void logged(boolean logged){
        if(logged){
            aggiungi.setVisible(true);
            valuta.setVisible(true);
        } else {
            aggiungi.setVisible(false);
            valuta.setVisible(false);
        }
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
