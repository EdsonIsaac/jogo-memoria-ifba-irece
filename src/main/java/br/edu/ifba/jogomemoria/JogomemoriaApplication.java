package br.edu.ifba.jogomemoria;

import br.edu.ifba.jogomemoria.infrastructure.exception.ObjectNotFoundException;
import br.edu.ifba.jogomemoria.infrastructure.service.Facade;
import br.edu.ifba.jogomemoria.user.enums.Authority;
import br.edu.ifba.jogomemoria.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class JogomemoriaApplication implements CommandLineRunner {

    @Autowired
    private Facade facade;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Value("${server.port}")
    private String port;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(JogomemoriaApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

        try {
            User user = facade.findUserByUsername("admin");

            if (!encoder.matches("JogoMemoriaIFBAIrece", user.getPassword())) {
                saveDefaultUser(user);
            }
        } catch (ObjectNotFoundException ex) {
            saveDefaultUser(new User());
        }

        createSystemTray();
    }

    private void createSystemTray() {
        TrayIcon trayIcon;

        if (SystemTray.isSupported()) {

            try {
                SystemTray systemTray = SystemTray.getSystemTray();
                Image icon = ImageIO.read(getClass().getResourceAsStream("/static/imgs/logo-if.png"));
                PopupMenu popupMenu = new PopupMenu("Opções");
                MenuItem menuItemExit = new MenuItem("Sair");

                ActionListener exitListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        System.exit(0);
                    }
                };

                menuItemExit.addActionListener(exitListener);
                popupMenu.add(menuItemExit);

                trayIcon = new TrayIcon(icon, "Sistema em execução na porta: " + port, popupMenu);
                trayIcon.setImageAutoSize(true);

                systemTray.add(trayIcon);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível gerar o System Tray!", "System Tray", JOptionPane.INFORMATION_MESSAGE);
                Logger.getLogger(JogomemoriaApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "System tray não suportado!", "System Tray", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void saveDefaultUser(User user) {

        user.setName("Administrador");
        user.setUsername("admin");
        user.setPassword("JogoMemoriaIFBAIrece");
        user.setConfirmPassword("JogoMemoriaIFBAIrece");
        user.addAuthority(Authority.PANEL_USERS);

        facade.save(user);
    }
}