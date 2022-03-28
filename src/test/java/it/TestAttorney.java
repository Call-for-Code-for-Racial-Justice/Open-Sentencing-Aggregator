package it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.ApiResponseMessage;

import static it.IntegrationTestConstants.ATTORNEY_RESOURCE;

public class TestAttorney {

    private final String attorneyId;
    private final ApiResponseMessage apiResponseMessage;

    private TestAttorney(ApiResponseMessage apiResponseMessage) {
        this.attorneyId = apiResponseMessage.getMessage();
        this.apiResponseMessage = apiResponseMessage;
    }

    public String getAttorneyId() {
        return attorneyId;
    }

    public ApiResponseMessage getApiResponseMessage() {
        return apiResponseMessage;
    }

    @Override
    public String toString() {
        return "TestAttorney{" +
                "attorneyId='" + attorneyId + '\'' +
                ", apiResponseMessage=" + apiResponseMessage +
                '}';
    }

    public static class Builder {

        private String json = "";

        public Builder() {}

        public Builder withoutCase() {
            json = "{\n" +
                    "  \"_id\": null,\n" +
                    "  \"_rev\": null,\n" +
                    "  \"username\": \"string\",\n" +
                    "  \"cases\": null\n" +
                    "}";

            return this;
        }

        public Builder withCase() {
            json = "{\n" +
                    "  \"_id\": null,\n" +
                    "  \"_rev\": null,\n" +
                    "  \"username\": \"string\",\n" +
                    "  \"cases\": [\n" +
                    "    {\n" +
                    "      \"_id\": null,\n" +
                    "      \"_rev\": null,\n" +
                    "      \"client_id\": \"string\",\n" +
                    "      \"attorney_id\": \"string\",\n" +
                    "      \"possible_charges\": [\n" +
                    "        {\n" +
                    "          \"_id\": null,\n" +
                    "          \"_rev\": null,\n" +
                    "          \"trial_type\": \"Guilty plea\",\n" +
                    "          \"disposition_charged_class\": \"string\",\n" +
                    "          \"charge_code\": \"Administration of Justice\",\n" +
                    "          \"attempted\": true,\n" +
                    "          \"primary\": true,\n" +
                    "          \"controlled_substance_quantity_level\": 17,\n" +
                    "          \"possible_sentences\": [\n" +
                    "            {\n" +
                    "              \"_id\": null,\n" +
                    "              \"_rev\": null,\n" +
                    "              \"chargeDisposition\": \"BFW\",\n" +
                    "              \"phase\": \"string\",\n" +
                    "              \"sentence_type\": \"Prison Only\",\n" +
                    "              \"commitmentTerm\": 0,\n" +
                    "              \"commitmentUnit\": \"string\",\n" +
                    "              \"commitmentType\": \"string\",\n" +
                    "              \"minimum_incarceration_months\": 0,\n" +
                    "              \"maximum_incarceration_months\": 0,\n" +
                    "              \"probation_term_months\": 0,\n" +
                    "              \"minimum_probation_months\": 0,\n" +
                    "              \"maximum_probation_months\": 0,\n" +
                    "              \"fine_dollars\": 0,\n" +
                    "              \"minimum_fine_dollars\": 0,\n" +
                    "              \"maximum_fine_dollars\": 0,\n" +
                    "              \"community_service_hours\": 0,\n" +
                    "              \"restitution\": \"string\",\n" +
                    "              \"alternative_sentence\": \"string\",\n" +
                    "              \"suspended_sentence\": true\n" +
                    "            }\n" +
                    "          ]\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            return this;
        }

        public TestAttorney create() {
            try {
                if(json.isEmpty()) {
                    throw new RuntimeException("json must be set");
                }

                String jsonResponse = new AggregatorConnection().doPost(ATTORNEY_RESOURCE, json);
                ApiResponseMessage apiResponseMessage = new ObjectMapper().readValue(jsonResponse, ApiResponseMessage.class);
                return new TestAttorney(apiResponseMessage);
            } catch(JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
