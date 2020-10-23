package GuiProject.RemoteTerminal;

import events.EventRs;
import interfaces.EventRsListenerInterface;
import interfaces.InterfaceRs;
import listeners.EventRsManageListener;
import main_pakage.SenderServicePostRequestHttpClient;
import things.AbstractThingClass;
import things.request.ThingOneRq;
import things.response.ThingOneRs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


public class GuiProgramThingWorx extends JFrame {
    private JPanel mainPanel;
    private JButton clickOne;
    private JButton clickTwo;
    private JPanel panelOne;
    private JPanel panelTwo;
    private JPanel panelThree;
    private JPanel panelFour;
    private JPanel colorPanel;
    private JPanel buttonPanel;
    private JTextField textFieldUrl;
    private JTextField textFieldThingName;
    private JTextField textFieldServiceName;
    private JTextField textFieldAppKey;
    private JButton connectedBtn;
    private JLabel urlLabel;
    private JLabel thingNameLabel;
    private JLabel serviceNameLabel;
    private JLabel appKeyLabel;
    private JLabel connectedLbl;
    private JLabel lableBtn1;
    private JLabel lableBtn2;
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

    //нужно вызвать для передачи листенера, чтобы ему присвоить какие поля изменять в зависимости от события
    public GuiProgramThingWorx setManageListener(EventRsManageListener manageListener) {
        this.manageListener = manageListener;
        manageListener.setEvListener(new PanelListener());
        thingOneRs.setManageListener(manageListener);
        return this;
    }

    //Данный метод служит для того, если извне вызывается программа с другим меняющим данные классом
    public GuiProgramThingWorx setThingOneRq(ThingOneRq thingOneRq) {
        this.thingOneRq = thingOneRq;
        return this;
    }

    public GuiProgramThingWorx(String title) throws HeadlessException {
        super(title);
        initParamsGui();
        clickOne.setText("clickOne");
        clickTwo.setText("clickTwo");
        clickOne.addActionListener(e -> {
            thingOneRq.addCountBtn1();
        });

        clickTwo.addActionListener(e -> {
            thingOneRq.addCountBtn2();
        });

        // настройка листенера и объекта для ответа
        manageListener.setEvListener(new PanelListener());
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
                threadServerSender = new Thread(() -> {
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            httpSender.doPostRequest(thingOneRq, thingOneRs);
                            Thread.sleep(5000);
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
//        catch (IOException eio){
//            Logger.getLogger("Logger").warning("Подключение к серверу прервано! Повторите попытку");
//            try {
//                httpSender.closeClient();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            httpSender = null;
//            connectedLbl.setText("Соединение потеряно, повторите попытку подключения");
//            eio.printStackTrace();
//        }
                });
                threadServerSender.start();
                threadServerSender.setDaemon(true);
            }
        });

        panelOne.setMinimumSize(new Dimension(100,100));
        panelTwo.setMinimumSize(new Dimension(100,100));
        panelThree.setMinimumSize(new Dimension(100,100));
        panelFour.setMinimumSize(new Dimension(100,100));

        panelOne.setBackground(Color.BLACK);
        //panelOne.setSize(new Dimension(100,100));
        panelTwo.setBackground(Color.BLACK);
        panelThree.setBackground(Color.BLACK);
        panelFour.setBackground(Color.BLACK);
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
        SwingUtilities.invokeLater(()->new GuiProgramThingWorx("Program"));
    }
}
