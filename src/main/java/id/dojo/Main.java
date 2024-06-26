package id.dojo;
import com.google.gson.Gson;
import id.dojo.controller.ActorController;
import id.dojo.controller.FilmActorController;
import id.dojo.controller.FilmCategoryController;
import id.dojo.controller.IndexController;
import id.dojo.model.FilmActor;
import io.javalin.Javalin;


import static id.dojo.model.Actor.listActor;

public class Main {
    public static void main(String[] args) {
            Javalin app = Javalin.create(/*config*/)
                    .start(7070);
//            ROUTER
            app
                    .get("/", IndexController.indexApi)
                    .get("/actor", ActorController.listActorApi)
                    .get("/actor/<actor_id>", ActorController.getActorApiById)
                    .post("/actor", ActorController.postActor)
                    .put("/actor", ActorController.putActor)
//                  FILM-CATEGORY
                    .get("/film-category", FilmCategoryController.getFilmCategoryById)
//                    .get("/film-category/<film_id>", FilmCategoryController.getFilmCategoryById)
//                  FILM-ACTOR
                    .get("/film-actor/<film_id>", FilmActorController.getFilmActorById)
            ;
        }


}