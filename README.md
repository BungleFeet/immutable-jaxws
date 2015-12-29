immutable-jaxws
===============

### An experiment into using immutable Java Beans with JAXB and JAX-WS SOAP web services.

Class `ImmutablePerson` is an immutable bean - it is final, has final fields, and no setters.
It has a private, no-arg constructor that initializes the fields to `null`, or some default
value in the case of primitives. This constructor is required for JAXB marshalling (removing the
no-arg constructor causes the test to fail).

This experiment includes a Spock test that confirms we can create a SOAP web service (JAX-WS)
that produces and consumes immutable beans.

To run the test, run `gradlew test`