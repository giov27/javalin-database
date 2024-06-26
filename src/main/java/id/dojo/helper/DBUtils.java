package id.dojo.helper;

import com.google.gson.Gson;
import id.dojo.PgConnection;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBUtils<T> {
    static private Sql2o sql2o = PgConnection.getSql2o();

//    public DBResponse<List<T>> list(String query, Map<String, Object> params, Class<T> mapper) {
    public Res<List<T>> list(String query, List<Object> params, Class<T> mapper) {
        try (Connection con = sql2o.open()) {
            List<T> list = con.createQuery(query).executeAndFetch(mapper);
            return new Res<List<T>>("", list);
        }
        catch (Sql2oException sql2oException) {
            sql2oException.printStackTrace();
            return new Res(null, sql2oException.toString());
        } catch (Exception e){
            e.printStackTrace();
            return new Res(null, e.toString());
        }
    }

    public Res<T> get(String query,Object param, Class<T> mapper) {
        try (Connection con = sql2o.open()) {
            T list = con.createQuery(query).withParams(param).executeAndFetchFirst(mapper);
            return new Res<T>("", list);
        } catch (Sql2oException sql2oException) {
            return new Res(sql2oException.toString(),null);
        } catch (Exception e){
            return new Res(e.toString(), null);
        }
    }

    public Res<T> update(String query, T mapper, String method) {
        try (Connection con = sql2o.open()) {
            Object post = con.createQuery(query).bind(mapper).executeUpdate().getKey();
            return new Res(String.format("Berhasil %s Data.", method), post);
        } catch (Sql2oException sql2oException) {
            sql2oException.printStackTrace();
            return new Res(sql2oException.toString(),null);
        } catch (Exception e){
            return new Res(e.toString(), null);
        }
    }

    public T getPlain(String query,Object param, Class<T> mapper) {
        try (Connection con = sql2o.open()) {
            T data = con.createQuery(query).withParams(param).executeAndFetchFirst(mapper);
            return data;
        }
//        catch (Sql2oException sql2oException) {
//            sql2oException.printStackTrace();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
    }
}
