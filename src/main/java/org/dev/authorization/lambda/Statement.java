package org.dev.authorization.lambda;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.UUID;

@JsonDeserialize(builder = Statement.Builder.class)
public class Statement {

//    public final String Action = "lambda:InvokeFunction";
    public final String Action = "execute-api:Invoke";

    public String Effect;
    public String Resource;

//    public String Principle="apigateway.amazonaws.com";
//
//    public String FunctionName="arn:aws:lambda:ap-south-1:410529460453:function:macquariePOCLambda2";
//
//    public String StatementId= UUID.randomUUID().toString();
//     public static String SourceArn= "arn:aws:execute-api:ap-south-1:410529460453:fs3ssd5khl/*/*/macquariePOCLambda2";
    private Statement(Builder builder) {
        this.Effect = builder.effect;
        this.Resource = builder.resource;
    }
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String effect;
        private String resource;
        private Builder() { }
        public Builder effect(String effect) {
            this.effect = effect;
            return this;
        }
        public Builder resource(String resource) {
            this.resource = resource;
            return this;
        }
        public Statement build() {
            return new Statement(this);
        }
    }
}