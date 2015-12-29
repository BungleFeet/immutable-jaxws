package net.lazygun.experiment.immutables

import net.lazygun.experiment.immutable.ImmutablePerson
import net.lazygun.experiment.immutable.PersonService
import net.lazygun.experiment.immutable.PersonServiceImpl
import spock.lang.Specification

import javax.xml.namespace.QName
import javax.xml.ws.Endpoint
import javax.xml.ws.Service


class ImmutablePersonWebServiceSpec extends Specification {

    def "A webservice can be created that produces/consumes immutable beans"() {
        setup: 'two immutable bean objects'
        def arthur = new ImmutablePerson('Arthur', 20)
        def betty = new ImmutablePerson('Betty', 30)

        and: 'a SOAP endpoint initialized with one of the ImmutablePerson beans'
        Endpoint.publish("http://localhost:9999/ws/person", new PersonServiceImpl(arthur))

        and: 'a client'
        def url = new URL("http://localhost:9999/ws/person?wsdl")
        def qName = new QName("http://immutable.experiment.lazygun.net/", "PersonService")
        def webService = Service.create(url, qName)
        def personService = webService.getPort(PersonService.class)

        expect: 'the service can produce an immutable bean'
        personService.findByName('Arthur') == arthur

        and: 'the service can consume an immutable bean'
        personService.create(betty);
        personService.findByName('Betty') == betty
    }
}