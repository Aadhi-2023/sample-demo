package Handler;

import com.azure.cosmos.models.CosmosItemResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;
import Functions.Functions;

import java.util.HashMap;
import java.util.Map;

public class Handler {

    private final Functions functions = new Functions();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @FunctionName("postServiceData")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> request,
            final ExecutionContext context) {

        try {
            String requestBody = request.getBody();
            if (requestBody == null || requestBody.isEmpty()) {
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                        .body("Please pass a valid JSON in the request body")
                        .build();
            }

            Map<String, Object> serviceData = objectMapper.readValue(requestBody, Map.class);
            Map<String, String> validationErrors = validateRequest(serviceData);

            if (!validationErrors.isEmpty()) {
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                        .body(validationErrors)
                        .build();
            }

            CosmosItemResponse<Object> cosmosResponse = functions.getContainer().createItem(serviceData);
            Map<String, Object> response = new HashMap<>();
            response.put("id", cosmosResponse.getItem());
            response.put("status", "success");

            return request.createResponseBuilder(HttpStatus.OK)
                    .body(response)
                    .build();

        } catch (Exception e) {
            context.getLogger().severe("Error processing request: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating document in Cosmos DB")
                    .build();
        }
    }

    private Map<String, String> validateRequest(Map<String, Object> serviceData) {
        Map<String, String> errors = new HashMap<>();
        if (!serviceData.containsKey("requiredField")) {
            errors.put("requiredField", "This field is required.");
        }
        return errors;
    }
}
