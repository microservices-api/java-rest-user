/**
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
import microservices.api.user.database.DatabaseRegistry;
import microservices.api.user.jaxrs.model.Profile;

@Path("")
@Api(value = "User Profile", authorizations = { @Authorization(value="basicAuth") })
public class ProfileResource {


	@GET
	@ApiOperation(value = "Retrieve all profiles", responseContainer = "array", response = Profile.class)
	@Produces("application/json")
	public Response getProfiles() {
		return Response.ok().entity(DatabaseRegistry.getStorageService().getAllEntries()).build();
	}

	@POST
	@ApiOperation("Create a profile")
	@Consumes("application/json")
	@Produces("application/json")
	@ApiResponses({ @ApiResponse(code = 201, message = "Profile created", response = String.class) })
	public Response createProfile(@ApiParam(required = true) Profile profile) {
		final String id = DatabaseRegistry.getStorageService().createEntry(profile);
		return Response.status(Status.CREATED).entity("{\"id\":" + id + "}").build();
	}

	@GET
	@Path("{id}")
	@ApiOperation(value = "Get a profile")
	@Produces("application/json")
	@ApiResponses({ @ApiResponse(code = 200, message = "Profile retrieved", response = Profile.class),
			@ApiResponse(code = 404, message = "Profile not found") })
	public Response getProfile(@PathParam("id") String id) {
		Profile profile = DatabaseRegistry.getStorageService().getEntry(id);
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
	public Response updateProfile(@PathParam("id") String id, @ApiParam(required = true) Profile profile) {
		Profile currentProfile = DatabaseRegistry.getStorageService().getEntry(id);
		if (currentProfile != null) {
			DatabaseRegistry.getStorageService().updateEntry(id, profile);
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
	public Response deleteProfile(@PathParam("id") String id) {
		Profile currentProfile = DatabaseRegistry.getStorageService().getEntry(id);
		if (currentProfile != null) {
			DatabaseRegistry.getStorageService().deleteEntry(id);
			return Response.ok().build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
