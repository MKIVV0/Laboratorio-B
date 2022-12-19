package user;

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
    private FeedbackListener feedbackListener;

    public ObjectAreaPanel(){
        setLayout(new BorderLayout());

        // JLIST
        songResultSet = new JList<Song>();
        songResultSet.setBorder(BorderFactory.createEtchedBorder());

        // PANNELLO DEI TASTI
        tasti = new JPanel(new FlowLayout(FlowLayout.LEFT));

        showFeedback = new JButton("Feedbacks");
        aggiungi = new JButton("Aggiungi");
        valuta = new JButton("Valuta");

        aggiungi.setVisible(false);
        valuta.setVisible(false);

        showFeedback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(songResultSet.getSelectedIndex() != -1){
                    Song song = (Song) songResultSet.getSelectedValue();
                    if(feedbackListener != null) {
                        try {
//                            LinkedList<String> feedbacks = frame.resourceManager.getFeedback(song);
                            LinkedList<String> feedbacks = feedbackListener.guardaFeedback(song);
                            String feedbackString = "";
                            for (String s : feedbacks) {
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
            }
        });

        tasti.add(showFeedback);
        tasti.add(aggiungi);
        tasti.add(valuta);

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

    public void setFeedbackListener(FeedbackListener feedbackListener) {
        this.feedbackListener = feedbackListener;
    }
}
