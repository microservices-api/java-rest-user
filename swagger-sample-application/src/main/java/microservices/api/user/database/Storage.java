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
package microservices.api.user.database;

import java.util.Set;

import microservices.api.user.jaxrs.model.Profile;

/**
 * Simple {key,value} storage for Profiles
 */
public interface Storage {

	Set<Profile> getAllEntries();
	
	Profile getEntry(String id);
	
	String createEntry(Profile value);
	
	void updateEntry(String id, Profile value);
	
	void deleteEntry(String id);

}
