package user;

import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class BarraStrumenti extends JPanel {

    private JButton bottoneLog;
    private JButton bottoneRegistra;

    public BarraStrumenti(Frame frame){

        super(new FlowLayout(FlowLayout.LEFT));

        if(frame.user instanceof NotLoggedUser) {
            bottoneLog = new JButton("Login");
            bottoneRegistra = new JButton("Registrati");
        }else if(frame.user instanceof LoggedUser){
            bottoneLog.setText("Logout");
            bottoneRegistra.setText("+ Playlist");
        }

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
                                frame.user = frame.resourceManager.login(frame.user, username.getText(), new String(password.getPassword()));
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
                        frame.user = frame.resourceManager.logout(frame.user);
                        JOptionPane.showMessageDialog(null, "Logged out!");
                    }catch (Exception exe){}
                }

                if(frame.user instanceof LoggedUser) {
                    bottoneLog.setText("Logout");
                    bottoneRegistra.setText("+ Playlist");
                }
                else if(frame.user instanceof NotLoggedUser) {
                    bottoneLog.setText("Login");
                    bottoneRegistra.setText("Registrati");
                }
            }
        });

        bottoneLog.setFocusable(false);
        bottoneRegistra.setFocusable(false);

        this.add(bottoneLog);
        this.add(bottoneRegistra);
    }


}