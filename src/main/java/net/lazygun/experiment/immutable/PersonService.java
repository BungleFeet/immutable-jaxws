package net.lazygun.experiment.immutable;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;

@WebService
public interface PersonService {

    @WebMethod
    ArrayList<ImmutablePerson> findAll();

    @WebMethod
    ImmutablePerson findByName(String name);

    @WebMethod
    void create(ImmutablePerson person);
}
