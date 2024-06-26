package id.dojo.model;

import com.google.gson.Gson;
import id.dojo.PgConnection;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class FilmActor {
    private Integer film_id;
    private Integer actor_id;
    private List<Actor> actors;

    public FilmActor(){

    }

    public FilmActor(Integer filmId, List<Actor> actors){
        this.film_id = filmId;
        this.actors = actors;
    }

    static Sql2o sql2o = PgConnection.getSql2o();
    static Gson gson = new Gson();

    public static String getFilmActor(Object filmId){
        String query = "SELECT fa.film_id, fa.actor_id FROM film_actor fa " +
                "JOIN film_actor a ON a.actor_id = fa.actor_id  " +
                "LIMIT 20;";
        try (Connection con = sql2o.open()) {
            List<FilmActor> list = con.createQuery(query).executeAndFetch(FilmActor.class);
//            System.out.println(list);

            FilmActor filmActors = new FilmActor(Integer.valueOf(filmId.toString()), new ArrayList<>());

            for(FilmActor fa : list){
                System.out.println(fa.actor_id);
                System.out.println(fa.film_id);

                Actor data = con
                        .createQuery("SELECT actor_id, first_name, last_name FROM actor WHERE actor_id = :p1;")
                        .withParams(fa.actor_id)
                        .executeAndFetchFirst(Actor.class);
//                System.out.println(data);
                filmActors.actors.add(data);
            }
            return gson.toJson(filmActors);
        }
        catch (Sql2oException sql2oException) {
            sql2oException.printStackTrace();
            return "";
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }


}
