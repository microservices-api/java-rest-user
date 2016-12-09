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
	private Map<Integer, Profile> profiles = new ConcurrentHashMap<Integer, Profile>();
	private volatile int currentId = 0;

	@GET
	@ApiOperation(value = "Retrieve all profiles", responseContainer = "array", response = ProfileWithID.class)
	@Produces("application/json")
	public Response getProfiles() {
		List<ProfileWithID> result = new ArrayList<ProfileWithID>(profiles.size());
		for (Entry<Integer, Profile> profileEntry : profiles.entrySet()) {
			result.add(new ProfileWithID(profileEntry.getValue(), profileEntry.getKey()));
		}
		return Response.ok().entity(result).build();
	}

	@POST
	@ApiOperation("Create a profile")
	@Consumes("application/json")
	@Produces("application/json")
	@ApiResponses({ @ApiResponse(code = 201, message = "Profile created", response = String.class) })
	public Response createProfile(@ApiParam(required = true) Profile profile) {
		profiles.put(currentId, profile);
		return Response.status(Status.CREATED).entity("{\"id\":" + currentId++ + "}").build();
	}

	@GET
	@Path("{id}")
	@ApiOperation(value = "Get a profile")
	@Produces("application/json")
	@ApiResponses({ @ApiResponse(code = 200, message = "Profile retrieved", response = Profile.class),
			@ApiResponse(code = 404, message = "Profile not found") })
	public Response getProfile(@PathParam("id") int id) {
		Profile profile = profiles.get(id);
		if (profile != null) {
			return Response.ok().entity(profile).build();
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
			@ApiResponse(code = 404, message = "Profile not found") })
	public Response updateProfile(@PathParam("id") int id, @ApiParam(required = true) Profile profile) {
		if (profiles.get(id) != null) {
			profiles.put(id, profile);
			return Response.ok().build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@DELETE
	@Path("{id}")
	@ApiOperation(value = "Delete a profile")
	@ApiResponses({ @ApiResponse(code = 200, message = "Profile deleted"),
			@ApiResponse(code = 404, message = "Profile not found") })
	@Produces("text/plain")
	public Response deleteProfile(@PathParam("id") int id) {
		if (profiles.get(id) != null) {
			profiles.remove(id);
			return Response.ok().build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
