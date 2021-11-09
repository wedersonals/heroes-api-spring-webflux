package br.wals.heroesapi.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.Arrays;

import static br.wals.heroesapi.constants.HeroesConstant.ENDPOINT_DYNAMO;
import static br.wals.heroesapi.constants.HeroesConstant.REGION_DYNAMO;

public class HeroesTable {

    public static void main(String[] args) throws Exception {
        createTable();
    }

    private static void createTable() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(ENDPOINT_DYNAMO, REGION_DYNAMO))
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        String tableName = "Heroes_Api_Table";
        try {
            System.out.println("Criando tabela, aguarde...");
            Table table = dynamoDB.createTable(tableName,
                    Arrays.asList(new KeySchemaElement("id", KeyType.HASH)),
                    Arrays.asList(new AttributeDefinition("id", ScalarAttributeType.S)),
                    new ProvisionedThroughput(5L, 5L));
            table.waitForActive();
            System.out.println("Successo " + table.getDescription().getTableStatus());
        } catch (Exception e) {
            System.err.println("Não foi possível criar a tabela");
            System.err.println(e.getMessage());
        }
    }
}