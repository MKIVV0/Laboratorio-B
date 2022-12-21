package user;

import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class BarraStrumenti extends JPanel {

    private JButton bottoneLogin;
    private JButton bottoneLogout;
    private JButton bottoneRegistra;
    private LogListener logListener;
    private RegistrazioneListener registrazioneListener;

    public BarraStrumenti() {

        super(new FlowLayout(FlowLayout.LEFT));

            bottoneLogin = new JButton("Login");
            bottoneRegistra = new JButton("Registrati");
            bottoneLogout = new JButton("Logout");
            bottoneLogout.setVisible(false);

        // LOGIN
        bottoneLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
                    if (uid.equals("") || pw.equals("")) {
                        JOptionPane.showMessageDialog(null, "Please insert Username and Password");
                    } else {
                        LogEvent le = new LogEvent(this, uid, pw);
                        if (logListener != null)
                            try {
                                logListener.credenzialiFornite(le);
                                JOptionPane.showMessageDialog(null, "Logged in!");
                            } catch (RemoteException ex) {
                            } catch (AlreadyLoggedException ex) {
                            } catch (SQLException ex) {
                            } catch (WrongCredentialsException ex) {
                                JOptionPane.showMessageDialog(null, "Wrong Username / Password");
                            }
                    }
                }

            }
        });

        // LOGOUT
        bottoneLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(logListener != null)
                    try {
                        logListener.logout();
                        JOptionPane.showMessageDialog(null, "Logged out!");
                    } catch (NotLoggedException ex) {
                    } catch (RemoteException ex) {
                    }
            }
        });

        // REGISTRAZIONE
        bottoneRegistra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new BorderLayout(5, 5));

                JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                label.add(new JLabel("First name", SwingConstants.RIGHT));
                label.add(new JLabel("Last name", SwingConstants.RIGHT));
                label.add(new JLabel("Fiscal code", SwingConstants.RIGHT));
                label.add(new JLabel("Address", SwingConstants.RIGHT));
                label.add(new JLabel("E-Mail", SwingConstants.RIGHT));
                label.add(new JLabel("Username", SwingConstants.RIGHT));
                label.add(new JLabel("Password", SwingConstants.RIGHT));

                panel.add(label, BorderLayout.WEST);

                JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
                JTextField firstname = new JTextField();
                JTextField lastname = new JTextField();
                JTextField fiscalcode = new JTextField();
                JTextField address = new JTextField();
                JTextField email = new JTextField();
                JTextField username = new JTextField();
                JPasswordField password = new JPasswordField();

                controls.add(firstname);
                controls.add(lastname);
                controls.add(fiscalcode);
                controls.add(address);
                controls.add(email);
                controls.add(username);
                controls.add(password);

                panel.add(controls, BorderLayout.CENTER);

                int scelta = JOptionPane.showConfirmDialog(Frame.getFrames()[0], panel, "registrazione", JOptionPane.OK_CANCEL_OPTION);
                if (scelta == 0) {
                    String fn = firstname.getText();
                    String ln = lastname.getText();
                    String FC = fiscalcode.getText();
                    String addr = address.getText();
                    String em = email.getText();
                    String uid = username.getText();
                    String pw = new String(password.getPassword());
                    if (fn.equals("") || ln.equals("") || FC.equals("") || addr.equals("") || em.equals("") || uid.equals("") || pw.equals(""))
                        JOptionPane.showMessageDialog(null, "Please insert all info");
                    else {
                        RegistrazioneEvent re = new RegistrazioneEvent(this, fn, ln, FC, addr, em, uid, pw);
                        if (registrazioneListener != null)
                            try {
//                            frame.resourceManager.registerUser(fn, ln, FC, addr, em, uid, pw);
                                registrazioneListener.datiForniti(re);
                                JOptionPane.showMessageDialog(null, "User registered successfully!");
                            } catch (AlreadyRegisteredException ex) {
                                JOptionPane.showMessageDialog(null, "The user with these data already exists!");
                            } catch (RemoteException ex) {
                            }
                    }
                }
            }
        });

        bottoneLogin.setFocusable(false);
        bottoneRegistra.setFocusable(false);
        bottoneLogout.setFocusable(false);

        add(bottoneLogin);
        add(bottoneRegistra);
        add(bottoneLogout);
    }

    public void logged(boolean logged){
        if(logged){
            bottoneLogin.setVisible(false);
            bottoneRegistra.setVisible(false);
            bottoneLogout.setVisible(true);
        } else {
            bottoneLogin.setVisible(true);
            bottoneRegistra.setVisible(true);
            bottoneLogout.setVisible(false);
        }
    }

    public void setLogListener(LogListener logListener) {
        this.logListener = logListener;
    }

    public void setRegistrazioneListener(RegistrazioneListener registrazioneListener) {
        this.registrazioneListener = registrazioneListener;
    }

}
