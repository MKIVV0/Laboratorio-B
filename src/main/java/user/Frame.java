package user;

import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.LinkedList;

public class Frame extends JFrame {

    private JPanel bottoni;
    private TextAreaPanel textAreaPanel;
    private PannelloForm pannelloForm;
    private ResourceManagerInterface resourceManager;
    private AbstractUser user;

    public Frame() throws RemoteException, NotBoundException {

        super("Titolo finestra");

        setLayout(new BorderLayout());

        bottoni = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textAreaPanel = new TextAreaPanel();
        pannelloForm = new PannelloForm();

        Registry r = LocateRegistry.getRegistry(11000);
        resourceManager = (ResourceManagerInterface) r.lookup("Gestore");

        user = new NotLoggedUser();

        JButton bottoneLog = new JButton("Login");
        JButton bottoneRegistra = new JButton("Registrati");

        bottoneLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bottoneLog.getText().equals("Login")) {
                    JPanel panel = new JPanel(new BorderLayout(5, 5));

                    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                    label.add(new JLabel("Username", SwingConstants.RIGHT));
                    label.add(new JLabel("Password", SwingConstants.RIGHT));

                    panel.add(label, BorderLayout.WEST);

                    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
                    JTextField username = new JTextField();
                    JPasswordField password = new JPasswordField();
                    controls.add(username);
                    controls.add(password);

                    panel.add(controls, BorderLayout.CENTER);

                    int scelta = JOptionPane.showConfirmDialog(Frame.getFrames()[0], panel, "login", JOptionPane.OK_CANCEL_OPTION);

                    if (scelta == 0) {
                        String uid = username.getText();
                        String pw = new String(password.getPassword());
                        if(uid.equals("") || pw.equals("")) {
                            JOptionPane.showMessageDialog(null, "Please insert Username and Password");
                        }
                        else {
                            try {
                                user = resourceManager.login(user, username.getText(), new String(password.getPassword()));
                                JOptionPane.showMessageDialog(null, "Logged in!");
                            } catch (RemoteException ex) {
                            } catch (AlreadyLoggedException ex) {
                            } catch (SQLException ex) {
                            }catch (WrongCredentialsException ex){
                                JOptionPane.showMessageDialog(null, "Wrong Username / Password");
                            }
                        }
                    }
                }else {
                    try {
                        user = resourceManager.logout(user);
                        JOptionPane.showMessageDialog(null, "Logged out!");
                    }catch (Exception exe){}
                }

                if(user instanceof LoggedUser) {
                    bottoneLog.setText("Logout");
                    bottoneRegistra.setText("+ Playlist");
                }
                else if(user instanceof NotLoggedUser) {
                    bottoneLog.setText("Login");
                    bottoneRegistra.setText("Registrati");
                }
            }
        });

        bottoni.add(bottoneLog);
        bottoni.add(bottoneRegistra);

        pannelloForm.setFormListener(new FormListener() {
            @Override
            public void formEventListener(FormEvent fe) {
                String testo = fe.getTesto();
                String tipoRicerca = fe.getTipoRicerca();

                try {
                    LinkedList<Song> tmp = resourceManager.findSong(testo);
                    for (Song s : tmp)
                        textAreaPanel.appendiTesto(s.toString());

                } catch (RemoteException e) {
                }
            }
        });

        add(textAreaPanel, BorderLayout.CENTER);
        add(bottoni, BorderLayout.PAGE_START);
        add(pannelloForm, BorderLayout.LINE_START);

        setSize(800, 500);
        setLocationRelativeTo(null);
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
