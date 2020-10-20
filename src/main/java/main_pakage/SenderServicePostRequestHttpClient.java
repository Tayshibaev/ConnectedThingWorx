package main_pakage;

import GuiProject.GuiProgramThingWorx;
import interfaces.InterfaceRs;
import listeners.EventRsManageListener;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import things.AbstractThingClass;
import things.request.ThingOneRq;
import things.response.ThingOneRs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SenderServicePostRequestHttpClient<T extends AbstractThingClass, T1 extends InterfaceRs> {
    private final String thingName;
    private final String serviceName;
    private final String appkey;
    private final String url;
    private ObjectMapper objMap = new ObjectMapper();
    private StringWriter writer;
    int timeout = 10;
    RequestConfig config = RequestConfig.custom()
            .setConnectTimeout(timeout * 1000)
            //.setConnectionRequestTimeout(timeout * 1000)
            //.setSocketTimeout(timeout * 1000)
            .build();
    private CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
//    private List<T> thingRq = new LinkedList<>();
//    private List<T1> thingRs = new LinkedList<>();
    private Map<T, T1> mapThingsRqRs = new LinkedHashMap<>();
    private HttpPost request;
    private int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    public SenderServicePostRequestHttpClient(String thingName, String serviceName, String appkey, String url) {
        this.thingName = "/Things/" + thingName;
        this.serviceName = "/Services/" + serviceName;
        this.appkey = appkey;
        this.url = url;
        //this.thingRq = thingRq;
        // this.thingRs = thingRs;
        request = new HttpPost(url + this.thingName + this.serviceName);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("accept", "application/json");
        request.setHeader("appKey", this.appkey);
        try {
            HttpResponse response1 = client.execute(request);
            statusCode = response1.getStatusLine().getStatusCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addThinqs(T thinqRq, T1 thingRs){
        mapThingsRqRs.put(thinqRq, thingRs);
    }

//
//    public void addThingRq(T thingRq) {
//        this.thingRq.add(thingRq);
//    }
//
//    public void addThings(T1 thingRs) {
//        this.thingRs.add(thingRs);
//    }
//
//    public List<T> getThingRq() {
//        return thingRq;
//    }
//
//    public List<T1> getThingRs() {
//        return thingRs;
//    }

    //исполнение запроса для всех вещей
    public void doPostRequestAllThinq() {
        mapThingsRqRs.forEach((x,y)->{
            doPostRequest(x,y);
        });
    }

    public void doPostRequest(T thingRq, T1 thingRs) {
        try {
            writer = new StringWriter();
            objMap.writeValue(writer, thingRq);
            System.out.println(String.format("Сформирован объект JSON для передачи данных на сервер %s", writer.toString()));
            request.setEntity(new StringEntity(writer.toString()));
            HttpResponse response = client.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufReader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }
            System.out.println(builder);
            System.out.println((T1) objMap.readValue(builder.toString(), thingRs.getClass()));
            thingRs.getObject((T1) objMap.readValue(builder.toString(), thingRs.getClass()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Закрывает соединение с клиентом. Необхоимо вызвать в конце работы с классом
     */
    public void closeClient() throws IOException {
        client.close();
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        ThingOneRq thingOneRq = new ThingOneRq();
        ThingOneRs thingOneRs = new ThingOneRs();
        EventRsManageListener eventRsManage = new EventRsManageListener();
        thingOneRs.setManageListener(eventRsManage);

        GuiProgramThingWorx gui = new GuiProgramThingWorx("ThingWorxConnected")
                .setThingOneRq(thingOneRq).setManageListener(eventRsManage);


        Thread.sleep(5000);

        SenderServicePostRequestHttpClient<AbstractThingClass, InterfaceRs> ssPr =
                new SenderServicePostRequestHttpClient<AbstractThingClass, InterfaceRs>("TwoThing", "Service2"
                        , "9adde0b8-fc59-43ba-89dd-5aaf03e1952c",
                        "https://pp-20091114071y.devportal.ptc.io/Thingworx");

        long a = System.currentTimeMillis() + 60000;
        Thread thread = new Thread(() -> {
            //  try {
            while (System.currentTimeMillis() < a) {
                ssPr.doPostRequest(thingOneRq, thingOneRs);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    try {
                        ssPr.closeClient();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
            //  }
//            catch (IOException e) {
//                Logger.getLogger("hi").warning("Подключение к серверу прервано! Повторите попытку");
//                e.printStackTrace();
//            }
        });
        thread.setDaemon(true);
        thread.start();

        thread.join();
        ssPr.closeClient();
    }
}
