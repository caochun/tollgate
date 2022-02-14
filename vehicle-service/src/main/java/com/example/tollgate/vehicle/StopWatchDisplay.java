package com.example.tollgate.vehicle;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Quick GUI to demonstrate the SCXML driven stopwatch.
 * <p>
 * Separation of UI (this class) from behavior (StopWatch class).
 * UI serves merely as a front to relay user initiated events to StopWatch
 * object, which encapsulates all the behavior of a stopwatch.
 * Using SCXML makes the StopWatch class simplistic, and provides a direct
 * route from the UML model to the runtime.
 *
 * @see StopWatch
 */
@Component
public class StopWatchDisplay extends JFrame {

    private StopWatch stopWatch;

    @Autowired
    public void setStopWatch(StopWatch stopWatch) {
        this.stopWatch = stopWatch;
    }

    private Image watchImage;

    public StopWatchDisplay() {
        super("SCXML stopwatch");
    }


    @PostConstruct
    private void setupUI() {
        final URL imageURL = this.getClass().getClassLoader()
                .getResource("stopwatch.gif");
        final URL iconURL = this.getClass().getClassLoader()
                .getResource("stopwatchicon.gif");
        final Toolkit kit = Toolkit.getDefaultToolkit();
        watchImage = kit.createImage(imageURL);
        final Image watchIcon = kit.createImage(iconURL);
        final WatchPanel panel = new WatchPanel();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);
        display = new JLabel(stopWatch.getDisplay());
        panel.add(display, BorderLayout.PAGE_START);
        state = new JLabel();
        panel.add(state, BorderLayout.CENTER);
        pack();
        setLocation(200, 200);
        setIconImage(watchIcon);
        setResizable(false);
        setSize(300, 125);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        final Timer displayTimer = new Timer();
        displayTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                display.setText(DISPLAY_PREFIX + stopWatch.getDisplay()
                        + DISPLAY_SUFFIX);
                state.setText(STATE_PREFIX + stopWatch.getCurrentState()
                        + STATE_SUFFIX);
            }
        }, 100, 100);
    }


    class WatchPanel extends JPanel {
        @Override
        public void paintComponent(final Graphics g) {
            if (watchImage != null) {
                g.drawImage(watchImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }

    private JLabel display, state;
    // spaces :: GridBagConstraints ;-)
    private static final String DISPLAY_PREFIX = "<html><font face=\"Courier\" color=\"maroon\"" +
            " size=\"10\"><b>&nbsp;&nbsp;&nbsp;",
            DISPLAY_SUFFIX = "</b></font></html>",
            STATE_PREFIX = "<html><font color=\"blue\" size=\"4\"" +
                    ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
            STATE_SUFFIX = "</font></html>";

}