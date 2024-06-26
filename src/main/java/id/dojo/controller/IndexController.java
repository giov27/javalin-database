package id.dojo.controller;

import io.javalin.http.Handler;

public class IndexController {
    public static Handler indexApi = ctx -> ctx.result("Hello Javalin!");
}
