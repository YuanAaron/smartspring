package com.smartspring.test;

import com.smartspring.test.v1.V1AllTests;
import com.smartspring.test.v2.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        V1AllTests.class,
        V2AllTests.class})
public class AllTests {
}
