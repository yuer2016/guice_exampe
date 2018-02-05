package com.yicheng.rpc.codec;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by yuer on 2017/2/10.
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty",features = "src/test/java/feature")
public class ExporterTest {

}