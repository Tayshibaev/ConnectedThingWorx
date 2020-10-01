import GuiProject.GuiProgramThingWorx;
import interfaces.InterfaceRs;
import listeners.EventRsManageListener;
import org.apache.http.HttpResponse;
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

public class SenderServicePostRequestHttpClient<T, T1 extends InterfaceRs> {
    private final String thingName;
    private final String serviceName;
    private final String appkey;
    private final String url;
    private ObjectMapper objMap = new ObjectMapper();
    private StringWriter writer;
    private CloseableHttpClient client = HttpClientBuilder.create().build();
    private T thingRq;
    private T1 thingRs;
    private HttpPost request;

    public SenderServicePostRequestHttpClient(String thingName, String serviceName, String appkey, String url, T thingRq, T1 thingRs) {
        this.thingName = "/Things/" + thingName;
        this.serviceName = "/Services/" + serviceName;
        this.appkey = appkey;
        this.url = url;
        this.thingRq = thingRq;
        this.thingRs = thingRs;
        request = new HttpPost(url + this.thingName + this.serviceName);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("accept", "application/json");
        request.setHeader("appKey", this.appkey);
    }

    public void setThingRq(T thingRq) {
        this.thingRq = thingRq;
    }

    public T getThingRq() {
        return thingRq;
    }

    public void doPostRequest() {
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
                        "https://pp-20091114071y.devportal.ptc.io/Thingworx", thingOneRq, thingOneRs);

        long a = System.currentTimeMillis() + 60000;
        Thread thread = new Thread(() -> {
            while (System.currentTimeMillis() < a) {
                ssPr.doPostRequest();
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
        });
        thread.setDaemon(true);
        thread.start();

        thread.join();
        ssPr.closeClient();
    }
}
