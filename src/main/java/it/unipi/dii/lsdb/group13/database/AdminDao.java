package it.unipi.dii.lsdb.group13.database;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.TransactionWork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.sortByCount;
import static com.mongodb.client.model.Aggregates.unwind;

public class AdminDao {

    public LinkedHashMap<String, Integer> rankCities(String jobType){
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        // Rank cities based on # of (job_type) jobs
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        mongoDB.getJobOffersCollection();
        Bson m = match(eq("job_type",jobType));
        Bson g = group("$location", Accumulators.sum("count",1));
        Bson s = sort(Sorts.descending("count"));
        Bson l = limit(10);
        AggregateIterable<Document> aggregate = mongoDB.getJobOffersCollection().aggregate(Arrays.asList(m, g, s,l));
        MongoCursor<Document> iterator = aggregate.iterator();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            Document loc = (Document) next.get("_id");
            map.put(loc.getString("state") + " - " + loc.getString("city"), next.getInteger("count"));
        }
        iterator.close();
        return map;
    }

    public LinkedHashMap<String, Integer> rankSkills(){
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        MongoCollection collection = mongoDB.getJobSeekersCollection();
        Bson uw = unwind("$skills");
        // trim operator to remove the whitespace characters and toLower operator to change all to lower case
        Bson sb = sortByCount(eq("$toLower", eq("$trim", eq("input", "$skills"))));
        Bson limit = limit(10);
        AggregateIterable aggregate = collection.aggregate(Arrays.asList(uw, sb, limit));
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        MongoCursor<Document> iterator = aggregate.iterator();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            map.put(next.getString("_id"), next.getInteger("count"));
        }
        iterator.close();
        return map;
    }

    public List<String> findTopCompanies() {
        Neo4jManager neo4j = Neo4jManager.getInstance();
        List<String> names = new ArrayList<>();
        try (org.neo4j.driver.Session session = neo4j.getDriver().session()) {
            session.readTransaction((TransactionWork<List<String>>) tx -> {
                Result result = tx.run("MATCH(co:Company)<-[r:FOLLOWS]-(js:JobSeeker)" +
                        " WITH co, count (r) as rels"+" RETURN co.name AS name, rels AS relation" + " ORDER BY rels DESC"
                        + " LIMIT 10");
                while(result.hasNext()) {
                    Record r = result.next();
                    names.add(r.get("name").asString()+" : "+ r.get("relation")+" followers");
                }
                return names;
            });
        }
        return names;
    }

    public List<Long> statistics (){
        ArrayList<Long> stats = new ArrayList<>();
        try {
            MongoDBManager mongoDB = MongoDBManager.getInstance();
            stats.add(mongoDB.getJobSeekersCollection().countDocuments());
            stats.add(mongoDB.getCompaniesCollection().countDocuments());
            stats.add(mongoDB.getJobOffersCollection().countDocuments());
        }catch (Exception e){
            e.printStackTrace();
        }
        return stats;
    }

}
