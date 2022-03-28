package it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.ApiResponseMessage;

import static it.IntegrationTestConstants.CLIENT_RESOURCE;

public class TestClient {

    private final String clientId;
    private final String attorneyId;
    private final ApiResponseMessage apiResponseMessage;

    private TestClient(String attorneyId, ApiResponseMessage apiResponseMessage) {
        this.clientId = apiResponseMessage.getMessage();
        this.attorneyId = attorneyId;
        this.apiResponseMessage = apiResponseMessage;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAttorneyId() {
        return attorneyId;
    }

    public ApiResponseMessage getApiResponseMessage() {
        return apiResponseMessage;
    }

    @Override
    public String toString() {
        return "TestClient{" +
                "clientId='" + clientId + '\'' +
                ", apiResponseMessage=" + apiResponseMessage +
                '}';
    }

    public static class Builder {

        private final String attorneyId;
        private final String json;

        public Builder(String attorneyId) {

            this.attorneyId = attorneyId;

            json = "{\n" +
                    "  \"_id\": null,\n" +
                    "  \"_rev\": null,\n" +
                    "  \"attorney_id\": \"" + attorneyId + "\",\n" +
                    "  \"race\": \"White\",\n" +
                    "  \"gender\": \"Female\",\n" +
                    "  \"criminal_history_category\": \"Category I\"\n" +
                    "}";
        }

        public TestClient create() {
            try {
                String jsonResponse = new AggregatorConnection().doPost(CLIENT_RESOURCE, json);
                ApiResponseMessage apiResponseMessage = new ObjectMapper().readValue(jsonResponse, ApiResponseMessage.class);
                return new TestClient(attorneyId, apiResponseMessage);
            } catch(JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
