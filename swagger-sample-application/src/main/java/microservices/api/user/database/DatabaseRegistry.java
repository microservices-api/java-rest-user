package microservices.api.user.database;

import microservices.api.user.database.impl.StorageInMemory;

public class DatabaseRegistry {

	//TODO: Change this to your actual storage service, instead of just using the in-memory default service
	static private Storage defaultStorage = new StorageInMemory();
	
	public static Storage getStorageService() {
		return defaultStorage;
	}
}
