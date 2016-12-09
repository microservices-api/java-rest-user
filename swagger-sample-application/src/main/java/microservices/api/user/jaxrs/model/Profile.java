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

import io.swagger.annotations.ApiModelProperty;

public class Profile {

	@ApiModelProperty(required = true, example = "Arthur")
	private String firstName;

	@ApiModelProperty(required = true, example = "De Magalhaes")
	private String lastName;

	@ApiModelProperty(required = true, example = "01/15/16")
	private String dateOfBirth;
	
	@ApiModelProperty(required = true, example = "9051234567")
	private String phoneNumber;
	
	@ApiModelProperty(required = false, example = "No calls after 10pm EST")
	private String otherNotes;

	protected Profile() {
		
	}
	
	protected Profile(Profile profile) {
		this.firstName = profile.firstName;
		this.lastName = profile.lastName;
		this.dateOfBirth = profile.dateOfBirth;
		this.phoneNumber = profile.phoneNumber;
		this.otherNotes = profile.otherNotes;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOtherNotes() {
		return otherNotes;
	}

	public void setOtherNotes(String otherNotes) {
		this.otherNotes = otherNotes;
	}


}
