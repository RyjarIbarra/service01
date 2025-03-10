package com.servicery.service.implement;

import com.servicery.service.connection.ConexionBD;
import com.servicery.service.model.Cliente;
import com.servicery.service.model.Producto;
import com.servicery.service.model.Proveedor;
import com.servicery.service.modelDto.Filter.DefaultFilter;
import com.servicery.service.modelDto.Response.DefaultResponse;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoImpl {

    public DefaultResponse<Producto> Listar(DefaultFilter Filtro){
        DefaultResponse<Producto> defaultResponse = new DefaultResponse<>();
        List<Producto> productoList = new ArrayList<>();
        ConexionBD coneDB = new ConexionBD();
        StringBuilder Query = new StringBuilder();
        Query.append("SELECT * FROM public.producto");

        if(!Filtro.getFilter().isEmpty()){
            Query.append(" where (descripcion ilike '%");
            Query.append(Filtro.getFilter());
            Query.append("%'");
            Query.append(" or codigo ilike '%");
            Query.append(Filtro.getFilter());
            Query.append("%')");
        }

        Query.append(" order by descripcion");

        try(
                Connection conn = coneDB.cone();
                PreparedStatement ps = conn.prepareStatement(Query.toString());
        ){

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    productoList.add(new Producto(rs.getString("codigo"), rs.getString("descripcion"), rs.getLong("precio"), rs.getLong("stock")));
                }
            }catch (SQLException e){
                defaultResponse.setStatus(500);
                defaultResponse.setMessage("Error (ResultSet) ProductoService - Listar: " + e.getMessage());
                defaultResponse.setOk(false);
            }

        } catch (SQLException e) {
            defaultResponse.setStatus(500);
            defaultResponse.setMessage("Error ProductoService - Listar: " + e.getMessage());
            defaultResponse.setOk(false);
        }
        defaultResponse.setObjectsList(productoList);
        return defaultResponse;
    }

    public DefaultResponse<Producto> insert(Producto prod) {
        DefaultResponse<Producto> defaultResponse = new DefaultResponse<>();
        ConexionBD coneDB = new ConexionBD();
        String Query = "INSERT INTO public.producto(codigo, descripcion, precio, stock) VALUES (?, ?, ?, ?);";

        try(
            Connection conn = coneDB.cone();
            PreparedStatement ps = conn.prepareStatement(Query)
        ){

            ps.setString(1, prod.getCodigo());
            ps.setString(2, prod.getDescripcion());
            ps.setLong(3, prod.getPrecio());
            ps.setLong(4, prod.getStock());
            ps.execute();

        }catch (SQLException e){
            defaultResponse.setStatus(500);
            defaultResponse.setMessage("Error ProductoService - Grabar: " + e.getMessage());
            defaultResponse.setOk(false);
        }

        return defaultResponse;
    }

}
