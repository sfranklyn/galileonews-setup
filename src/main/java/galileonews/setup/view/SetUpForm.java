/*
 * Copyright 2015 Samuel Franklyn <sfranklyn at gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package galileonews.setup.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import net.java.dev.designgridlayout.DesignGridLayout;
import galileonews.setup.service.SetupService;
import java.io.IOException;

/**
 *
 * @author Samuel Franklyn <sfranklyn at gmail.com>
 */
public class SetUpForm extends JFrame {

    private static final long serialVersionUID = 4926547912798562520L;

    private final JTextField textServer;
    private final JTextField textPort;
    private final JTextField textUser;
    private final JPasswordField textPassword;
    private final JButton buttonOk;
    private final JButton buttonCancel;
    private final SetupService setup;
    private final JCheckBox checkDropDb;

    public SetUpForm() {
        setup = new SetupService();

        setTitle("Set Up");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        DesignGridLayout layout = new DesignGridLayout(panel);

        textServer = new JTextField("localhost", 10);
        textPort = new JTextField("3306", 10);
        textUser = new JTextField("root", 10);
        textPassword = new JPasswordField("", 10);
        checkDropDb = new JCheckBox("Drop Database");

        buttonOk = new JButton("Ok");
        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                buttonOkAction(evt);
            }
        });

        buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                buttonCancelAction(evt);
            }
        });

        layout.row().grid(new JLabel("Server")).add(textServer);
        layout.row().grid(new JLabel("Port")).add(textPort);
        layout.row().grid(new JLabel("User")).add(textUser);
        layout.row().grid(new JLabel("Password")).add(textPassword);
        layout.row().grid().add(checkDropDb);
        layout.row().grid().add(buttonOk).add(buttonCancel);

        add(panel);
        rootPane.setDefaultButton(buttonOk);
        pack();
        setLocationRelativeTo(null);
    }

    private void buttonOkAction(ActionEvent evt) {
        try {
            if (checkDropDb.isSelected()) {
                setup.dropDb(textServer.getText(), textPort.getText(),
                        textUser.getText(), new String(textPassword.getPassword()));
            }
            setup.execute(textServer.getText(), textPort.getText(),
                    textUser.getText(), new String(textPassword.getPassword()));
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(SetUpForm.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        JOptionPane.showMessageDialog(null, "Setup successful");
        dispose();
        System.exit(0);
    }

    private void buttonCancelAction(ActionEvent evt) {
        dispose();
        System.exit(0);
    }

    public static void main(String args[]) {
        SetUpForm setUpForm = new SetUpForm();
        setUpForm.setVisible(true);
        setUpForm.textPassword.requestFocusInWindow();
    }

}
