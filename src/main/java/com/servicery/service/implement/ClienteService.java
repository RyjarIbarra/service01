package com.servicery.service.implement;

import com.servicery.service.connection.ConexionBD;
import com.servicery.service.model.Cliente;
import com.servicery.service.modelDto.Filter.DefaultFilter;
import com.servicery.service.modelDto.Response.DefaultResponse;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class ClienteService {

    public DefaultResponse<Cliente> Listar(DefaultFilter Filtro) {

        ArrayList<Cliente> list = new ArrayList<>();
        DefaultResponse<Cliente> defaultResponse = new DefaultResponse<>();

        ConexionBD coneDB = new ConexionBD();
        StringBuilder Query = new StringBuilder();

        Query.append("SELECT * FROM public.cliente");

        if(!Filtro.getFilter().isEmpty()){
            Query.append(" where (clinom ilike '%");
            Query.append(Filtro.getFilter());
            Query.append("%'");
            Query.append(" or cliruc ilike '%");
            Query.append(Filtro.getFilter());
            Query.append("%')");
        }

        Query.append(" order by clinom");

        try (
                Connection conn = coneDB.cone();
                PreparedStatement ps = conn.prepareStatement(Query.toString())
        ){

            try(
                    ResultSet rs = ps.executeQuery()
            ){
                while (rs.next()){
                    list.add(new Cliente(
                            rs.getLong("cliid"),
                            rs.getString("clinom"), rs.getString("cliruc"), rs.getString("clitel"), rs.getString("clitel"), rs.getString("clitip"), rs.getString("climail"), rs.getString("clicontr")
                    ));
                }
            }catch (SQLException e){
                defaultResponse.setStatus(500);
                defaultResponse.setMessage("Error (ResultSet) ClienteImpl - Listar: " + e.getMessage());
                defaultResponse.setOk(false);
            }

        }catch (SQLException e){
            defaultResponse.setStatus(500);
            defaultResponse.setMessage("Error ClienteImpl - Listar: " + e.getMessage());
            defaultResponse.setOk(false);
        }
        defaultResponse.setObjectsList(list);
        return defaultResponse;

    }

    public DefaultResponse<Cliente> insert(Cliente cli) {

        ConexionBD coneDB = new ConexionBD();
        DefaultResponse<Cliente> defaultResponse = new DefaultResponse<>();
        String Query = "INSERT INTO public.cliente(clinom, cliruc, clitel, clidir, clitip, climail, clicontr) VALUES (?, ?, ?, ?, ?, ?, ?);";

        try(
                Connection conn = coneDB.cone();
                PreparedStatement ps = conn.prepareStatement(Query)
        ){

            ps.setString(1, cli.getClinom());
            ps.setString(2, cli.getCliruc());
            ps.setString(3, cli.getClitel());
            ps.setString(4, cli.getClidir());
            ps.setString(5, cli.getClitip());
            ps.setString(6, cli.getClimail());
            ps.setString(7, cli.getClicontr());
            ps.execute();

        }catch (SQLException e){
            defaultResponse.setStatus(500);
            defaultResponse.setMessage("Error ClienteImpl - Grabar: " + e.getMessage());
            defaultResponse.setOk(false);
        }

        return defaultResponse;
    }

    public DefaultResponse<Cliente> update(Cliente cli) {
        ConexionBD coneDB = new ConexionBD();
        DefaultResponse<Cliente> defaultResponse = new DefaultResponse<>();
        String respuesta = "Registro Grabado";
        String Query = """
                UPDATE public.cliente
                	SET clinom = ?, cliruc = ?, clitel = ?, clidir = ?, clitip = ?, climail = ?, clicontr = ?
                	WHERE cliid = ?;
                """;

        try(
                Connection conn = coneDB.cone();
                PreparedStatement ps = conn.prepareStatement(Query)
        ){
            ps.setString(1, cli.getClinom());
            ps.setString(2, cli.getCliruc());
            ps.setString(3, cli.getClitel());
            ps.setString(4, cli.getClidir());
            ps.setString(5, cli.getClitip());
            ps.setString(6, cli.getClimail());
            ps.setString(7, cli.getClicontr());
            ps.setLong(8, cli.getId());
            ps.execute();
        } catch (SQLException e) {
            defaultResponse.setStatus(500);
            defaultResponse.setMessage("Error ClienteImpl - Update: " + e.getMessage());
            defaultResponse.setOk(false);
        }
        return defaultResponse;
    }

    public DefaultResponse<Cliente> delete(Cliente cli){
        ConexionBD coneDB = new ConexionBD();
        DefaultResponse<Cliente> defaultResponse = new DefaultResponse<>();
        String Query = """
                DELETE FROM public.cliente
                	WHERE cliid = ?;
                """;
        try(
                Connection conn = coneDB.cone();
                PreparedStatement ps = conn.prepareStatement(Query)
        ){
            ps.setLong(1, cli.getId());
            ps.execute();
        } catch (SQLException e) {
            defaultResponse.setStatus(500);
            defaultResponse.setMessage("Error ClienteImpl - Delete: " + e.getMessage());
            defaultResponse.setOk(false);
        }
        return defaultResponse;
    }

    public int CantidadCliente() {
        ConexionBD coneDB = new ConexionBD();
        int cantidad;

        String Query = "Select Count(*) cant From Cliente";

        try(
                Connection conn = coneDB.cone();
                PreparedStatement ps = conn.prepareStatement(Query)
        ){

            try(
                ResultSet rs = ps.executeQuery()
            ){

                rs.next();
                cantidad = rs.getInt("cant");

            }catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cantidad;
    }

}
