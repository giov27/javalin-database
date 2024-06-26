package id.dojo.controller;

import com.google.gson.Gson;
import id.dojo.helper.Res;
import id.dojo.model.Actor;
import id.dojo.model.FilmCategory;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.Map;

public class FilmCategoryController {
    static Gson gson = new Gson();
    public static Handler getFilmCategoryById = ctx -> {
        try{
            String filmCat = gson.fromJson(FilmCategory.getFilmCategory();



            ctx.json(FilmCategory.getFilmCategory());
        }
        catch (Exception e){
            e.printStackTrace();
            ctx.status(500).json(
                    gson.toJson(new Res(e.getMessage(), ""))
            );
        }
    };
}
