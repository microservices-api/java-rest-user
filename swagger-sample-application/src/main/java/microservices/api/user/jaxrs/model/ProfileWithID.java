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

package microservices.api.user.jaxrs.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(parent = Profile.class)
public class ProfileWithID extends Profile {
	@ApiModelProperty(readOnly = true, required = true)
	private int id;

	public ProfileWithID(Profile profile, int id) {
		super(profile);
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}
