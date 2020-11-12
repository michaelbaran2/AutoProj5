package base;

import exceptions.TestMapperInitializedMoreThanOnceException;
import exceptions.TestNotFoundException;


import java.io.File;
import java.util.*;

public class TestMapper {
    private static boolean initialized = false;
    private final HashMap<String, Class<?>> testClasses;

    private TestMapper(Set<Class<?>> testClasses) {
        this.testClasses = new HashMap<>();
        for (Class<?> testClass: testClasses) {
            this.testClasses.put(createKey(testClass), testClass);
        }
    }

    public static TestMapper initTestMapper() throws ClassNotFoundException, TestMapperInitializedMoreThanOnceException {
        if (!initialized) {
            initialized = true;
            Set<Class<?>> testClasses = getClassesInPackage("tests");
            return new TestMapper(testClasses);
        }
        throw new TestMapperInitializedMoreThanOnceException("A TestMapper object was already initialized!");
    }

    public void validateTests(String[] tests) throws TestNotFoundException {
        for (String test: tests) {
            if (!testClasses.containsKey(test.toLowerCase())) {
                throw new TestNotFoundException("Test not found identified by key '" + test + "'");
            }
        }
    }

    public Class<?> getTest(String key) {
        return testClasses.get(key.toLowerCase());
    }

    private static String createKey(Class<?> testClass) {
        String name = testClass.getName();
        return name.substring(name.indexOf('.') + 1).toLowerCase();
    }

    private static Set<Class<?>> getClassesInPackage(String packageName) throws ClassNotFoundException {
        String path = packageName.replace(".", File.separator);
        Set<Class<?>> classes = new HashSet<>();
        String[] classPathEntries = System.getProperty("java.class.path").split(
                System.getProperty("path.separator")
        );
        String name;
        for (String classpathEntry : classPathEntries) {
            if (!classpathEntry.endsWith(".jar")) {
                File base = new File(classpathEntry + File.separatorChar + path);
                File[] files;
                try {
                    files = Objects.requireNonNull(base.listFiles());
                }
                catch (NullPointerException e) {
                    continue;
                }
                for (File file : files) {
                    name = file.getName();
                    if (name.endsWith(".class")) {
                        name = name.substring(0, name.length() - 6);
                        classes.add(Class.forName(packageName + "." + name));
                    }
                    else {
                        Set<Class<?>> subFolder = getClassesInPackage(packageName + "." + name);
                        classes.addAll(subFolder);
                    }
                }
            }
        }
        return classes;
    }


}
