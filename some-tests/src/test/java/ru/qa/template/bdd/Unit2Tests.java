package ru.qa.template.bdd;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "classpath:bdd", plugin={"pretty", "html:build/cucumber-report"})
public class Unit2Tests extends AbstractTestNGCucumberTests {
}
