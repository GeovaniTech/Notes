package br.com.notes.api.notes.wsrest;

import java.io.Serializable;
import java.util.List;

import br.com.notes.api.notes.model.TONoteRestModel;
import br.com.notes.api.notes.service.KeepNoteSbean;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/notes")
public class WSNotes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	KeepNoteSbean noteSBean;
	
	@POST
	@Path("/list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TONoteRestModel> list() {
		return this.noteSBean.list();
	}
	
	@PUT
	@Path("/add")
	public Response add(List<TONoteRestModel> notes) {
		try {
			this.noteSBean.add(notes);
			return Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@DELETE
	@Path("/remove")
	public Response remove(TONoteRestModel note) {
		try {
			this.noteSBean.remove(note);
			return Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
}
