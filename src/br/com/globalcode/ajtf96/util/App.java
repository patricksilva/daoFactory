package br.com.globalcode.ajtf96.util;



/**
 * 
 * @author Dima = http://stackoverflow.com/questions/27723477/design-patterns-for-data-access-layer
 * 
 * So, what most DAOs are in java nowadays are essentially what you mentioned 
 * in the beginning - classes, full of static methods.
 * One difference is that, instead of making all the methods static,
 * it is better to have a single static "factory method"
 * (probably, in a different class), that returns a (singleton) instance of your DAO,
 * which implements a particular interface,
 * used by application code to access the database:
 * 
 * In real life, this is often done without any changes to the code at all:
 * the factory method gets the implementation class name via a property setting,
 * and instantiates it using reflection, so, all you need to do to switch
 * implementations is edit a property file.
 * There are actually frameworks - like spring or guice - that manage this
 * "dependency injection" mechanism for you, but I won't go into details,
 * first, because it is really beyond the scope of your question, and also,
 * because I am not necessarily convinced that the benefit you get from using
 * those frameworks is worth the trouble integrating with them for most applications.
 * 
 * Another (probably, more likely to be taken advantage of) benefit of this
 * "factory approach" as opposed to static is testability. Imagine, that you are
 * writing a unit test, that should test the logic of your App class independently
 * of any underlying DAO. You don't want it to use any real underlying storage for
 * several reasons (speed, having to set it up, and clean up afterwords, possible
 * collisions with other tests, possibility of polluting test results with problems
 * in DAO, unrelated to App, which is actually being tested, etc.).
 * 
 * To do this, you want a test framework, like Mockito, that allows you to "mock out"
 * the functionality of any object or method, replacing it with a "dummy" object,
 * with predefined behavior (I'll skip the details, because, this again is beyond the scope).
 * So, you can create this dummy object replacing your DAO, and make the GreatDAOFactory
 * return your dummy instead of the real thing by calling GreatDAOFactory.setDAO(dao)
 * before the test (and restoring it after). If your were using static methods instead of
 * the instance class, this would not be possible.
 * 
 * One more benefit, which is kinda similar to switching databases I described above is
 * "pimping up" your dao with additional functionality. Suppose that your application
 * becomes slower as the amount of data in the database grows, and you decide that you
 * need a cache layer. Implement a wrapper class, that uses the real dao instance
 * (provided to it as a constructor param) to access the database, and caches the objects
 * it reads in memory, so that they can be returned faster. You can then make your
 * GreatDAOFactory.getDAO instantiate this wrapper, for the application to take advantage of it.
 * 
 * (This is called "delegation pattern" ... and seems like pain in the butt, especially when
 * you have lots of methods defined in your DAO: you will have to implement all of them in
 * the wrapper, even to alter behavior of just one. Alternatively, you could simply subclass
 * your dao, and add caching to it this way. This would be a lot less boring coding upfront,
 * but may become problematic when you do decide to change the database, or, worse, to have
 * an option of switching implementations back and forth).
 * 
 * One equally broadly used (but, in my opinion, inferior) alternative to the "factory"
 * method is making the dao a member variable in all classes that need it:
 * 
 * public class App {
 *   GreatDao dao;
 *   public App(GreatDao d) { dao = d; }
 * }
 * 
 * This way, the code that instantiates these classes needs to instantiate the dao object
 * (could still use the factory), and provide it as a constructor parameter.
 * The dependency injection frameworks I mentioned above, usually do something similar to this.
 * 
 * This provides all the benefits of the "factory method" approach, that I decribed earlier, but,
 * like I said, is not as good in my opinion.
 * The disadvantages here are having to write a constructor for each of your app classes,
 * doing the same exact thing over, and over, and also not being able to instantiate the
 * classes easily when needed, and some lost readability: with a large enough code base,
 * a reader of your code, not familiar with it, will have hard time understanding which
 * actual implementation of the dao is used, how it is instantiated, whether it is a singleton,
 * a thread-safe implementation, whether it keeps state, or caches anything, how the decisions
 * on choosing a particular implementation are made etc.
 *
 */
public class App {
     void setUserName(int id, String name) {
          GreatDao dao =  GreatDAOFactory.getDao();
          User u = dao.getUser(id);
          u.setName(name);
          dao.saveUser(u);
     }
}