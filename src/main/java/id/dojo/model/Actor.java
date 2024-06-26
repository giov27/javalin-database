package id.dojo.model;

import com.google.gson.Gson;
import id.dojo.PgConnection;
import id.dojo.helper.DBResponse;
import id.dojo.helper.DBUtils;
import id.dojo.helper.Res;
import io.javalin.http.Handler;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Actor {
//    private int actor_id;
//    private String first_name;
//    private String last_name;
//    private Timestamp last_update;
    private int actor_id;
    private String first_name;
    private String last_name;
    private Timestamp last_update;

    public Actor(){
    }

    public Actor(int actor_id, String first_name, String last_name){
        this.actor_id = actor_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }


    static Sql2o sql2o = PgConnection.getSql2o();
    static Gson gson = new Gson();

    public static String listActor(Map<String, String> params){
        String sql = "SELECT actor_id, first_name, last_name, last_update FROM actor WHERE TRUE ";


        String where = "";

        if(params.get("first_name") != null && params.get("first_name") != ""){
//            where += String.format(" AND %s ILIKE '%%' ", "first_name", params.get("first_name"));
            where += " AND first_name ILIKE '%"+params.get("first_name")+"%' ";
        }
//        if(param.get("acto"))
//
//        Map<String, Object> paramsList = new HashMap<String, Object>();
//        paramsList.put("p1", "Nick");
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            if(!entry.getValue().equals(null) && !entry.getValue().equals("")){
//                where += String.format(" AND %s = '%s' ", entry.getKey(), entry.getValue());
//            }
//            System.out.println(entry.getKey() + " => " + entry.getValue());
//        }
        System.out.println(sql + where);

        List<Object> paramsList = new ArrayList<>();
//        paramsList.add("Nick");


        Res data = new DBUtils().list(
                sql + where +
//                "SELECT actor_id, first_name, last_name, last_update FROM actor " +
//                        " WHERE first_name = :p1 " +
                        " ORDER BY actor_id;",
                paramsList,
                Actor.class
        );
        return gson.toJson(data);
    }

    public static String getActor(Object actor_id){
        Res data = new DBUtils().get(
                "SELECT actor_id, first_name, last_name, last_update FROM actor WHERE actor_id = :p1;",
                actor_id,
                Actor.class
        );
        return gson.toJson(data);
    }

    public static String postActor(Actor actor){
        Res data = new DBUtils().update(
                "INSERT INTO actor (first_name, last_name) " +
                        "VALUES ( :first_name, :last_name );",
                actor,
                "menambahkan"
        );
        return gson.toJson(data);
    }

    public static String updateActor(Actor actor){
        Res data = new DBUtils().update(
                "UPDATE actor SET " +
                        " first_name = :first_name , " +
                        " last_name = :last_name " +
                        " WHERE actor_id = :actor_id ",
                actor,
                "mengubah"
        );
        return gson.toJson(data);
    }

    @Override
    public String toString(){
        return "id : " + this.actor_id +
                " first_name : " + this.first_name +
                " last_name : " + this.last_name +
                " last_update : " + this.last_update + "\n";
    }

    public int getActor_id() {
        return actor_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }
}
