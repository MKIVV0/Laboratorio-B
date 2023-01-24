/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import common.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This class purpose is to print the songs on screen
 */
public class ObjectAreaPanel extends JPanel {

    /**
     * This attribute represents the song list that has to be shown on the screen
     */
    private JList songResultSet;
    /**
     * The list of the song's feedback
     */
    private JTextArea textArea;
    /**
     * this attribute contains the buttons of the class
     */
    private JPanel tasti;
    /**
     * this attribute represents the showFeedback button
     */
    private JButton bottoneShowFeedback;
    /**
     * this attribute represents the addToPlaylist button
     */
    private JButton bottoneAggiungi;
    /**
     * this attribute represents the evaluate button
     */
    private JButton bottoneValuta;
    /**
     * this attribute represents the removeSong button
     */
    private JButton bottoneTogli;
    /**
     * this attribute represents an entity that waits for some user interaction with the class buttons
     */
    private SongListener songListener;
    /**
     * this attribute represents the user's state
     */
    private boolean logged;
    /**
     * this attribute tells if the song list is the content of a playlist
     */
    private boolean songOfPlatlist;

    /**
     * Empty constructor, initializes its components.
     */
    public ObjectAreaPanel(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        textArea.setBackground(Frame.backDark);
        textArea.setForeground(Frame.compForeDark);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        JScrollPane scrollPane2 = new JScrollPane(textArea);
        scrollPane2.setPreferredSize(new Dimension(300,600));
        scrollPane2.setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.PINK));
        scrollPane2.setVerticalScrollBar(new ScrollBar());

        songResultSet = new JList<Song>();
        songResultSet.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
        songResultSet.setSelectionForeground(Color.PINK);
        songResultSet.setSelectionBackground(Frame.backDark);
        songResultSet.setFocusable(false);
        JScrollPane scrollPane = new JScrollPane(songResultSet);
        scrollPane.setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.PINK));
        scrollPane.setVerticalScrollBar(new ScrollBar());

        tasti = new JPanel(new FlowLayout(FlowLayout.LEFT));

        bottoneShowFeedback = new JButton("Feedbacks");
        bottoneAggiungi = new JButton("Add");
        bottoneValuta = new JButton("Evaluate");
        bottoneTogli = new JButton("Remove");
        bottoneShowFeedback.setVisible(false);
        bottoneAggiungi.setVisible(false);
        bottoneValuta.setVisible(false);
        bottoneTogli.setVisible(false);

        logged = false;
        songOfPlatlist = false;

        songResultSet.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(songResultSet.isSelectionEmpty()) {
                    bottoneShowFeedback.setVisible(false);
                    bottoneAggiungi.setVisible(false);
                    bottoneTogli.setVisible(false);
                    bottoneValuta.setVisible(false);
                } else {
                    bottoneShowFeedback.setVisible(true);
                    bottoneAggiungi.setVisible(logged && !songOfPlatlist);
                    bottoneTogli.setVisible(logged && songOfPlatlist);
                    bottoneValuta.setVisible(logged && songOfPlatlist);
                }
            }
        });

        bottoneShowFeedback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(songResultSet.getSelectedIndex() != -1){
                    Song song = (Song) songResultSet.getSelectedValue();
                    if(songListener != null) {
                        try {
//                            Feedback feedbacks = songListener.guardaFeedback(song);
//                            JTextArea textArea = new JTextArea();
//                            textArea.append(feedbacks.toString());
//                            textArea.setEditable(false);
//                            JFrame f = new JFrame(song.getTitle() + "'s feedbacks");
//                            f.setSize(500, 400);
//                            f.add(new JScrollPane(textArea), BorderLayout.CENTER);
//                            f.setLocationRelativeTo(null);
//                            f.addWindowListener(new WindowAdapter() {
//                                @Override
//                                public void windowClosing(WindowEvent e) {
//                                    f.dispose();
//                                }
//                            });
//                            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//                            f.setVisible(true);
                            Feedback feedbacks = songListener.guardaFeedback(song);
                            textArea.setText(feedbacks.toString());
                        } catch (NoFeedbackException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        } catch (SQLException ex) {
                        } catch (RemoteException ex) {
                        }
                    }
                }
            }
        });
        bottoneAggiungi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(songResultSet.getSelectedIndex() != -1){
                    Song song = (Song) songResultSet.getSelectedValue();
                    if(songListener != null)
                        try {
                            songListener.addSong(song);
                            JOptionPane.showMessageDialog(null, "Song added successfully!");
                        } catch (SQLException ex) {
                        } catch (playlistException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        } catch (RemoteException ex) {
                        }
                }
            }
        });
        bottoneTogli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(songResultSet.getSelectedIndex() != -1){
                    Song song = (Song) songResultSet.getSelectedValue();
                    if(songListener != null)
                        try {
                            songListener.removeSong(song);
                        } catch (SQLException ex) {
                        } catch (playlistException ex) {
                        } catch (RemoteException ex) {
                        }
                }
            }
        });
        bottoneValuta.addActionListener(new ActionListener() {
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
                    int scelta = JOptionPane.showConfirmDialog(Frame.getFrames()[0], panel, "evaluation", JOptionPane.OK_CANCEL_OPTION);
                    if(scelta == 0){
                        Emotions em = (Emotions) listaEmotions.getSelectedItem();
                        Song song = (Song) songResultSet.getSelectedValue();
                        int sc = (int)score.getValue();
                        String note = notes.getText();
                        FeedbackForm ff = new FeedbackForm(em, song, sc, note);
                        if(songListener != null)
                            try{
                                songListener.valutaSong(ff);
                                JOptionPane.showMessageDialog(null, "Thanks for your feedback!");
                            } catch (UserException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                            } catch (SQLException ex) {
                            } catch (AlreadyValuedException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                            } catch (RemoteException ex) {
                            }
                    }
                }
            }
        });

        tasti.add(bottoneShowFeedback);
        tasti.add(bottoneAggiungi);
        tasti.add(bottoneValuta);
        tasti.add(bottoneTogli);

        add(scrollPane, BorderLayout.CENTER);
        add(tasti, BorderLayout.SOUTH);
        add(scrollPane2, BorderLayout.LINE_END);

        setColor(Frame.backDark, Frame.compBackDark, Frame.compForeDark);
    }

    /**
     * songOfPlaylist setter.
     * @param songOfPlaylist
     */
    public void setSongOfPlaylist(boolean songOfPlaylist) {
        this.songOfPlatlist = songOfPlaylist;
    }

    /**
     * logged setter.
     * @param logged
     */
    public void setLogged(boolean logged){
        this.logged = logged;
    }

    /**
     * Add the song list in the songResultSet's model.
     * @param songs
     */
    public void inserisciBrani(LinkedList<Song> songs) {
        DefaultListModel modelloSongs = new DefaultListModel<Song>();
        for (Song s : songs)
            if (!s.getId().equals("ZZZZZZZZZZZZZZZZZZ"))
                modelloSongs.addElement(s);
        songResultSet.setModel(modelloSongs);
    }

    /**
     * Clear the songResultSet's model
     */
    public void pulisciArea() {
        songResultSet.setModel(new DefaultListModel());
    }

    /**
     * Set the background color and the component's background and foreground.
     * @param back
     * @param compBack
     * @param compFore
     */
    public void setColor(Color back, Color compBack, Color compFore){
        songResultSet.setBackground(compBack);
        songResultSet.setForeground(compFore);
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
     * songListener setter.
     * @param songListener
     */
    public void setSongListener(SongListener songListener) {
        this.songListener = songListener;
    }

}