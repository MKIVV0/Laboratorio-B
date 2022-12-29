package user;

import common.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.LinkedList;

public class ObjectAreaPanel extends JPanel {

    private JList songResultSet;
    private JPanel tasti;
    private JButton bottoneShowFeedback, bottoneAggiungi, bottoneValuta, bottoneTogli;
    private SongListener songListener;
    private boolean logged, songOfPlatlist;

    public ObjectAreaPanel(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        // JLIST
        songResultSet = new JList<Song>();
        songResultSet.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

        JScrollPane scrollPane = new JScrollPane(songResultSet);
        scrollPane.setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.PINK));
        scrollPane.setVerticalScrollBar(new ScrollBar());

        // PANNELLO DEI TASTI
        tasti = new JPanel(new FlowLayout(FlowLayout.LEFT));

        bottoneShowFeedback = new JButton("Feedbacks");
        bottoneAggiungi = new JButton("Aggiungi");
        bottoneValuta = new JButton("Valuta");
        bottoneTogli = new JButton("Togli");
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
                            Feedback feedbacks = songListener.guardaFeedback(song);
                            JTextArea textArea = new JTextArea();
                            textArea.append(feedbacks.toString());
                            textArea.setEditable(false);
                            JFrame f = new JFrame(song.getTitle() + "'s feedbacks");
                            f.setSize(500, 400);
                            f.add(new JScrollPane(textArea), BorderLayout.CENTER);
                            f.setLocationRelativeTo(null);
                            f.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    f.dispose();
                                }
                            });
                            f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            f.setVisible(true);
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

        bottoneAggiungi.addActionListener(new ActionListener() {
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
                            throw new RuntimeException(ex);
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
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

        tasti.add(bottoneShowFeedback);
        tasti.add(bottoneAggiungi);
        tasti.add(bottoneValuta);
        tasti.add(bottoneTogli);

        add(scrollPane, BorderLayout.CENTER);
        add(tasti, BorderLayout.SOUTH);

        setColor(Frame.backDark, Frame.compBackDark, Frame.compForeDark);

    }

    public void setSongOfPlaylist(boolean songOfPlaylist) {
        this.songOfPlatlist = songOfPlaylist;
    }

    public void setLogged(boolean logged){
        this.logged = logged;
    }

    public void inserisciBrani(LinkedList<Song> songs) {
        DefaultListModel modelloSongs = new DefaultListModel<Song>();
        for (Song s : songs)
            if (!s.getId().equals("ZZZZZZZZZZZZZZZZZZ"))
                modelloSongs.addElement(s);
        songResultSet.setModel(modelloSongs);
    }

    public void pulisciArea() {
        songResultSet.setModel(new DefaultListModel());
    }

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

    public void setSongListener(SongListener songListener) {
        this.songListener = songListener;
    }
}
