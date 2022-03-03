package it;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AggregatorConnection {

    AggregatorConnection() {
    }

    public String doGet(String resource) {
        return doRequest(getUrl(resource),
                RequestMethod.GET,
                null);
    }

    public String doPost(String resource, String data) {
        return doRequest(getUrl(resource),
                RequestMethod.POST,
                data);
    }

    public String doDelete(String resource) {
        return doRequest(getUrl(resource),
                RequestMethod.DELETE,
                null);
    }

    private String getUrl(String resource) {
        return IntegrationTestConstants.URL + "/" + resource;
    }

    private String doRequest(String urlString,
                             RequestMethod requestMethod,
                             String data) {

        HttpURLConnection connection = null;
        try {
            connection = openHttpURLConnection(urlString, requestMethod.name());
            connection.setDoOutput(true);

            if (data != null && !data.trim().isEmpty()) {
                writeString(connection, data);
            }

            InputStream inputStream = connection.getInputStream();

            return getString(inputStream);
        } catch (Exception e) {
            handleException(connection, e);
        }

        // Execution would never get to this point but otherwise there's a compile-time error for no return statement.
        return "";
    }

    private void handleException(HttpURLConnection connection, Exception e) throws RuntimeException {
        if (connection != null && connection.getErrorStream() != null) {
            throw new RuntimeException(e.getMessage() + "\n" + getString(connection.getErrorStream()));
        } else {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    private HttpURLConnection openHttpURLConnection(String urlString, String requestMethod) throws IOException {

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("Content-Type", "application/json");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        connection.setConnectTimeout(20000);
        connection.setReadTimeout(20000);

        return connection;
    }

    private void writeString(HttpURLConnection connection, String str) {
        try (OutputStream os = connection.getOutputStream()) {
            byte[] inputBytes = str.getBytes(StandardCharsets.UTF_8);
            os.write(inputBytes, 0, inputBytes.length);
            os.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getString(InputStream inputStream) {

        try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }

            return result.toString(StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private enum RequestMethod {
        GET,
        POST,
        DELETE
    }
}