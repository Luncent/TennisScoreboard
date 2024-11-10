package org.example.Utils;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

public class SessionSupplier {
    private final SessionFactory factory;
    @Getter
    private final Session proxySession;

    public SessionSupplier(SessionFactory factory){
        this.factory = factory;
        this.proxySession = createProxySession();
    }

    private Session createProxySession(){
        // why just not to return factory.getCurrentSession()? proxy seem to have no benefits here

        // I'm not sure you work with hibernate session correctly. 
        // Seem like you re-use the same session per app run, which is is not recommended/
        // May be read a bit more?
        // https://stackoverflow.com/questions/26150843/is-it-a-good-practice-to-use-the-same-hibernate-session-over-and-over
        // https://www.tutorialspoint.com/hibernate/hibernate_sessions.htm

        return (Session) Proxy.newProxyInstance(
                Session.class.getClassLoader(),
                new Class[]{Session.class},
                (proxy, method, args)-> method.invoke(factory.getCurrentSession(),args)); 
    }
}
