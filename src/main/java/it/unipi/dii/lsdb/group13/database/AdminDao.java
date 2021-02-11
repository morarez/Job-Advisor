package it.unipi.dii.lsdb.group13.database;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.Arrays;
import java.util.LinkedHashMap;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.sortByCount;
import static com.mongodb.client.model.Aggregates.unwind;

public class AdminDao {

    public LinkedHashMap<String, Integer> rankCities(String jobType){
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
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
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        MongoCursor<Document> iterator = aggregate.iterator();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            map.put(next.getString("_id"), next.getInteger("count"));
        }
        iterator.close();

        return map;
    }
}
