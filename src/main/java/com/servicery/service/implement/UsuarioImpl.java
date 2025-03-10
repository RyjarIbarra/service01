package com.servicery.service.implement;

import com.servicery.service.connection.ConexionBD;
import com.servicery.service.model.Usuario;
import com.servicery.service.modelDto.Login.UserData;
import com.servicery.service.modelDto.Login.UserResponse;
import com.servicery.service.modelDto.Response.DefaultResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioImpl {

    private final ConexionBD coneDB = new ConexionBD();

    public DefaultResponse<Usuario> Listar(String Filtro) {
        List<Usuario> lista = new ArrayList<>();
        DefaultResponse<Usuario> defaultResponse = new DefaultResponse<>();

        String Query = """
                SELECT * FROM public.usuario order by id
                """;

        try (
                Connection conn = coneDB.cone();
                PreparedStatement ps = conn.prepareStatement(Query)
        ){
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Usuario(rs.getInt("id"), rs.getString("usuario"), rs.getString("nickname"), rs.getString("telefono"), rs.getString("nivel"), "sin dato"));
                }
            } catch (SQLException e) {
                defaultResponse.setStatus(500);
                defaultResponse.setMessage(e.getMessage());
                defaultResponse.setOk(true);
                System.out.println("Error UsuarioImpl (ResultSet) listar ClienteImpl: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error UsuarioImpl (PS) listar: " + e.getMessage());
            defaultResponse.setStatus(500);
            defaultResponse.setMessage(e.getMessage());
            defaultResponse.setOk(true);
        }
        defaultResponse.setObjectsList(lista);
        return defaultResponse;
    }

    public DefaultResponse<Usuario> insert(Usuario usu) {
        DefaultResponse<Usuario> defaultResponse = new DefaultResponse<>();
        String Query = "INSERT INTO public.usuario(usuario, nickname, telefono, password, nivel) VALUES (?, ?, ?, ?, ?);";
        try (
                Connection conn = coneDB.cone();
                PreparedStatement ps = conn.prepareStatement(Query)
        ){
            ps.setString(1, usu.getUsuario().trim());
            ps.setString(2, usu.getNickname().trim());
            ps.setString(3, usu.getTelefono().trim());
            ps.setString(4, Base64Impl.encode(usu.getPassword().trim()));
            ps.setString(5, usu.getNivel().trim());
            ps.execute();
        } catch (SQLException e) {
            defaultResponse.setStatus(500);
            defaultResponse.setMessage(e.getMessage());
            defaultResponse.setOk(false);
            System.out.println(e.getMessage());
        }
        return defaultResponse;
    }

    public DefaultResponse<Usuario> update(Usuario usu) {
        DefaultResponse<Usuario> defaultResponse = new DefaultResponse<>();
        String Query = "Update public.usuario set usuario = ?, nickname = ?, telefono = ?, nivel = ? Where id = ?";
        try (
                Connection conn = coneDB.cone();
                PreparedStatement ps = conn.prepareStatement(Query)
        ){
            ps.setString(1, usu.getUsuario());
            ps.setString(2, usu.getNickname());
            ps.setString(3, usu.getTelefono());
            ps.setString(4, usu.getNivel());
            ps.setLong(5, usu.getId());
            ps.execute();
        } catch (SQLException e) {
            defaultResponse.setStatus(500);
            defaultResponse.setMessage(e.getMessage());
            defaultResponse.setOk(false);
            System.out.println(e.getMessage());
        }
        return defaultResponse;
    }

    public DefaultResponse<Usuario> delete(Usuario usu) {
        DefaultResponse<Usuario> defaultResponse = new DefaultResponse<>();
        ConexionBD coneDB = new ConexionBD();
        String Query = "DELETE FROM public.usuario Where id = ?";
        String respuesta;
        try (
                Connection conn = coneDB.cone();
                PreparedStatement ps = conn.prepareStatement(Query)
        ){
          ps.setLong(1, usu.getId());
          ps.execute();
        } catch (SQLException e) {
            defaultResponse.setStatus(500);
            defaultResponse.setMessage(e.getMessage());
            defaultResponse.setOk(false);
            System.out.println(e.getMessage());
        }
        return defaultResponse;
    }

    public UserResponse sign_in(UserData data) {
        UserResponse dato = new UserResponse();
        ConexionBD coneDB = new ConexionBD();
        String Query = "SELECT * FROM public.usuario Where nickname = ? and password = ?";
        try (
                Connection conn = coneDB.cone();
                PreparedStatement ps = conn.prepareStatement(Query)
        ){
            String encript = Base64Impl.encode(data.getPassword().trim());
            ps.setString(1, data.getNick().trim());
            ps.setString(2, encript);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    dato.setItoken(rs.getString("usuario"));
                    dato.setStatus(true);
                } else {
                    dato.setItoken("Usuario o Contraseña incorrecto");
                    dato.setStatus(false);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dato;
    }

}
