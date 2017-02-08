package br.com.globalcode.ajtf96.util;

import br.com.globalcode.ajtf96.util.GreatDao;

public class GreatDAOFactory {
	
    private static GreatDao dao = null;
    
    protected static synchronized GreatDao setDAO(GreatDao d) {
    	
        GreatDao old = dao;
        dao = d;
        return old;
        
    }
    
    
    public static synchronized GreatDao getDAO() {
    	
        return dao == null ? dao = new TheGreatestDAO() : dao;
        
    }
}
