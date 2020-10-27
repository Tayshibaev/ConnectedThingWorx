package GuiProject.RobotGui;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
    private JTextField textT4;
    private JTextField textM4;
    private JTextField textL5;
    private JTextField textT5;
    private JTextField textM5;
    private JTextField textL6;
    private JTextField textT6;
    private JTextField textM6;
    private JTextField textM3;
    private JTextField textT3;
    private JTextField textL3;
    private JTextField textL2;
    private JTextField textT2;
    private JTextField textM2;
    private JTextField textM1;
    private JTextField textT1;
    private JTextField textL1;

    private List<JTextField> listL = Arrays.asList(textL1, textL2, textL3, textL4, textL5, textL6);
    private List<JTextField> listT = Arrays.asList(textT1, textT2, textT3, textT4, textT5, textT6);
    private List<JTextField> listM = Arrays.asList(textM1, textM2, textM3, textM4, textM5, textM6);

    private JPanel PanelLoad;
    private JPanel panelTemp;
    private JPanel panelPosition;
    private JLabel LoadMotor;
    private JLabel TempMotor;
    private JLabel PositionMotor;
    private JButton sendMotorDataBtn;
    private JTextField textStasus;
    private JTextField textNumCommand;
    private JPanel statusAndNumCommPane;
    private JLabel Status;
    private JLabel NumCommand;
    private JButton rndValueMotorBtn;
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
        connectedLbl.setText("Ответ от сервера");
        appKeyLabel.setText("Введите ваш appKey для доступа к серверу");
        serviceNameLabel.setText("Введите название вызываемого Сервиса");
        thingNameLabel.setText("Введите название подключаемой вещи");
        urlLabel.setText("Введите url сервера");
        connectedBtn.setText("Click for Connection to server");

        textFieldThingName.setText("Robot_1");
        textFieldServiceName.setText("robot_service");
        textFieldAppKey.setText("a99e84db-d28d-4aa8-86b6-2d63b5299d5f");
        textFieldUrl.setText("https://pp-20101315258i.devportal.ptc.io/Thingworx");
    }

    //Длч установки в текстовые поля рандомных значений
    private void setRndValueTextField(List<JTextField> list) {
        list.forEach((textField) -> {
            textField.setText(String.valueOf(ThreadLocalRandom.current().nextInt(100, 600)));
        });
    }

    private List<Double> getValueTextField(List<JTextField> list) {
        List<Double> listD = new ArrayList<>();
        list.forEach((x) -> {
            listD.add(Double.parseDouble(x.getText()));
        });
        return listD;
    }

    RobotThingRq rbThing = new RobotThingRq();

    private void motorSettings() {

        sendMotorDataBtn.setText("Отправить данные на сервер");
        rndValueMotorBtn.setText("Задать новые значение для данных мотора");
        LoadMotor.setText("LoadMotor");
        PositionMotor.setText("PositionMotor");
        TempMotor.setText("TempMotor");

        setRndValueTextField(listL);
        setRndValueTextField(listT);
        setRndValueTextField(listM);

        rndValueMotorBtn.addActionListener((e) -> {
            setRndValueTextField(listL);
            setRndValueTextField(listT);
            setRndValueTextField(listM);
        });

        sendMotorDataBtn.addActionListener((e) -> {
            rbThing.setAllParams(getValueTextField(listL), getValueTextField(listT), getValueTextField(listM));
            if (httpSender != null) {
                httpSender.doPostRequest(rbThing, null);
            } else {
                connectedLbl.setText("Не установлено соединение! Повторите попытку");
            }
        });
    }

    public RobotGUI(String title) throws HeadlessException {
        super(title);
        initParamsGui();
        motorSettings();

        // настройка листенера и объекта для ответа
        manageListener.setEvListener(new RobotGUI.PanelListener());
        thingOneRs.setManageListener(manageListener);

        //событие на кнопку коннекта
        connectedBtn.addActionListener(e -> {
            //threadServerSender.interrupt();
            String url = textFieldUrl.getText();
            String thingName = textFieldThingName.getText();
            String serviceNamr = textFieldServiceName.getText();
            String appKey = textFieldAppKey.getText();
            httpSender = new SenderServicePostRequestHttpClient(thingName, serviceNamr, appKey, url);
            statusCode = httpSender.getStatusCode();
            if (statusCode != 200) {
                if (httpSender != null) {
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


        setSize(1200, 600);
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
        SwingUtilities.invokeLater(() -> new RobotGUI("Robot"));
    }
}
