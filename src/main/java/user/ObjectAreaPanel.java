package user;

import common.*;

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
    private JButton showFeedback, aggiungi, valuta, togli;
    private SongListener songListener;

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
        togli = new JButton("Togli");

        isSongOfPlaylist(false);
        logged(false);

        showFeedback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(songResultSet.getSelectedIndex() != -1){
                    Song song = (Song) songResultSet.getSelectedValue();
                    if(songListener != null) {
                        try {
                            LinkedList<String> feedbacks = songListener.guardaFeedback(song);
                            JPanel panel = new JPanel(new BorderLayout(5, 5));
                            JTextArea textArea = new JTextArea();
                            panel.add(textArea, BorderLayout.CENTER);
                            String feedbackString = "";
                            for (String s : feedbacks) {
                                feedbackString += s + "\n";
                                textArea.append(feedbackString);
                            }
                            JOptionPane.showConfirmDialog(Frame.getFrames()[0], panel, "feedbacks", JOptionPane.OK_CANCEL_OPTION);
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

        aggiungi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(songResultSet.getSelectedIndex() != -1){
                    Song song = (Song) songResultSet.getSelectedValue();
                    if(songListener != null)
                        try {
                            songListener.addSong(song);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Brano gia presente");
                        } catch (playlistException ex) {
                            throw new RuntimeException(ex);
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                }
            }
        });

        togli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(songResultSet.getSelectedIndex() != -1){
                    Song song = (Song) songResultSet.getSelectedValue();
                    if(songListener != null)
                        try {
                            songListener.removeSong(song);
                        } catch (SQLException ex) {
                        } catch (playlistException ex) {
                            throw new RuntimeException(ex);
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                }
            }
        });

        valuta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(songResultSet.getSelectedIndex() != -1){
                    JPanel panel = new JPanel(new BorderLayout(5, 5));

                    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                    label.add(new JLabel("Emotion", SwingConstants.RIGHT));
                    label.add(new JLabel("Score", SwingConstants.RIGHT));
                    label.add(new JLabel("Notes", SwingConstants.RIGHT));

                    panel.add(label, BorderLayout.WEST);

                    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));

                    Emotions[] emotions = Emotions.values();
                    JComboBox listaEmotions = new JComboBox(emotions);
                    listaEmotions.setSelectedIndex(1);
                    JSpinner score = new JSpinner();
                    SpinnerNumberModel modelloSpinner = new SpinnerNumberModel(1, 1, 5, 1);
                    score.setModel(modelloSpinner);
                    JTextArea notes = new JTextArea();

                    controls.add(listaEmotions);
                    controls.add(score);
                    controls.add(notes);

                    panel.add(controls, BorderLayout.CENTER);

                    int scelta = JOptionPane.showConfirmDialog(Frame.getFrames()[0], panel, "valutazione", JOptionPane.OK_CANCEL_OPTION);
                    if(scelta == 0){
                        Emotions em = (Emotions) listaEmotions.getSelectedItem();
                        Song song = (Song) songResultSet.getSelectedValue();
                        int sc = (int)score.getValue();
                        String note = notes.getText();
                        FeedbackForm ff = new FeedbackForm(em, song, sc, note);
                        if(songListener != null)
                            try{
                                songListener.valutaSong(ff);
                            } catch (NotLoggedException ex) {
                                throw new RuntimeException(ex);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "sql");
                            } catch (AlreadyValuedException ex) {
                                JOptionPane.showMessageDialog(null, "already valued");
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
        tasti.add(togli);

        add(new JScrollPane(songResultSet), BorderLayout.CENTER);
        add(tasti, BorderLayout.SOUTH);

    }

    public void isSongOfPlaylist(boolean songOfPlaylist) {
        if(songOfPlaylist){
            aggiungi.setVisible(false);
            togli.setVisible(true);
            valuta.setVisible(true);
        } else {
            aggiungi.setVisible(true);
            togli.setVisible(false);
            valuta.setVisible(false);
        }
    }

    public void logged(boolean logged){
        if(logged)
            aggiungi.setVisible(true);
        else
            aggiungi.setVisible(false);
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


    public void setSongListener(SongListener songListener) {
        this.songListener = songListener;
    }
}
