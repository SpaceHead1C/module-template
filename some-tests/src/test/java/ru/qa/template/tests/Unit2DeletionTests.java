package ru.qa.template.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.qa.template.model.Unit2Data;
import ru.qa.template.model.Units2;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class Unit2DeletionTests extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() {
        app.goTo().unit2Page();
        if (app.unit2().all().size() == 0) {
            app.unit2().create(new Unit2Data().withTextField("test1"));
        }
    }

    @Test
    public void testGroupDeletion() throws Exception {
        Units2 before = app.unit2().all();
        Unit2Data deletedGroup = before.iterator().next();
        app.unit2().delete(deletedGroup);
        assertThat(app.unit2().count(), equalTo(before.size() - 1));
        Units2 after = app.unit2().all();
        assertThat(after, equalTo(before.without(deletedGroup)));
    }
}
