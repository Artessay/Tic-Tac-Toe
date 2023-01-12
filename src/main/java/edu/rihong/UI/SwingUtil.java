package edu.rihong.UI;

import java.awt.Point;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.ImageIcon;

public class SwingUtil {
        
    /**创建一个可以自适应组件大小的ImageIcon对象 */
    public static ImageIcon createAutoAdjustIcon(Image image) {
        ImageIcon icon = new ImageIcon(image) {
            @Override
            public synchronized void paintIcon(java.awt.Component component, Graphics g, int x, int y) {
                //初始化参数
                Point startPoint = new Point(0, 0);     //默认绘制起点
                Dimension imgSize = component.getSize();    //获取组件大小
                
                //根据起点和区域大小进行绘制
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
