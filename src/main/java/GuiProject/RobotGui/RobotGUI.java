package GuiProject.RobotGui;

import GuiProject.RemoteTerminal.GuiProgramThingWorx;
import events.EventRs;
import interfaces.EventRsListenerInterface;
import interfaces.InterfaceRs;
import listeners.EventRsManageListener;
import main_pakage.SenderServicePostRequestHttpClient;
import things.AbstractThingClass;
import things.request.RobotThingRq;
import things.request.ThingOneRq;
import things.response.ThingOneRs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class RobotGUI extends JFrame {
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JTextField textFieldUrl;
    private JLabel urlLabel;
    private JLabel thingNameLabel;
    private JTextField textFieldThingName;
    private JTextField textFieldServiceName;
    private JTextField textFieldAppKey;
    private JLabel serviceNameLabel;
    private JLabel appKeyLabel;
    private JButton connectedBtn;
    private JLabel lableBtn1;
    private JLabel lableBtn2;
    private JLabel connectedLbl;
    private JPanel MotorPanel;
    private JTextField textL4;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textL3;
    private JTextField textL2;
    private JTextField textField14;
    private JTextField textField15;
    private JTextField textField16;
    private JTextField textField17;
    private JTextField textL1;
    private JPanel PanelLoad;
    private JPanel panelTemp;
    private JPanel panelPosition;
    private JTextField motorLoad;
    private JTextField motorTemp;
    private JButton sendMotorData;
    private JPanel panelOne;
    private JPanel panelTwo;
    private JPanel panelThree;
    private JPanel panelFour;

    private ThingOneRq thingOneRq = new ThingOneRq();
    private ThingOneRs thingOneRs = new ThingOneRs();
    private EventRsManageListener manageListener = new EventRsManageListener();
    private SenderServicePostRequestHttpClient<AbstractThingClass, InterfaceRs> httpSender;
    private int statusCode;
    private Thread threadServerSender = new Thread();

    private void initParamsGui() {
        lableBtn1.setText("Нажми на кнопку");
        lableBtn2.setText("Нажми на кнопку");
        connectedLbl.setText("Ответ от сервера");
        appKeyLabel.setText("Введите ваш appKey для доступа к серверу");
        serviceNameLabel.setText("Введите название вызываемого Сервиса");
        thingNameLabel.setText("Введите название подключаемой вещи");
        urlLabel.setText("Введите url сервера");
        connectedBtn.setText("Click for Connection to server");

        textFieldThingName.setText("TwoThing");
        textFieldServiceName.setText("Service2");
        textFieldAppKey.setText("9adde0b8-fc59-43ba-89dd-5aaf03e1952c");
        textFieldUrl.setText("https://pp-20091114071y.devportal.ptc.io/Thingworx");
    }

    RobotThingRq rbThing = new RobotThingRq();
    private void motorSettings(){
        motorLoad.setText("311");
        motorTemp.setText("100");
        sendMotorData.addActionListener((e)->{
            rbThing.setM11(Double.parseDouble(motorLoad.getText()));
            rbThing.setL11(Double.parseDouble(motorTemp.getText()));
            httpSender.doPostRequest(rbThing, null);
        });
    }

    public RobotGUI(String title) throws HeadlessException {
        super(title);
        initParamsGui();


        // настройка листенера и объекта для ответа
        manageListener.setEvListener(new RobotGUI.PanelListener());
        thingOneRs.setManageListener(manageListener);

        //событие на кнопку коннекта
        connectedBtn.addActionListener(e -> {
            threadServerSender.interrupt();
            String url = textFieldUrl.getText();
            String thingName = textFieldThingName.getText();
            String serviceNamr = textFieldServiceName.getText();
            String appKey = textFieldAppKey.getText();
            httpSender = new SenderServicePostRequestHttpClient(thingName, serviceNamr, appKey, url);
            statusCode = httpSender.getStatusCode();
            if (statusCode != 200) {
                if(httpSender!=null){
                    try {
                        httpSender.closeClient();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                httpSender = null;
                connectedLbl.setText("Соединение не установлено! Код ошибки " + statusCode + " Повторите попытку с другими параметрами");
            } else {
                connectedLbl.setText("Соединение с сервером установлено. Код ответа равен: " + statusCode);
//                threadServerSender = new Thread(() -> {
//                    try {
//                        while (!Thread.currentThread().isInterrupted()) {
//                            httpSender.doPostRequest(thingOneRq, thingOneRs);
//                            Thread.sleep(5000);
//                        }
//                    } catch (InterruptedException ex) {
//                        ex.printStackTrace();
//                        Thread.currentThread().interrupt();
//                    }
//                });
//                threadServerSender.start();
//                threadServerSender.setDaemon(true);
            }
        });


        setSize(800, 600);
        setContentPane(mainPanel);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                threadServerSender.interrupt();
                //  threadServerSender.stop();
                try {
                    if (httpSender != null)
                        httpSender.closeClient();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.exit(0);
            }
        });
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        // pack();
    }

    private class PanelListener implements EventRsListenerInterface {

        @Override
        public void actionPerformed(EventRs e) {
            switch (e.getComponent()) {
                case PANEL1:
                    if (e.isEnable()) {
                        panelOne.setBackground(Color.BLUE);
                    } else {
                        panelOne.setBackground(Color.BLACK);
                    }
                    ;
                    break;
                case PANEL2:
                    if (e.isEnable()) {
                        panelTwo.setBackground(Color.RED);
                    } else {
                        panelTwo.setBackground(Color.BLACK);
                    }
                    ;
                    break;
                case PANEL3:
                    if (e.isEnable()) {
                        panelThree.setBackground(Color.GREEN);
                    } else {
                        panelThree.setBackground(Color.BLACK);
                    }
                    ;
                    break;
                case PANEL4:
                    if (e.isEnable()) {
                        panelFour.setBackground(Color.YELLOW);
                    } else {
                        panelFour.setBackground(Color.BLACK);
                    }
                    ;
                    break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->new RobotGUI("Robot"));
    }
}
