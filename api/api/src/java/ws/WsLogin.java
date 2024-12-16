package ws;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import pojo.LoginColaborador;
import dominio.ImpLogin;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("login")
public class WsLogin {
    @Context
    private UriInfo context;
    
    public WsLogin() {}
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public LoginColaborador loginColaborador(@FormParam("numeroPersonal") String noPersona, @FormParam("contrasena") String contrasena) {
        // Validate inputs
        if (noPersona == null || noPersona.isEmpty() || contrasena == null || contrasena.isEmpty() || noPersona.length() > 10) {
            throw new BadRequestException("Invalid input: ensure numeroPersonal is not empty, has a maximum length of 10, and contrasena is not empty.");
        } 
        
        // Attempt to login
        return ImpLogin.login(noPersona, contrasena); 
    }   
}
