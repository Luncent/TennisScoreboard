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
        return (Session) Proxy.newProxyInstance(
                Session.class.getClassLoader(),
                new Class[]{Session.class},
                (proxy, method, args)-> method.invoke(factory.getCurrentSession(),args));
    }
}
