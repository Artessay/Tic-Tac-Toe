package edu.rihong.UI;

import java.awt.Point;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.ImageIcon;

public class SwingUtil {
        
    /** create an auto adjust ImageIcon */
    public static ImageIcon createAutoAdjustIcon(Image image) {
        ImageIcon icon = new ImageIcon(image) {
            @Override
            public synchronized void paintIcon(java.awt.Component component, Graphics g, int x, int y) {
                Point startPoint = new Point(0, 0);
                Dimension imgSize = component.getSize();
                
                // draw
                if(getImageObserver() == null) {
                    g.drawImage(getImage(), startPoint.x, startPoint.y,
                            imgSize.width, imgSize.height, component);
                 } else {
                    g.drawImage(getImage(), startPoint.x, startPoint.y,
                            imgSize.width, imgSize.height, getImageObserver());
                 }
            };
        };

        return icon;
    }
}
