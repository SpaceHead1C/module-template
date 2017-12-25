package ru.qa.template.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.qa.template.model.Unit2Data;
import ru.qa.template.model.Units2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class Unit2CreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validUnits2FromXml() throws IOException {
        String xml = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/units2.xml")))) {
            String line = reader.readLine();
            while (line != null) {
                xml += line;
                line = reader.readLine();
            }
        }

        XStream xStream = new XStream();
        xStream.processAnnotations(Unit2Data.class);
        List<Unit2Data> units2 = (List<Unit2Data>)xStream.fromXML(xml);
        return units2.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> validUnits2FromJson() throws IOException {
        String json = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/units2.json")))) {
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
        }

        Gson gson = new Gson();
        List<Unit2Data> units2 = gson.fromJson(json, new TypeToken<List<Unit2Data>>(){}.getType()); // like a List<Unit2Data>.class
        return units2.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "validUnits2FromJson")
    public void testUnit2Creation(Unit2Data unit2) throws Exception {
        app.goTo().unit2Page();
        Units2 before = app.unit2().all();
        app.unit2().create(unit2);
        assertThat(app.unit2().count(), equalTo(before.size() + 1));
        Units2 after = app.unit2().all();
        assertThat(after, equalTo(
                before.withAdded(unit2.withId(after.stream().mapToInt(Unit2Data::getId).max().getAsInt()))));
    }
}
