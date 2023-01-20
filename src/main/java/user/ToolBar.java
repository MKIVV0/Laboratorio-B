/**
 * @author Zhang Ying Huang, Matricola 746483, CO
 * @author Alessandro Di Lorenzo, Matricola 733052, CO
 */

package user;

import common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * This class contains the general user actions
 */
public class ToolBar extends JPanel {

    /**
     * this attribute represents the login button
     */
    private JButton bottoneLogin;
    /**
     * this attribute represents the logout button
     */
    private JButton bottoneLogout;
    /**
     * this attribute represents the sign-up button
     */
    private JButton bottoneRegistra;
    /**
     * these attribute represent the modify username and modify password buttons
     */
    private JButton bottoneModificaUsername, bottoneModificaPassword;
    /**
     * this attribute represents an entity that waits for some user interaction with the login or logout buttons
     */
    private LogListener logListener;
    /**
     * this attribute represents an entity that waits for some user interaction with the sign-up button
     */
    private RegistrationListener registrationListener;
    /**
     * this attribute represents an entity that waits for some user interaction with the buttons which purpose
     * is to modify fields, such as modify password or modify username
     */
    private SettingsListener settingsListener;

    /**
     * ToolBar constructor
     * */
    public ToolBar() {
        super(new FlowLayout(FlowLayout.LEFT));

        bottoneLogin = new JButton("Login");
        bottoneRegistra = new JButton("Sign_up");
        bottoneModificaUsername = new JButton("Modify username");
        bottoneModificaUsername.setVisible(false);
        bottoneModificaPassword = new JButton("Modify password");
        bottoneModificaPassword.setVisible(false);
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
                            } catch (RemoteException ex) {
                            } catch (SQLException ex) {
                            } catch (UserException ex) {
                                JOptionPane.showMessageDialog(null, "Wrong Username / Password OR Already logged");
                            }
                    }
                }
            }
        });

        /**
         * This listener is responsible for listening for actions performed on the logout button
         * */
        bottoneLogout.addActionListener(new ActionListener() {
            /**
             * manages the action performed on the logout button
             * @param  e the click on the button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logListener != null)
                    try {
                        logListener.logout();
                        JOptionPane.showMessageDialog(null, "Logged out!");
                    } catch (UserException ex) {
                    } catch (RemoteException ex) {
                    }
            }
        });

        /**
         * This listener is responsible for listening for actions performed on the sign-up button
         * */
        bottoneRegistra.addActionListener(new ActionListener() {
            /**
             * manages the action performed on the sign-up button
             * @param  e the click on the button
             */
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
                int scelta = JOptionPane.showConfirmDialog(Frame.getFrames()[0], panel, "registration", JOptionPane.OK_CANCEL_OPTION);
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
                        RegistrationEvent re = new RegistrationEvent(this, fn, ln, FC, addr, em, uid, pw);
                        if (registrationListener != null)
                            try {
                                registrationListener.datiForniti(re);
                                JOptionPane.showMessageDialog(null, "User registered successfully!");
                            } catch (UserException ex) {
                                JOptionPane.showMessageDialog(null, "The user with these data already exists!");
                            } catch (RemoteException ex) {
                            }
                    }
                }
            }
        });

        /**
         * This listener is responsible for listening for actions performed on the modify username button
         * */
        bottoneModificaUsername.addActionListener(new ActionListener() {
            /**
             * manages the action performed on the modify username button
             * @param  e the click on the button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new BorderLayout(5, 5));
                JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                label.add(new JLabel("New username", SwingConstants.RIGHT));
                panel.add(label, BorderLayout.WEST);
                JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
                JTextField username = new JTextField();
                controls.add(username);
                panel.add(controls, BorderLayout.CENTER);
                int scelta = JOptionPane.showConfirmDialog(Frame.getFrames()[0], panel, "modify username", JOptionPane.OK_CANCEL_OPTION);
                if (scelta == 0) {
                    String uid = username.getText();
                    if (uid.equals("")) {
                        JOptionPane.showMessageDialog(null, "Please insert Username");
                    } else {
                        if (settingsListener != null)
                            try {
                                settingsListener.modifyUsername(uid);
                                JOptionPane.showMessageDialog(null, "Changed!");
                            } catch (RemoteException ex) {
                            } catch (SQLException ex) {
                            } catch (UserException ex) {
                            }
                    }
                }
            }
        });

        /**
         * This listener is responsible for listening for actions performed on the modify password button
         * */
        bottoneModificaPassword.addActionListener(new ActionListener() {
            /**
             * manages the action performed on the modify password button
             * @param  e the click on the button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new BorderLayout(5, 5));
                JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
                label.add(new JLabel("New password", SwingConstants.RIGHT));
                panel.add(label, BorderLayout.WEST);
                JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
                JPasswordField password = new JPasswordField();
                controls.add(password);
                panel.add(controls, BorderLayout.CENTER);
                int scelta = JOptionPane.showConfirmDialog(Frame.getFrames()[0], panel, "modify password", JOptionPane.OK_CANCEL_OPTION);
                if (scelta == 0) {
                    String pw = new String(password.getPassword());
                    if (pw.equals("")) {
                        JOptionPane.showMessageDialog(null, "Please insert a new password");
                    } else {
                        if (settingsListener != null)
                            try {
                                settingsListener.modifyPassword(pw);
                                JOptionPane.showMessageDialog(null, "Changed!");
                            } catch (RemoteException ex) {
                            } catch (SQLException ex) {
                            } catch (UserException ex) {
                            }
                    }
                }
            }
        });

        add(bottoneLogin);
        add(bottoneRegistra);
        add(bottoneLogout);
        add(bottoneModificaUsername);
        add(bottoneModificaPassword);

        setColor(Frame.backDark, Frame.compBackDark, Frame.compForeDark);
    }

    public void logged(boolean logged) {
        if (logged) {
            bottoneLogin.setVisible(false);
            bottoneRegistra.setVisible(false);
            bottoneLogout.setVisible(true);
            bottoneModificaUsername.setVisible(true);
            bottoneModificaPassword.setVisible(true);
        } else {
            bottoneLogin.setVisible(true);
            bottoneRegistra.setVisible(true);
            bottoneLogout.setVisible(false);
            bottoneModificaUsername.setVisible(false);
            bottoneModificaPassword.setVisible(false);
        }
    }

    public void setColor(Color back, Color compBack, Color compFore){
        for(Component c: getComponents()){
            c.setBackground(compBack);
            c.setForeground(compFore);
            c.setFocusable(false);
        }
        setForeground(compFore);
        setBackground(back);
    }

    public void setLogListener(LogListener logListener) {
        this.logListener = logListener;
    }

    public void setRegistrazioneListener(RegistrationListener registrationListener) {
        this.registrationListener = registrationListener;
    }

    public void setSettingsListener(SettingsListener settingsListener) {
        this.settingsListener = settingsListener;
    }

    /*@Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setColor(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.fillRect(0, getHeight() - 25, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }*/

//    @Override
//    protected void paintComponent(Graphics grphcs) {
//        Graphics2D g2 = (Graphics2D) grphcs;
//        g2.setColor(getBackground());
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        GradientPaint gp = new GradientPaint(0, 0, Color.decode("#4568dc"), 0, getHeight(), Color.decode("#b06ab3"));
//        g2.setPaint(gp);
//        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
//        g2.fillRect(getWidth() - 25, 0, getWidth(), getHeight());
//        g2.fillRect(0, getHeight() - 25, getWidth(), getHeight());
//        super.paintComponent(grphcs);
//    }
}
