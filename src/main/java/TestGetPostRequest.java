

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;

public class TestGetPostRequest {
    static String url = "https://pp-20091114071y.devportal.ptc.io/Thingworx";
    static String appKey = "appKey=9adde0b8-fc59-43ba-89dd-5aaf03e1952c";
    static String appKeyOnly = "9adde0b8-fc59-43ba-89dd-5aaf03e1952c";
    static StringBuilder stringBuilderPost = new StringBuilder();
    static StringBuilder stringBuilderGet = new StringBuilder();


    public static void main(String[] args) throws IOException, InterruptedException {
//        HttpURLConnection connection = (HttpURLConnection)
//                new URL(url + "/Things/OneThings/Services/Service1?" + appKey).openConnection();
//
//        connection.setRequestMethod("GET");
//        connection.setRequestProperty("accept", "application/json");
//        System.out.println(connection.getPermission());
//        System.out.println(connection.getURL());
//        //  connection.setReadTimeout(60);
//        int responseCode = connection.getResponseCode();
//        System.out.println(responseCode);
//        System.out.println(connection.getResponseMessage());
//        System.out.println(connection.getHeaderFields());
//        if (responseCode == 200) {
          //  String response = "";
//
          //  Scanner scanner = new Scanner(connection.getInputStream());
//            while (scanner.hasNextLine()) {
//                response += scanner.nextLine();
//                response += "\N";
//            }
//
//            System.out.println(connection.getRequestMethod());
//            System.out.println(response);
//            Gson gson = new Gson();
//            ThingInThingworx th = gson.fromJson(response, ThingInThingworx.class);
//
//            System.out.println("OneParam = " + th.oneParam);
//            System.out.println("lamp2 = " + th.lamp2);
//            scanner.close();
//        }

//
//        HttpURLConnection urlConnectionPost = (HttpURLConnection) new URL(url + "/Things/OneThings/Services/Service1"
//                +
//                "?"
//                +
//             //    "OneParam=9&" +
//                "lamp2=5"
//       // +       appKey
//        ).openConnection();
//
//
//        urlConnectionPost.setRequestMethod("POST");
//        urlConnectionPost.setRequestProperty("Content-Type", "application/json");//передача данных в json
//        urlConnectionPost.setRequestProperty("accept", "application/json");//получение данных из json
//        urlConnectionPost.setRequestProperty("appKey", "9adde0b8-fc59-43ba-89dd-5aaf03e1952c");//получение данных из json
//
//        int responseCode1 = urlConnectionPost.getResponseCode();
//        System.out.println(urlConnectionPost.getPermission());
//        System.out.println(urlConnectionPost.getURL());
//        urlConnectionPost.setReadTimeout(60);
//        System.out.println(responseCode1);
//        System.out.println(urlConnectionPost.getResponseMessage());
//        System.out.println(urlConnectionPost.getHeaderFields());
//        if (responseCode1 == 200) {
//            String response = "";
//            Scanner scanner = new Scanner(urlConnectionPost.getInputStream());
//            while (scanner.hasNextLine()) {
//                response += scanner.nextLine();
//                response += "\N";
//            }
//
//            System.out.println(response);
//            Gson gson = new Gson();
//            ThingInThingworx th = gson.fromJson(response, ThingInThingworx.class);
//
//            System.out.println("OneParam = " + th.OneParam);
//            System.out.println("lamp2 = " + th.lamp2);
//            // System.out.println(urlConnectionPost.getContentType());
//            scanner.close();
//        }


        /**
        *
        * */

//        MonitoringPropertiesFromThings mm =
//                new MonitoringPropertiesFromThings("OneThings","Service1","9adde0b8-fc59-43ba-89dd-5aaf03e1952c");
//        Thread t = new Thread(mm);
//        t.setDaemon(true);
//        t.start();
//
//        t.join();


        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost request = new HttpPost(url+"/Things/OneThings/Services/Service1");
            request.setHeader("Content-Type", "application/json");
            request.setHeader("accept", "application/json");
            request.setHeader("appKey", appKeyOnly);

            ObjectMapper objMap = new ObjectMapper();
            StringWriter writer = new StringWriter();
            ThingInThingworx thing = new ThingInThingworx();
            thing.setLamp2(7);
            thing.setOneParam(44);
            objMap.writeValue(writer,thing);

            System.out.println(writer.toString());
            request.setEntity(new StringEntity(writer.toString()));

            System.out.println(request);
            HttpResponse response = client.execute(request);

            System.out.println(response);
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

            ThingInThingworx thing1;
            thing1 = objMap.readValue(builder.toString(),ThingInThingworx.class);
            System.out.println("Lamp2 = ");
            System.out.println("OnePa = ");

            request.setEntity(new StringEntity("{\"OneParam\" : \"13\", \"lamp2\" : \"90\"}"));

            response = client.execute(request);

            System.out.println(response.getStatusLine().getStatusCode());
            bufReader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            builder = new StringBuilder();

            while ((line = bufReader.readLine()) != null) {

                builder.append(line);
                builder.append(System.lineSeparator());
            }

            System.out.println(builder);
        }



//            assertEquals(200, response.getStatusLine().getStatusCode());
//            assertEquals("application/json", response.getEntity().getContentType().getValue());
//            final String json = EntityUtils.toString(response.getEntity());
//            final JsonNode node = mapper.readTree(json);
//            assertEquals(1, node.get("result").get("data").get(GraphSONTokens.VALUEPROP).size());
    }





    /**
    * Работает только для однократного запроса, необходимо будет каждый раз новый объект создавать для циклического опроса сервера. Поэтому этот вариант не подходит
     * Этот вариант передает параметры в строке запроса, а не как отдельные параметры
    * */
    public static class MonitoringPropertiesFromThings implements Runnable {

        StringBuilder request = new StringBuilder().append(url).append("/Things/");
        int responeCode = 200;
        HttpURLConnection urlConnectionPost;
        boolean flag = true;
        long timeout = 7000;

        public void disableFlag(){
            flag = false;
        }

        public void setTimeout(long timeout){
            this.timeout = timeout;
        }

        public MonitoringPropertiesFromThings(String thingName, String serviceName, String appkeyHeader) throws IOException {
            this.request.append(thingName+"/").append("Services/").append(serviceName+"?").append("OneParam=9&lamp2=5");
            urlConnectionPost = (HttpURLConnection) new URL(request.toString()).openConnection();
            urlConnectionPost.setRequestMethod("POST");
            urlConnectionPost.setRequestProperty("Content-Type", "application/json");//передача данных в json
            urlConnectionPost.setRequestProperty("accept", "application/json");//получение данных из json
            urlConnectionPost.setRequestProperty("appKey", appkeyHeader);//получение данных из json
        }

        @Override
        public void run() {
            try(Scanner scanner = new Scanner(urlConnectionPost.getInputStream());) {
                StringBuffer response = new StringBuffer();
                ThingInThingworx th;
                while (responeCode == 200 && flag) {
                    responeCode = urlConnectionPost.getResponseCode();
                    if (responeCode == 200) {
                        while (scanner.hasNextLine()) {
                            response.append(scanner.nextLine());
                            response.append("\n");
                        }
                        if(response.length()!=0) {
                            System.out.println(response.toString());
                            Gson gson = new Gson();
                            th = gson.fromJson(response.toString(), ThingInThingworx.class);
                            response.delete(0, response.length());
                            System.out.println("OneParam = ");
                            System.out.println("lamp2 = ");
                        } else {
                            System.out.println("response пустой");
                        }
                    }
                    Thread.sleep(7000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * С инета метод для образца
     * */
//    public static String getPersonData(String name) throws IOException {
//
//        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/people/" + name).openConnection();
//
//        connection.setRequestMethod("GET");
//
//        int responseCode = connection.getResponseCode();
//        if (responseCode == 200) {
//            String response = "";
//            Scanner scanner = new Scanner(connection.getInputStream());
//            while (scanner.hasNextLine()) {
//                response += scanner.nextLine();
//                response += "\N";
//            }
//            scanner.close();
//
//            return response;
//        }
//
//        // an error happened
//        return null;
//    }


}
