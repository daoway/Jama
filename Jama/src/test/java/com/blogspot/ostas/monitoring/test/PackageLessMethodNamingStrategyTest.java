package com.blogspot.ostas.monitoring.test;

import com.blogspot.ostas.monitoring.publish.naming.impl.PackageLessMethodNamingStrategy;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class PackageLessMethodNamingStrategyTest {
    private static final Logger LOGGER = Logger.getLogger(PackageLessMethodNamingStrategyTest.class);
    @Test
    public void test() throws NoSuchMethodException {
        class A{
            int a1,a2;
            A() {}
            A(int a1, int a2) {
                this.a1 = a1;
                this.a2 = a2;
            }
            public void print(){
                System.out.println("A");
            }
        }

        class B extends A{
            int b1,b2;
            B() {}
            B(int b1, int b2) {
                this.b1 = b1;
                this.b2 = b2;
            }
            public void print(){
                System.out.println("B");
            }
        }
        PackageLessMethodNamingStrategy packageLessMethodNamingStrategy = new PackageLessMethodNamingStrategy();
        Method method = B.class.getMethod("print");
        String methodId = packageLessMethodNamingStrategy.getMethodAsString(method);
        LOGGER.info("Method id of derived class : "+methodId);

        assertEquals(methodId,"void B::print()");
    }
    @Test
    public void test2() throws NoSuchMethodException {
        class TestInterfaceImpl implements TestInterface{
            @Override
            public void method() {}
        }

        PackageLessMethodNamingStrategy packageLessMethodNamingStrategy = new PackageLessMethodNamingStrategy();
        Method method = TestInterfaceImpl.class.getMethod("method");
        String methodId = packageLessMethodNamingStrategy.getMethodAsString(method);
        LOGGER.info("Method id of derived class : "+methodId);

        assertEquals(methodId,"void TestInterfaceImpl::method()");
    }
}
