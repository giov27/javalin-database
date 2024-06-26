package id.dojo.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import id.dojo.helper.DBResponse;
import id.dojo.helper.Res;
import id.dojo.model.Actor;
import io.javalin.http.Handler;
import org.sql2o.Connection;

import java.util.HashMap;
import java.util.Map;

public class ActorController {
    static Gson gson = new Gson();
    public static Handler listActorApi = ctx -> {
        Map<String, String> paramsList = new HashMap<String, String>();

        String actorId = ctx.queryParam("actor_id");
        String firstName = ctx.queryParam("first_name");

        paramsList.put("actor_id", actorId);
        paramsList.put("first_name", firstName);

        for (Map.Entry<String, String> entry : paramsList.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }

        ctx.json(Actor.listActor(paramsList));
    };

    public static Handler getActorApiById = ctx -> {
        String actorId = ctx.pathParam("actor_id");
        try{
            ctx.json(
                    Actor.getActor(Integer.valueOf(actorId))
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

    public static Handler postActor = ctx -> {
        Map<String, Object> payload = gson.fromJson(ctx.body(), new TypeToken<Map<String, Object>>(){}.getType());

        Object firstname = payload.get("first_name");
        Object lastname = payload.get("last_name");
        Actor actor = new Actor(-1, firstname.toString(), lastname.toString());
        System.out.println(actor);
        try{
            ctx.json(
                    Actor.postActor(actor)
            );
        } catch (Exception e){
            e.printStackTrace();
            ctx.status(500).json(
                    gson.toJson(new Res(e.getMessage(), ""))
            );
        }

    };

    public static Handler putActor = ctx -> {
        Map<String, Object> payload = gson.fromJson(ctx.body(), new TypeToken<Map<String, Object>>(){}.getType());

        Object actorId = payload.get("actor_id");
        Object firstname = payload.get("first_name");
        Object lastname = payload.get("last_name");
        Actor actor = new Actor(Double.valueOf(actorId.toString()).intValue(), firstname.toString(), lastname.toString());
        System.out.println(actor);
        try{
            ctx.json(
                    Actor.updateActor(actor)
            );
        } catch (Exception e){
            e.printStackTrace();
            ctx.status(500).json(
                    gson.toJson(new Res(e.getMessage(), ""))
            );
        }

    };
}
