package com.track;

import com.track.job.TrackListener;
import com.track.job.TrackUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrackFrame {
    private static final String TRACK = "      --------";
    private static boolean isTracking = false;

    private static JButton trackBtn;
    private static JLabel trackLbl;
    public static TrackListener trackListener;

    static {
        trackBtn = new JButton("开始定位");
        trackLbl = new JLabel(TRACK);

        trackListener = new TrackListener() {
            @Override
            public void positionUpdated(String address) {
                trackLbl.setText(address);
            }
        };

        trackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (TrackFrame.class) {
                    isTracking = !isTracking;
                    trackBtn.setText(isTracking ? "停止定位" : "开始定位");

                    if (isTracking) {
                        TrackUtil.start();
                    } else {
                        TrackUtil.stop();
                        trackLbl.setText(TRACK);
                    }
                }
            }
        });
    }

    public static JFrame showFrame() {
        // create frame
        final JFrame frame = new JFrame("代驾定位客户端");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Box chatBox = Box.createVerticalBox();
        chatBox.add(trackBtn);
        chatBox.add(trackLbl);

        // create panel
        JPanel panel = new JPanel();
        panel.add(Box.createVerticalStrut(150));
        panel.add(chatBox);

        // show panel
        frame.setContentPane(panel);
        frame.setVisible(true);

        // do work
        frame.getRootPane().setDefaultButton(trackBtn);
        trackBtn.doClick();

        return frame;
    }
}
