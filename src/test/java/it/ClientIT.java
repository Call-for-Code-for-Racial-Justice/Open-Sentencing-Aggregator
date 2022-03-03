package it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.ApiResponseMessage;
import io.swagger.model.Client;
import io.swagger.model.ClientResponse;
import org.junit.Test;

import static it.IntegrationTestConstants.CLIENT_RESOURCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClientIT {

    private final AggregatorConnection conn = new AggregatorConnection();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void addClient_addsClient() {

        ApiResponseMessage apiResponseMessage = new TestClient.Builder(addAttorney())
                .create()
                .getApiResponseMessage();
        assertEquals(ApiResponseMessage.OK, apiResponseMessage.getCode());
        assertNotNull(apiResponseMessage.getMessage());
    }

    @Test
    public void deleteClient_deletesClient() throws JsonProcessingException {

        String clientId = addClient(addAttorney());

        String json = conn.doDelete(CLIENT_RESOURCE + "/" + clientId);
        ApiResponseMessage apiResponseMessage = objectMapper.readValue(json, ApiResponseMessage.class);
        assertEquals(ApiResponseMessage.OK, apiResponseMessage.getCode());
        assertEquals(clientId, apiResponseMessage.getMessage());
    }

    @Test
    public void getClients_getClientById() throws JsonProcessingException {

        String clientId = addClient(addAttorney());

        String json = conn.doGet(CLIENT_RESOURCE + "?clientId=" + clientId);
        Client client = objectMapper.readValue(json, Client.class);
        assertEquals(clientId, client.getId());
    }

    @Test
    public void getClients_getClientByAttorneyId() throws JsonProcessingException {

        String attorneyId = addAttorney();
        String clientId = addClient(attorneyId);

        String json = conn.doGet(CLIENT_RESOURCE + "?attorneyId=" + attorneyId);
        ClientResponse clientResponse = objectMapper.readValue(json, ClientResponse.class);
        assertEquals(200, clientResponse.getCode().intValue());
        assertEquals(1, clientResponse.getClients().size());
        assertEquals(clientId, clientResponse.getClients().get(0).getId());
    }

    @Test
    public void getClients_getClientByClientIdAndAttorneyId() throws JsonProcessingException {

        String attorneyId = addAttorney();
        String clientId = addClient(attorneyId);

        String json = conn.doGet(CLIENT_RESOURCE + "/?clientId=" + clientId + "&attorneyId=" + attorneyId);
        ClientResponse clientResponse = objectMapper.readValue(json, ClientResponse.class);
        assertEquals(200, clientResponse.getCode().intValue());
        assertEquals(1, clientResponse.getClients().size());
        assertEquals(clientId, clientResponse.getClients().get(0).getId());
    }

    private String addClient(String attorneyId) {
       return new TestClient.Builder(attorneyId)
                .create()
                .getClientId();
    }

    private String addAttorney() {

        TestAttorney testAttorney = new TestAttorney.Builder()
                .withoutCase()
                .create();
        return testAttorney.getAttorneyId();
    }
}
