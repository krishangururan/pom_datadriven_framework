package com.qa.util;

import java.io.File;
import java.io.FileFilter;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Philippe Schweitzer, dbi services Switzerland
 *
 * It is a Class that demonstrates how to list Classes and Packages from a given
 * root package name.
 *
 * This class uses a specific Classloader in order to lookup into jar files
 * located at a dedicated place (and not in all JavaSE library ).
 *
 */
public class RessourceFinderExample {

    /* Root package name */
    private static final String packageName = "com.qa.testcases";

    /* Paths list to jar files */
    private static final String[] classPaths = {"plugins", ".", "dist"};

    /**
     * Main method, it creates the Classloader and displays a consolidated
     * result.
     *
     * @param args no argument required
     */
    public static void main(String args[]) {

        URLClassLoader cl = new URLClassLoader(findJarURLsInClasspath(), Thread.currentThread().getContextClassLoader());

        System.out.println("");

        TreeSet<String> packages = RessourceFinderExample.getPackageFromPackage(packageName, cl);

        System.out.println("");
        System.out.println("-- CONSOLIDATED  RESULT --");
        System.out.println("");

        for (String packageString : packages) {
            System.out.println("   PACKAGE : " + packageString);
        }

        System.out.println("");
        System.out.println("--------------------------");
        System.out.println("");

        TreeMap<String, Class> classes = RessourceFinderExample.getClassesFromPackage(packageName, cl);

        System.out.println("");
        System.out.println("-- CONSOLIDATED  RESULT --");
        System.out.println("");

        for (Class c : classes.values()) {
            System.out.println("   CLASS   : " + packageName + "." + c.getCanonicalName());
        }

        System.out.println("");
        System.out.println("--------------------------");
        System.out.println("");
    }

    /**
     * Method that finds all jar files available in given dedicated classpath
     * places. It serves for an URLClassloader initialization.
     *
     * @return List of jar files URLs
     */
    private static URL[] findJarURLsInClasspath() {
        URL url;

        ArrayList<URL> jarURLs = new ArrayList();

        for (String path : classPaths) {

            File[] jars = new File(path).listFiles(new FileFilter() {
                public boolean accept(File pathname) {

                    return pathname.getName().toLowerCase().endsWith(".jar");
                }
            });

            if (jars != null) {
                for (int i = 0; i < jars.length; i++) {
                    try {
                        System.out.println("JAR Path: " + jars[i].getAbsolutePath());
                        url = jars[i].toURI().toURL();

                        jarURLs.add(url);

                    } catch (Exception e) {

                    }
                }
            }
        }

        URL[] urls = jarURLs.toArray(new URL[0]);
        return urls;
    }

    /**
     * Method that returns all jar files registered in the given URLClassloader
     * and which are present in dedicated classpath places.
     *
     * @return List of jar files URLs
     */
    private static URL[] getJarURLs(URLClassLoader cl) {
        URL[] result = cl.getURLs();
        ArrayList<URL> urls = new ArrayList();

        for (URL url : result) {

            try {
                Path jarPath = Paths.get(url.toURI());

                for (String classPathString : classPaths) {

                    Path classPath = Paths.get(classPathString).toAbsolutePath();

                    if (jarPath.startsWith(classPath)) {
                        urls.add(url);
                    }
                }

            } catch (URISyntaxException ex) {
            }
        }

        result = new URL[urls.size()];
        result = urls.toArray(result);

        return result;
    }

    /**
     * Method that returns all classes available underneath a given package
     * name.
     *
     * @return Set of Classes
     */
    private static TreeMap<String, Class> getClassesFromPackage(String pckgname, URLClassLoader cl) {
        TreeMap<String, Class> result = new TreeMap();
        ArrayList<File> files = new ArrayList();

        for (URL jarURL : getJarURLs(cl)) {
            getClassesInSamePackageFromJar(result, pckgname, jarURL.getPath(), cl);
        }

        return result;
    }

    /**
     * Method that fills TreeMap with all classes available in a particular jar
     * file, underneath a given package name.
     *
     */
    private static void getClassesInSamePackageFromJar(TreeMap<String, Class> result, String packageName, String jarPath, URLClassLoader cl) {
        JarFile jarFile = null;
        try {

            System.out.println("");
            System.out.println("** IN JAR : " + jarPath);
            jarFile = new JarFile(jarPath);

            Enumeration<JarEntry> en = jarFile.entries();
            while (en.hasMoreElements()) {
                JarEntry entry = en.nextElement();
                String entryName = entry.getName();

                packageName = packageName.replace('.', '/');

                if (entryName != null && entryName.endsWith(".class") && entryName.startsWith(packageName) && !entryName.substring(packageName.length() + 1).contains("/")) {

                    try {
                        Class<?> entryClass = cl.loadClass(entryName.substring(0, entryName.length() - 6).replace('/', '.'));
                        if (entryClass != null) {
                            result.put(entryClass.getCanonicalName(), entryClass);
                            System.out.println("   CLASS   : " + packageName + "." + entryClass.getCanonicalName());
                        }
                    } catch (Throwable e) {
//                        System.err.println("Error instanciating: " + entryName + " " + e.toString());
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                if (jarFile != null) {
                    jarFile.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * Method that returns all package names underneath a given root package
     * name.
     *
     * @return Set of Classes
     */
    private static TreeSet<String> getPackageFromPackage(String pckgname, URLClassLoader cl) {
        TreeSet<String> result = new TreeSet();

        for (URL jarURL : getJarURLs(cl)) {
            getPackageInPackageFromJar(result, pckgname, jarURL.getPath(), cl);
        }

        return result;
    }

    /**
     * Method that fills TreeMap with all package names in a particular jar
     * file, underneath a given root package name.
     *
     */
    private static void getPackageInPackageFromJar(TreeSet<String> result, String packageName, String jarPath, URLClassLoader cl) {
        JarFile jarFile = null;
        try {
            System.out.println("");
            System.out.println("** IN JAR : " + jarPath);

            jarFile = new JarFile(jarPath);

            Enumeration<JarEntry> en = jarFile.entries();
            while (en.hasMoreElements()) {
                JarEntry entry = en.nextElement();
                String entryName = entry.getName();

                packageName = packageName.replace('.', '/');

                if (entryName != null && entryName.endsWith("/") && entryName.startsWith(packageName + "/")) {

                    try {
                        String packageEntryName = entryName.substring(packageName.length() + 1);
                        packageEntryName = packageEntryName.substring(0, packageEntryName.indexOf("/"));

                        result.add(packageName.replace('/', '.') + "." + packageEntryName);

                        System.out.println("   PACKAGE : " + packageName.replace('/', '.') + "." + packageEntryName);
                    } catch (Throwable e) {
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                if (jarFile != null) {
                    jarFile.close();
                }
            } catch (Exception e) {
            }
        }
    }}