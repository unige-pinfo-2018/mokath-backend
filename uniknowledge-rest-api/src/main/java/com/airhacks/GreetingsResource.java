package com.airhacks;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("greetings")
public class GreetingsResource {
	
	@GET
	@Path("/greet")
	public JsonObject greet() {
		return Json.createObjectBuilder().add("hello", "world").build();
	}

	@GET
	@Path("/bye")
	public JsonObject bye() {
		return Json.createObjectBuilder().add("bye", "world").build();
	}

}
