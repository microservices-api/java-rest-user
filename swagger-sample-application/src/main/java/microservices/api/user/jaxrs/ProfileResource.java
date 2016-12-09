/**
* (C) Copyright IBM Corporation 2016.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package microservices.api.user.jaxrs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import microservices.api.user.jaxrs.model.Profile;
import microservices.api.user.jaxrs.model.ProfileWithID;

@Path("")
@Api(value = "User Profile", authorizations = { @Authorization(value="basicAuth") })
public class ProfileResource {
	private Map<Integer, Profile> tasks = new ConcurrentHashMap<Integer, Profile>();
	private volatile int currentId = 0;

	@GET
	@ApiOperation(value = "Retrieve all profiles", responseContainer = "array", response = ProfileWithID.class)
	@Produces("application/json")
	public Response getTasks() {
		List<ProfileWithID> result = new ArrayList<ProfileWithID>(tasks.size());
		for (Entry<Integer, Profile> taskEntry : tasks.entrySet()) {
			result.add(new ProfileWithID(taskEntry.getValue(), taskEntry.getKey()));
		}
		return Response.ok().entity(result).build();
	}

	@POST
	@ApiOperation("Create a task")
	@Consumes("application/json")
	@Produces("application/json")
	@ApiResponses({ @ApiResponse(code = 201, message = "Task created", response = String.class) })
	public Response createTask(@ApiParam(required = true) Profile task) {
		tasks.put(currentId, task);
		return Response.status(Status.CREATED).entity("{\"id\":" + currentId++ + "}").build();
	}

	@GET
	@Path("{id}")
	@ApiOperation(value = "Get a profile")
	@Produces("application/json")
	@ApiResponses({ @ApiResponse(code = 200, message = "Task retrieved", response = Profile.class),
			@ApiResponse(code = 404, message = "Task not found") })
	public Response getTask(@PathParam("id") int id) {
		Profile task = tasks.get(id);
		if (task != null) {
			return Response.ok().entity(task).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Path("{id}")
	@ApiOperation(value = "Update a profile")
	@Consumes("application/json")
	@Produces("text/plain")
	@ApiResponses({ @ApiResponse(code = 200, message = "Profile updated"),
			@ApiResponse(code = 404, message = "Task not found") })
	public Response updateTask(@PathParam("id") int id, @ApiParam(required = true) Profile task) {
		if (tasks.get(id) != null) {
			tasks.put(id, task);
			return Response.ok().build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@DELETE
	@Path("{id}")
	@ApiOperation(value = "Delete a profile")
	@ApiResponses({ @ApiResponse(code = 200, message = "Profile deleted"),
			@ApiResponse(code = 404, message = "Task not found") })
	@Produces("text/plain")
	public Response deleteTask(@PathParam("id") int id) {
		if (tasks.get(id) != null) {
			tasks.remove(id);
			return Response.ok().build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
