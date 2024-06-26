package id.dojo.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import id.dojo.PgConnection;
import id.dojo.helper.DBUtils;
import id.dojo.helper.Res;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.Map;

public class FilmCategory {
    private Integer film_id;
    private Integer category_id;
    private java.sql.Timestamp last_update;
    private Film film;

    public FilmCategory(){

    }

    public FilmCategory(FilmCategory fc, Film film){
        this.film_id = fc.film_id;
        this.category_id = fc.category_id;;
        this.last_update = fc.last_update;
        this.film = film;
    }

    static Sql2o sql2o = PgConnection.getSql2o();
    static Gson gson = new Gson();

    public static String getFilmCategory2(Object filmId){
        Res data = new DBUtils().get(
                "SELECT film_id, category_id, last_update FROM film_category WHERE film_id = :p1;",
                filmId,
                FilmCategory.class
        );
        return gson.toJson(data);
    }

    public static String getFilmCategory(){
        String query = "SELECT fc.film_id, fc.category_id, fc.last_update FROM film_category fc " +
                "JOIN film f ON f.film_id = fc.film_id ;";

        List<FilmCategory> filmCats = new DBUtils<List<FilmCategory>>();
        try (Connection con = sql2o.open()) {
            List<FilmCategory> list = con.createQuery(query).executeAndFetch(FilmCategory.class);

            for(FilmCategory fc : list){
                Film data = con
                        .createQuery("SELECT film_id, title, description FROM film WHERE film_id = :p1;")
                        .withParams(fc.film_id)
                        .executeAndFetchFirst(Film.class);
                fc.film = data;
            }
            return gson.toJson(list);
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
