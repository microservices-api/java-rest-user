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
package microservices.api.user.database.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import microservices.api.user.database.Storage;
import microservices.api.user.jaxrs.model.Profile;

/**
 * Default service that stores the objects in memory.  This is just for dev / illustrative purposes. 
 */
public class StorageInMemory implements Storage {

	private Map<String, Profile> profiles = new ConcurrentHashMap<String, Profile>();
	private volatile int currentId = 0;

	public Set<Profile> getAllEntries() {
		return new HashSet<Profile>(profiles.values());
	}

	public Profile getEntry(String id) {
		return profiles.get(id);
	}

	public String createEntry(Profile value) {
		String id = null;
		if (profiles.get(value.getId()) == null) {
			id = value.getId();
		} else {
			id = Integer.toString(currentId++);
		}
		profiles.put(id, value);
		return id;

	}

	public void updateEntry(String id, Profile value) {
		profiles.put(id, value);
	}

	public void deleteEntry(String id) {
		profiles.remove(id);
	}



}
