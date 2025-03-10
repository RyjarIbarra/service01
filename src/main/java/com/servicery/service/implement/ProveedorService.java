package com.servicery.service.implement;

import com.servicery.service.connection.ConexionBD;
import com.servicery.service.model.Cliente;
import com.servicery.service.model.Proveedor;
import com.servicery.service.modelDto.Response.DefaultResponse;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class ProveedorService {

    public DefaultResponse<Proveedor> Listar(String Filtro) {
        DefaultResponse<Proveedor> defaultResponse = new DefaultResponse<>();
        ArrayList<Proveedor> list = new ArrayList<>();
        ConexionBD coneDB = new ConexionBD();
        StringBuilder Query = new StringBuilder();

        Query.append("SELECT * FROM public.proveedor ");

        if(!Filtro.isEmpty()){
            Query.append(" where prvnom ilike '%");
            Query.append(Filtro);
            Query.append("%'");
        }

        Query.append(" order by prvnom");

        try(
                Connection conn = coneDB.cone();
                PreparedStatement ps = conn.prepareStatement(Query.toString())
        ){
            try(
                    ResultSet rs = ps.executeQuery()
            ){
                while (rs.next()){
                    list.add(new Proveedor(rs.getLong("prvid"), rs.getString("prvruc"), rs.getString("prvnom"),
                            rs.getString("prvtel"), rs.getString("prvmail"), rs.getString("prvdir"),
                            rs.getString("prvtip"), rs.getString("prvcontr")));
                }
            }catch (SQLException e){
                defaultResponse.setStatus(500);
                defaultResponse.setMessage("Error (ResultSet) ProveedorService - Listar: " + e.getMessage());
                defaultResponse.setOk(false);
            }

        } catch (SQLException e) {
            defaultResponse.setStatus(500);
            defaultResponse.setMessage("Error ProveedorService - Listar: " + e.getMessage());
            defaultResponse.setOk(false);
        }
        defaultResponse.setObjectsList(list);
        return defaultResponse;
    }

    public DefaultResponse<Proveedor> insert(Proveedor prov) {
        ConexionBD coneDB = new ConexionBD();
        DefaultResponse<Proveedor> defaultResponse = new DefaultResponse<>();
        String Query = """
                INSERT INTO public.proveedor(
                	prvruc, prvnom, prvtel, prvdir, prvmail, prvtip, prvcontr)
                	VALUES (?, ?, ?, ?, ?, ?, ?);
                """;

        try(
                Connection conn = coneDB.cone();
                PreparedStatement ps = conn.prepareStatement(Query)
        ){

            ps.setString(1, prov.getPrvruc());
            ps.setString(2, prov.getPrvnom());
            ps.setString(3, prov.getPrvtel());
            ps.setString(4, prov.getPrvdir());
            ps.setString(5, prov.getPrvmail());
            ps.setString(6, prov.getPrvtip());
            ps.setString(7, prov.getPrvcontr());
            ps.execute();

        }catch (SQLException e){
            defaultResponse.setStatus(500);
            defaultResponse.setMessage("Error ProveedorService - Grabar: " + e.getMessage());
            defaultResponse.setOk(false);
        }

        return defaultResponse;
    }

    public DefaultResponse<Proveedor> update(Proveedor prov){
        ConexionBD coneDB = new ConexionBD();
        DefaultResponse<Proveedor> defaultResponse = new DefaultResponse<>();
        String Query = """
                UPDATE public.proveedor
                	SET prvruc=?, prvnom=?, prvtel=?, prvmail=?, prvdir=?, prvtip=?, prvcontr=?
                	WHERE prvid=?;
                """;
        try(
                Connection conn = coneDB.cone();
                PreparedStatement ps = conn.prepareStatement(Query)
        ){

            ps.setString(1, prov.getPrvruc());
            ps.setString(2, prov.getPrvnom());
            ps.setString(3, prov.getPrvtel());
            ps.setString(4, prov.getPrvmail());
            ps.setString(5, prov.getPrvdir());
            ps.setString(6, prov.getPrvtip());
            ps.setString(7, prov.getPrvcontr());
            ps.setLong(8, prov.getPrvid());
            ps.execute();

        }catch (SQLException e){
            defaultResponse.setStatus(500);
            defaultResponse.setMessage("Error ProveedorService - Update: " + e.getMessage());
            defaultResponse.setOk(false);
        }

        return defaultResponse;
    }

    public DefaultResponse<Proveedor> delete (Proveedor prov){
        ConexionBD coneDB = new ConexionBD();
        DefaultResponse<Proveedor> defaultResponse = new DefaultResponse<>();
        String Query = """
                DELETE FROM public.proveedor WHERE prvid=?;
                """;
        try(
                Connection conn = coneDB.cone();
                PreparedStatement ps = conn.prepareStatement(Query)
        ){

            ps.setLong(1, prov.getPrvid());
            ps.execute();

        }catch (SQLException e){
            defaultResponse.setStatus(500);
            defaultResponse.setMessage("Error ProveedorService - Update: " + e.getMessage());
            defaultResponse.setOk(false);
        }

        return defaultResponse;
    }

}
