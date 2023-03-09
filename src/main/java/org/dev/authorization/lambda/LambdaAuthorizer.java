package org.dev.authorization.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;


public class LambdaAuthorizer implements RequestHandler<Request, Response> {
    public Response handleRequest(Request event, Context context) {


        try {
            System.out.println(new ObjectMapper().writeValueAsString(event));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String authorizationToken = event.getAuthorizationToken();

        String auth = "Deny";
        String sub = JWTUtil.getSub(authorizationToken);
        if (sub != null) {
            auth = "Allow";
        }

        String methodArn = event.getMethodArn();

        String[] arnPartials = methodArn.split(":");
        String region = arnPartials[3];
        String awsAccountId = arnPartials[4];
        String[] apiGatewayArnPartials = arnPartials[5].split("/");
        String restApiId = apiGatewayArnPartials[0];
        String stage = apiGatewayArnPartials[1];
        String httpMethod = apiGatewayArnPartials[2];
        String resource = ""; // root resource
        if (apiGatewayArnPartials.length == 4) {
            resource = apiGatewayArnPartials[3];
        }


        System.out.println("Region == " + region + " , awsAccountId == " + awsAccountId + "restApiId == " + restApiId + " , stage == " + stage + " , httpMethod= " + httpMethod + " , resource=" + resource);

        String arn = String.format("arn:aws:execute-api:%s:%s:%s/%s/%s/%s", System.getenv("AWS_REGION"), awsAccountId,
                restApiId, stage, httpMethod, "*");
        Statement statement = Statement.builder().effect(auth).resource(arn).build();

        PolicyDocument policyDocument = PolicyDocument.builder().statements(Collections.singletonList(statement))
                .build();

        Response response = Response.builder().principalId(awsAccountId).policyDocument(policyDocument)
                .build();

        System.out.println("Response  =>> ");

        try {
            System.out.println(new ObjectMapper().writeValueAsString(response));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return response;
    }
}