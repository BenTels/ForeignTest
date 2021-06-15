This is a small test project to exercise the Foreign Memory and Linker API which is part of the incubator module in JDK 16.

Note that this project is required to be a module in order to declare dependence on the jdk.incubator.foreign module.

It also requires a milestone version of surefire because the official release seems to have problems with modules in Java 16.

One of the tests is the standard example of the documentation and runs against libc. The other tests require the GNU Scientific library, libgls (and libglscblas).

If using an IDE to run the tests, the foreign.restricted property must be set to permit externally, before the tests are run. Doing it inside the test is impossible as that is too late.
