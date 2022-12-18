package user;

import common.AbstractUser;
import common.LoggedUser;
import common.Song;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class ControlPanel extends JPanel {

    private PannelloForm pannelloForm;
    private PannelloPlaylist pannelloPlaylist;

    ControlPanel(Frame frame) {
        setPreferredSize(new Dimension(300, 100));
        setLayout(new GridLayout(2,1));

        pannelloForm = new PannelloForm();

        pannelloForm.setFormListener(new FormListener() {
            @Override
            public void formEventListener(FormEvent fe) {
                String testo = fe.getTesto();
                String tipoRicerca = fe.getTipoRicerca();
                try {
                    LinkedList<Song> tmp = frame.resourceManager.findSong(testo);
                    frame.objectAreaPanel.pulisciArea();
                    frame.objectAreaPanel.inserisciBrani(tmp);
                } catch (RemoteException e) {
                }
            }
        });

        add(pannelloForm);

        pannelloPlaylist = new PannelloPlaylist();
        add(pannelloPlaylist);

        logged(frame.user);

    }

    public void logged(AbstractUser user){
        if(user instanceof LoggedUser){
            pannelloPlaylist.refresh(user);
            pannelloPlaylist.setVisible(true);
        } else {
            pannelloPlaylist.refresh(user);
            pannelloPlaylist.setVisible(false);
        }
    }
}
