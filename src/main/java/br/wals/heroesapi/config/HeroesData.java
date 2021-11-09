package br.wals.heroesapi.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import static br.wals.heroesapi.constants.HeroesConstant.ENDPOINT_DYNAMO;
import static br.wals.heroesapi.constants.HeroesConstant.REGION_DYNAMO;

public class HeroesData {

    public static void main(String[] args) throws Exception {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(ENDPOINT_DYNAMO, REGION_DYNAMO))
                .build();
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("Heroes_Api_Table");

        insertHero(table, "2", "Mulher Maravilha", "dc comics", 2);
        insertHero(table, "3", "Viúva Negra", "marvel", 2);
        insertHero(table, "4", "Capitão Marvel", "marvel", 2);
    }

    private static void insertHero(Table table, String id, String name, String universe, int films) {
        Item hero = new Item()
                .withPrimaryKey("id", id)
                .withString("name", name)
                .withString("universe", universe)
                .withNumber("films", films);
        PutItemOutcome outcome1 = table.putItem(hero);
    }
}
