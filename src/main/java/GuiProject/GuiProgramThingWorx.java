package GuiProject;

import events.EventRs;
import interfaces.EventRsListenerInterface;
import listeners.EventRsManageListener;
import things.request.ThingOneRq;

import javax.swing.*;
import java.awt.*;

public class GuiProgramThingWorx extends JFrame{
    private JPanel mainPanel;
    private JButton clickOne;
    private JButton clickTwo;
    private JPanel panelOne;
    private JPanel panelTwo;
    private JPanel panelThree;
    private JPanel panelFour;
    private JPanel colorPanel;
    private JPanel buttonPanel;
    private ThingOneRq thingOneRq;
    private EventRsManageListener manageListener;

    public GuiProgramThingWorx setManageListener(EventRsManageListener manageListener) {
        this.manageListener = manageListener;
        manageListener.setEvListener(new PanelListener());
        return this;
    }

    public GuiProgramThingWorx setThingOneRq(ThingOneRq thingOneRq) {
        this.thingOneRq = thingOneRq;
        return this;
    }

    public GuiProgramThingWorx(String title) throws HeadlessException {
        super(title);
        clickOne.setText("clickOne");
        clickTwo.setText("clickTwo");
        clickOne.addActionListener(e->{
            thingOneRq.addCountBtn1();
        });

        clickTwo.addActionListener(e->{
            thingOneRq.addCountBtn2();
        });

        panelOne.setBackground(Color.BLACK);
        panelTwo.setBackground(Color.BLACK);
        panelThree.setBackground(Color.BLACK);
        panelFour.setBackground(Color.BLACK);
        setSize(400, 300);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
       // pack();
    }

    private class PanelListener implements EventRsListenerInterface{

        @Override
        public void actionPerformed(EventRs e) {
            switch (e.getComponent()){
                case PANEL1: if(e.isEnable()){
                    panelOne.setBackground(Color.BLUE);
                } else {
                    panelOne.setBackground(Color.BLACK);
                }; break;
                case PANEL2:if(e.isEnable()){
                    panelTwo.setBackground(Color.RED);
                } else {
                    panelTwo.setBackground(Color.BLACK);
                }; break;
                case PANEL3:if(e.isEnable()){
                    panelThree.setBackground(Color.GREEN);
                } else {
                    panelThree.setBackground(Color.BLACK);
                }; break;
                case PANEL4:if(e.isEnable()){
                    panelFour.setBackground(Color.YELLOW);
                } else {
                    panelFour.setBackground(Color.BLACK);
                }; break;
            }
        }
    }

    public static void main(String[] args) {
        new GuiProgramThingWorx("123");
    }
}
