package it.unipi.dii.lsdb.group13.database;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.function.Consumer;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;

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
        // Rank cities based on # of Part-Time jobs
        AggregateIterable<Document> aggregate = mongoDB.getJobOffersCollection().aggregate(Arrays.asList(m, g, s,l));
        MongoCursor<Document> iterator = aggregate.iterator();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            Document loc = (Document) next.get("_id");
            map.put(loc.getString("state") + " - " + loc.getString("city"), next.getInteger("count"));
        }
        return map;
    }

    public LinkedHashMap<String, Integer> rankSkills(){
        MongoDBManager mongoDB = MongoDBManager.getInstance();
        Bson uw = unwind("$skills");
        Bson sbc = sortByCount("$toLower:{$trim:{input:\"$skills\"}}");
        Bson limit = limit(10);
        AggregateIterable aggregate = mongoDB.getJobSeekersCollection().aggregate(Arrays.asList(uw, sbc,limit));
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        MongoCursor<Document> iterator = aggregate.iterator();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            map.put(next.getString("_id"), next.getInteger("count"));
        }
        return map;
    }

   public void rankCompanies(){

   }
}
