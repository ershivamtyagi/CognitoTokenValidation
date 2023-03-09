package org.dev.authorization.lambda;

public class Request {

    private String methodArn;
    private String authorizationToken;


    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public void setMethodArn(String methodArn) {
        this.methodArn = methodArn;
    }

    public String getMethodArn() {
        return methodArn;
    }
}
