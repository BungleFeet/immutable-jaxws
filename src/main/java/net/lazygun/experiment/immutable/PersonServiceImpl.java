package net.lazygun.experiment.immutable;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@WebService(endpointInterface = "net.lazygun.experiment.immutable.PersonService", serviceName = "PersonService")
public class PersonServiceImpl implements PersonService {

    private final ConcurrentHashMap<String, ImmutablePerson> directory = new ConcurrentHashMap<>();

    public PersonServiceImpl(ImmutablePerson... initialDirectoryContents) {
        for (final ImmutablePerson person : initialDirectoryContents) {
            directory.put(person.getName(), person);
        }
    }

    @Override
    //@WebMethod
    public ArrayList<ImmutablePerson> findAll() {
        return new ArrayList<>(directory.values());
    }

    @Override
    //@WebMethod
    public ImmutablePerson findByName(final String name) {
        final ImmutablePerson person = directory.get(name);
        if (person == null) throw new NoSuchElementException("No person named '" + name + "'");
        return person;
    }

    @Override
    //@WebMethod
    public void create(final ImmutablePerson person) {
        final String name = person.getName();
        final ImmutablePerson existing = directory.putIfAbsent(name, person);
        if (existing != null) throw new IllegalStateException("Person name '" + name + "' already exists");
    }
}
