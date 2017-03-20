package com.caf.barryirvine.newsfeed.api;

import com.caf.barryirvine.newsfeed.network.IOUtils;

import org.junit.Assert;

import java.io.InputStream;

class ApiTest {

    String getFileContents(final String fileResourceName) {
        final String className = getClassName(0, Thread.currentThread().getStackTrace());
        try {
            final Class<?> clazz = Class.forName(className);
            final InputStream inputStream = clazz.getResourceAsStream(fileResourceName);
            Assert.assertNotNull("File " + fileResourceName
                    + " not found in the classpath of the class " + clazz
                    + ", also ensure that your test class uses naming convention ____"
                    + ApiTest.class.getSimpleName(), inputStream);
            return IOUtils.convertUTF8StreamToString(inputStream);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getClassName(final int index, final StackTraceElement[] stack) {
        if (index >= stack.length) {
            throw new RuntimeException("Make sure that your test class uses naming convention ____"
                    + ApiTest.class.getSimpleName());
        }
        final StackTraceElement ste = stack[index];
        final String name = ste.getClassName();
        return name.endsWith(ApiTest.class.getSimpleName())
                && !name.endsWith("." + ApiTest.class.getSimpleName())
                ? name : getClassName(index + 1, stack);
    }
}
