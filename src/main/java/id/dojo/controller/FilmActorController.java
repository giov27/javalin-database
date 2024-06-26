package id.dojo.controller;

import com.google.gson.Gson;
import id.dojo.helper.Res;
import id.dojo.model.FilmActor;
import id.dojo.model.FilmCategory;
import io.javalin.http.Handler;

public class FilmActorController {
    static Gson gson = new Gson();
    public static Handler getFilmActorById = ctx -> {
        String filmId = ctx.pathParam("film_id");
        try{
            ctx.json(
                    FilmActor.getFilmActor(Integer.valueOf(filmId))
            );
        }catch (NumberFormatException numberFormatException){
            numberFormatException.printStackTrace();
            ctx.status(500).json(
                    gson.toJson(new Res("Id harus integer ", ""))
            );
        }
        catch (Exception e){
            e.printStackTrace();
            ctx.status(500).json(
                    gson.toJson(new Res(e.getMessage(), ""))
            );
        }
    };
}
