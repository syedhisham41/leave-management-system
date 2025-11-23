package dao.factory;

import dao.factory.impl.DAO_factory_sqlite_impl;
import exceptions.exception.DbNotFoundException;

public class DAO_factory_selector {
	
	public static DAO_factory dao_factory_selector(String db_type) throws DbNotFoundException {

		switch(db_type.toLowerCase()) {
		case "sqlite":
			return new DAO_factory_sqlite_impl();
			
			default :
			throw new DbNotFoundException("Unsupported db type "+db_type, null);
		}
	}
}
